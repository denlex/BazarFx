package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.*;
import org.defence.infrastructure.DbHelper;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import java.io.File;
import java.util.*;

/**
 * Created by root on 30.08.15.
 */
public class MainViewModel implements ViewModel {
	private Command exitCommand;
	private final ListProperty<DescriptionFormatViewModel> formats = new SimpleListProperty<>();
	private DbHelper dbHelper = DbHelper.getInstance();
	private File catalogDescriptionFile;

	private final ObjectProperty<DescriptionFormatViewModel> selectedFormat = new SimpleObjectProperty<>();
	private final ObjectProperty<AssertedNameViewModel> selectedName = new SimpleObjectProperty<>();
	private final ObjectProperty<CatalogDescriptionViewModel> selectedDescription = new SimpleObjectProperty<>();
	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private ObjectProperty<TreeItem<Object>> root = new SimpleObjectProperty<>();

	private Command deleteDescriptionFormatCommand;
	private Command deleteAssertedNameCommand;
	private Command deleteCatalogDescriptionCommand;
	private Command importCatalogDescriptionCommand;
	private Command exportCatalogDescriptionCommand;
	private Command testCommand;

	private void expandChildren(ObservableList<TreeItem<Object>> children) {
		if (children == null || children.size() == 0) {
			return;
		}

		for (TreeItem<Object> obj : children) {
			obj.setExpanded(true);

			if (obj.getChildren() != null) {
				expandChildren(obj.getChildren());
			}
		}
	}

	public MainViewModel() {

		loadAllFormatsFromDb();
		root.setValue(new TreeItem<>());
		displayFormats();


		shownWindow = new SimpleObjectProperty<>(event -> {

		});

		deleteDescriptionFormatCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление СФО");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить СФО?:\nНаименование:   " + getSelectedFormat()
						.getName());

				ButtonType yes = new ButtonType("Удалить");
				ButtonType no = new ButtonType("Отмена");

				alert.getButtonTypes().setAll(yes, no);
				((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == yes) {
					dbHelper.deleteDescriptionFormat(getSelectedFormat().getId());
					formats.removeIf(f -> f.getId() == getSelectedFormat().getId());
					displayFormats();
				}
			}
		});

		deleteAssertedNameCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление УН");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить УН?:\nНаименование:   " + getSelectedName()
						.getName());

				ButtonType yes = new ButtonType("Удалить");
				ButtonType no = new ButtonType("Отмена");

				alert.getButtonTypes().setAll(yes, no);
				((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == yes) {
					dbHelper.deleteAssertedName(getSelectedName().getId());
					// remove selectedName from formats collection
					formats.stream().forEach(f -> f.getAssertedNames().removeIf(n -> n.getId() == getSelectedName()
							.getId()));
					displayFormats();
				}
			}
		});

		deleteCatalogDescriptionCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление КО");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить КО?:\nНаименование:   " + getSelectedDescription
						().getName());

				ButtonType yes = new ButtonType("Удалить");
				ButtonType no = new ButtonType("Отмена");

				alert.getButtonTypes().setAll(yes, no);
				((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == yes) {
					dbHelper.deleteCatalogDescription(getSelectedDescription().getId());
					// remove selectedDescription from formats collection
					for (DescriptionFormatViewModel format : formats) {
						for (AssertedNameViewModel name : format.getAssertedNames()) {
							for (CatalogDescriptionViewModel description : name.getCatalogDescriptions()) {
								if (description.getId() == getSelectedDescription().getId()) {
									name.getCatalogDescriptions().remove(description);
									displayFormats();
									return;
								}
							}
						}
					}

//					formats.stream().forEach(f -> f.getAssertedNames().removeIf(n -> n.getId() == getSelectedName()
// .getId()));
//					displayFormats();
				}
			}
		});

		testCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				displayFormats();
			}
		});

		exitCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				System.out.println("Inside action");
				DbHelper.terminateDbConnection();
				Platform.exit();
				System.exit(0);
			}
		});

		importCatalogDescriptionCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setValidating(false);
					DocumentBuilder builder = dbFactory.newDocumentBuilder();
					Document doc = builder.parse(catalogDescriptionFile);
//					NodeList root = doc.getChildNodes();

					this.getClass().getClassLoader().getResource("catalog_description.xsd");
					/*Schema
					dbFactory.setSchema();*/

					Element root = doc.getDocumentElement();
					Element name = (Element) root.getElementsByTagName("name").item(0);

					Alert alert = new Alert(Alert.AlertType.INFORMATION);

					System.out.println(name.getAttribute("value"));
					System.out.println("textContent = " + name.getTextContent());
					System.out.println("nodeValue = " + name.getNodeValue());
					System.out.println("tagName = " + name.getTagName());
					System.out.println("parentNodeName = " + name.getParentNode().getNodeName());

					alert.setContentText(name.getAttribute("value"));
					alert.showAndWait();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		exportCatalogDescriptionCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

				try {
					DocumentBuilderFactory dbFactory =
							DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder =
							dbFactory.newDocumentBuilder();
					Document doc = dBuilder.newDocument();
					// root element
					Element catalogDescription = doc.createElement("catalogDescription");
					doc.appendChild(catalogDescription);

					// name element
					Element descriptionName = doc.createElement("name");
					catalogDescription.appendChild(descriptionName);

					// setting attribute to element
					Attr descriptionNameAttr = doc.createAttribute("value");
					descriptionNameAttr.setValue(selectedDescription.getValue().getName());
					descriptionName.setAttributeNode(descriptionNameAttr);

					List<CharacteristicValueViewModel> values = selectedDescription.getValue().getValues();

					if (values != null && values.size() > 0) {
						List<CharacteristicType> characteristicTypeListFromDb = dbHelper.getAllCharacteristicTypes();
						List<MeasurementType> measurementTypeListFromDb = dbHelper.getAllMeasurementTypes();

						for (CharacteristicValueViewModel value : values) {
							Element characteristicValueNode = doc.createElement("characteristicValue");
							catalogDescription.appendChild(characteristicValueNode);

							Attr valueAttr = doc.createAttribute("value");
							valueAttr.setValue(value.getValue());
							characteristicValueNode.setAttributeNode(valueAttr);

							CharacteristicViewModel characteristicViewModel = value.getCharacteristic();
							if (characteristicViewModel != null) {
								Element characteristicNode = doc.createElement("characteristic");
								characteristicValueNode.appendChild(characteristicNode);

								Element characteristicCode = doc.createElement("code");
								characteristicNode.appendChild(characteristicCode);

								Attr characteristicCodeAttr = doc.createAttribute("value");
								characteristicCodeAttr.setValue(characteristicViewModel.getCode());
								characteristicCode.setAttributeNode(characteristicCodeAttr);

								Element characteristicName = doc.createElement("name");
								characteristicNode.appendChild(characteristicName);

								Attr characteristicNameAttr = doc.createAttribute("value");
								characteristicNameAttr.setValue(characteristicViewModel.getName());
								characteristicName.setAttributeNode(characteristicNameAttr);

								CharacteristicType characteristicType = getTypeByCharacteristic
										(characteristicTypeListFromDb, value.getCharacteristic().toModel());

								Element characteristicTypeNode = doc.createElement("characteristicType");
								characteristicValueNode.appendChild(characteristicTypeNode);

								Element characteristicTypeCodeNode = doc.createElement("code");
								characteristicTypeNode.appendChild(characteristicTypeCodeNode);

								Attr characteristicTypeCodeAttr = doc.createAttribute("value");
								characteristicTypeCodeAttr.setValue(characteristicType.getCode());
								characteristicTypeCodeNode.setAttributeNode(characteristicTypeCodeAttr);

								Element characteristicTypeNameNode = doc.createElement("name");
								characteristicTypeNode.appendChild(characteristicTypeNameNode);

								Attr characteristicTypeNameAttr = doc.createAttribute("value");
								characteristicTypeNameAttr.setValue(characteristicType.getName());
								characteristicTypeNameNode.setAttributeNode(characteristicTypeNameAttr);

								List<MeasurementViewModel> measurements = characteristicViewModel.getMeasurements();

								if (measurements != null && measurements.size() > 0) {

									Element measurementsNode = doc.createElement("measurements");

									for (MeasurementViewModel measurement : measurements) {

										Element measurementNode = doc.createElement("measurement");
										measurementsNode.appendChild(measurementNode);

										Element measurementCodeNode = doc.createElement("code");
										measurementNode.appendChild(measurementCodeNode);

										Attr measurementCodeAttr = doc.createAttribute("value");
										measurementCodeAttr.setValue(measurement.getCode());
										measurementCodeNode.setAttributeNode(measurementCodeAttr);

										Element measurementNameNode = doc.createElement("name");
										measurementNode.appendChild(measurementNameNode);

										Attr measurementNameAttr = doc.createAttribute("value");
										measurementNameAttr.setValue(measurement.getName());
										measurementNameNode.setAttributeNode(measurementNameAttr);

										Element measurementShortNameNode = doc.createElement("shortName");
										measurementNode.appendChild(measurementShortNameNode);

										Attr measurementShortNameAttr = doc.createAttribute("value");
										measurementShortNameAttr.setValue(measurement.getShortName());
										measurementShortNameNode.setAttributeNode(measurementShortNameAttr);

										MeasurementType measurementType = getTypeByMeasurement
												(measurementTypeListFromDb, measurement.toModel());

										if (measurementType != null) {
											Element measurementTypeNode = doc.createElement("measurementType");
											measurementNode.appendChild(measurementTypeNode);

											Element measurementTypeCodeNode = doc.createElement("code");
											measurementTypeNode.appendChild(measurementTypeCodeNode);

											Attr measurementTypeCodeAttr = doc.createAttribute("value");
											measurementTypeCodeAttr.setValue(measurementType.getCode());
											measurementTypeCodeNode.setAttributeNode(measurementTypeCodeAttr);

											Element measurementTypeNameNode = doc.createElement("name");
											measurementTypeNode.appendChild(measurementTypeNameNode);

											Attr measurementTypeNameAttr = doc.createAttribute("value");
											measurementTypeNameAttr.setValue(measurementType.getName());
											measurementTypeNameNode.setAttributeNode(measurementTypeNameAttr);
										}
									}

									characteristicNode.appendChild(measurementsNode);
								}
							}
						}
					}


					// write the content into xml file
					TransformerFactory transformerFactory =
							TransformerFactory.newInstance();
					Transformer transformer =
							transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result =
							new StreamResult(catalogDescriptionFile);
					transformer.transform(source, result);
					// Output to console for testing
					StreamResult consoleResult =
							new StreamResult(System.out);
					transformer.transform(source, consoleResult);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String getChildrenAsString(Document doc) {
		final String SPACE = "   ";
		StringBuffer content = new StringBuffer();
		Node node = doc.getChildNodes().item(0);
		ApplicationNode appNode = new ApplicationNode(node);

		content.append("Application \n");

		List<ClassNode> classes = appNode.getClasses();

		for (int i = 0; i < classes.size(); i++) {
			ClassNode classNode = classes.get(i);
			if (classNode == null) {
				System.out.println("classNode is null");
			}
			content.append(SPACE + "Class: " + classNode.getName() + " \n");

			List<MethodNode> methods = classNode.getMethods();

			for (int j = 0; j < methods.size(); j++) {
				MethodNode methodNode = methods.get(j);
				content.append(SPACE + SPACE + "Method: "
						+ methodNode.getName() + " \n");
			}
		}

		return content.toString();
	}

	private MeasurementType getTypeByMeasurement(List<MeasurementType> types, Measurement measurement) {
		for (MeasurementType type : types) {

			List<Measurement> measurements = type.getMeasurements();

			for (Measurement meas : measurements) {
				if (meas.getId() == measurement.getId()) {
					return type;
				}
			}
		}

		return null;
	}

	private CharacteristicType getTypeByCharacteristic(List<CharacteristicType> types, Characteristic characteristic) {
		for (CharacteristicType type : types) {

			List<Characteristic> characteristics = type.getCharacteristics();

			for (Characteristic ch : characteristics) {
				if (ch.getId() == characteristic.getId()) {
					return type;
				}
			}
		}

		return null;
	}


	public Command getExitCommand() {
		return exitCommand;
	}

	public ObservableList<DescriptionFormatViewModel> getFormats() {
		return formats.get();
	}

	public ListProperty<DescriptionFormatViewModel> formatsProperty() {
		return formats;
	}

	public void setFormats(ObservableList<DescriptionFormatViewModel> formats) {
		this.formats.set(formats);
	}

	public TreeItem<Object> getRoot() {
		return root.get();
	}

	public ObjectProperty<TreeItem<Object>> rootProperty() {
		return root;
	}

	public void setRoot(TreeItem<Object> root) {
		this.root.set(root);
	}

	public DescriptionFormatViewModel getSelectedFormat() {
		return selectedFormat.get();
	}

	public ObjectProperty<DescriptionFormatViewModel> selectedFormatProperty() {
		return selectedFormat;
	}

	public void setSelectedFormat(DescriptionFormatViewModel selectedFormat) {
		this.selectedFormat.set(selectedFormat);
	}

	public AssertedNameViewModel getSelectedName() {
		return selectedName.get();
	}

	public ObjectProperty<AssertedNameViewModel> selectedNameProperty() {
		return selectedName;
	}

	public void setSelectedName(AssertedNameViewModel selectedName) {
		this.selectedName.set(selectedName);
	}

	public CatalogDescriptionViewModel getSelectedDescription() {
		return selectedDescription.get();
	}

	public ObjectProperty<CatalogDescriptionViewModel> selectedDescriptionProperty() {
		return selectedDescription;
	}

	public void setSelectedDescription(CatalogDescriptionViewModel selectedDescription) {
		this.selectedDescription.set(selectedDescription);
	}

	public Command getTestCommand() {
		return testCommand;
	}

	public EventHandler<WindowEvent> getShownWindow() {
		return shownWindow.get();
	}

	public ObjectProperty<EventHandler<WindowEvent>> shownWindowProperty() {
		return shownWindow;
	}

	public void setShownWindow(EventHandler<WindowEvent> shownWindow) {
		this.shownWindow.set(shownWindow);
	}

	public Command getDeleteDescriptionFormatCommand() {
		return deleteDescriptionFormatCommand;
	}

	public void setDeleteDescriptionFormatCommand(Command deleteDescriptionFormatCommand) {
		this.deleteDescriptionFormatCommand = deleteDescriptionFormatCommand;
	}

	public Command getDeleteAssertedNameCommand() {
		return deleteAssertedNameCommand;
	}

	public void setDeleteAssertedNameCommand(Command deleteAssertedNameCommand) {
		this.deleteAssertedNameCommand = deleteAssertedNameCommand;
	}

	public Command getDeleteCatalogDescriptionCommand() {
		return deleteCatalogDescriptionCommand;
	}

	public void setDeleteCatalogDescriptionCommand(Command deleteCatalogDescriptionCommand) {
		this.deleteCatalogDescriptionCommand = deleteCatalogDescriptionCommand;
	}

	public Command getImportCatalogDescriptionCommand() {
		return importCatalogDescriptionCommand;
	}

	public void setImportCatalogDescriptionCommand(Command importCatalogDescriptionCommand) {
		this.importCatalogDescriptionCommand = importCatalogDescriptionCommand;
	}

	public Command getExportCatalogDescriptionCommand() {
		return exportCatalogDescriptionCommand;
	}

	public void setExportCatalogDescriptionCommand(Command exportCatalogDescriptionCommand) {
		this.exportCatalogDescriptionCommand = exportCatalogDescriptionCommand;
	}

	public File getCatalogDescriptionFile() {
		return catalogDescriptionFile;
	}

	public void setCatalogDescriptionFile(File catalogDescriptionFile) {
		this.catalogDescriptionFile = catalogDescriptionFile;
	}

	public void loadAllFormatsFromDb() {
		List<DescriptionFormat> allFormatsFromDb = dbHelper.getAllDescriptionFormats();
		List<DescriptionFormatViewModel> list = null;

		if (allFormatsFromDb != null) {
			list = new LinkedList<>();

			for (DescriptionFormat elem : allFormatsFromDb) {
				if (elem.getAssertedNames() == null) {
					continue;
				}
				list.add(new DescriptionFormatViewModel(elem));
			}
		}

		formats.setValue(new ObservableListWrapper<>(list));
	}

	private TreeItem<Object> displayChildren(ObservableList<TreeItem<Object>> children) {
		if (children == null || children.size() == 0) {
			return null;
		}

		TreeItem<Object> root = new TreeItem<>();

		for (TreeItem<Object> child : children) {
			if (child.getChildren() != null && child.getChildren().size() > 0) {

			}
		}

		return root;
	}

	public void displayAssertedNames(DescriptionFormatViewModel format) {
		if (format == null) {
			return;
		}

		List<AssertedNameViewModel> names = format.getAssertedNames();

		if (names == null || names.size() == 0) {
			return;
		}

		TreeItem<Object> root = new TreeItem<>();

		for (AssertedNameViewModel name : names) {
			root.getChildren().add(createNode(name));
		}
	}

	public void displayFormats() {

		if (root == null || root.getValue() == null) {
			return;
		}

		TreeItem<Object> rootItem = new TreeItem<>(root.getValue().getValue());

		for (Object object : formats) {
			rootItem.getChildren().add(createNode(object));

		}
		root.setValue(null);
		root.setValue(rootItem);
		expandChildren(root.getValue().getChildren());
		root.getValue().setExpanded(true);
	}

	private TreeItem<Object> createNode(Object o) {
		return new TreeItem<Object>(o) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override
			public ObservableList<TreeItem<Object>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;

					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;

					if (o instanceof CatalogDescriptionViewModel) {
						isLeaf = true;
					}
				}

				return isLeaf;
			}

			private ObservableList<TreeItem<Object>> buildChildren(TreeItem<Object> treeItem) {
				if (treeItem.getValue() instanceof DescriptionFormatViewModel) {
					DescriptionFormatViewModel format = (DescriptionFormatViewModel) treeItem.getValue();

					if (format != null) {
						List<AssertedNameViewModel> assertedNames = format.getAssertedNames();

						if (assertedNames != null && assertedNames.size() > 0) {
							ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

							for (AssertedNameViewModel name : assertedNames) {
								children.add(createNode(name));
//								children.add(new TreeItem<>(name));
							}
							return children;
						}
					}
				}

				if (treeItem.getValue() instanceof AssertedNameViewModel) {
					AssertedNameViewModel name = (AssertedNameViewModel) treeItem.getValue();

					if (name != null) {
						List<CatalogDescriptionViewModel> catalogDescriptions = name.getCatalogDescriptions();

						if (catalogDescriptions != null) {
							ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

							for (CatalogDescriptionViewModel description : catalogDescriptions) {
								children.add(createNode(description));
//								children.add(new TreeItem<>(description));
							}
							return children;
						}
					}
				}

				return FXCollections.emptyObservableList();
			}
		};
	}

	/**
	 * Объектное представление приложения.
	 */
	public static class ApplicationNode {

		Node node;

		public ApplicationNode(Node node) {
			this.node = node;
		}

		public List<ClassNode> getClasses() {
			ArrayList<ClassNode> classes = new ArrayList<ClassNode>();

			/**
			 * Получаем список дочерних узлов для данного узла XML, который
			 * соответствует приложению application. Здесь будут располагаться
			 * все узлы Node, каждый из которых является объектным
			 * представлением тега class для текущего тега application.
			 */
			NodeList classNodes = node.getChildNodes();

			for (int i = 0; i < classNodes.getLength(); i++) {
				Node node = classNodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					/**
					 * Создаем на основе Node узла своё объектное
					 * представление класса.
					 */
					ClassNode classNode = new ClassNode(node);
					classes.add(classNode);
				}
			}

			return classes;
		}

	}

	/**
	 * Объектное представление класса.
	 */
	public static class ClassNode {

		Node node;

		/**
		 * Создаем новый экземпляр объекта на основе Node узла.
		 */
		public ClassNode(Node node) {
			this.node = node;
		}

		/**
		 * Возвращает список методов класса.
		 */
		public List<MethodNode> getMethods() {
			ArrayList<MethodNode> methods = new ArrayList<MethodNode>();

			/**
			 * Получаем список дочерних узлов для данного узла XML,
			 * который соответствует классу class. Здесь будут располагаться
			 * все узлы Node, каждый из которых является объектным
			 * представлением тега method для текущего тега class.
			 */
			NodeList methodNodes = node.getChildNodes();

			for (int i = 0; i < methodNodes.getLength(); i++) {
				node = methodNodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					/**
					 * Создаем на основе Node узла своё объектное представление
					 * метода.
					 */
					MethodNode methodNode = new MethodNode(node);
					methods.add(methodNode);
				}
			}

			return methods;
		}

		/**
		 * Возвращае имя класса.
		 */
		public String getName() {

			/**
			 * Получаем атрибуты узла метода.
			 */
			NamedNodeMap attributes = node.getAttributes();

			/**
			 * Получаем узел аттрибута.
			 */
			Node nameAttrib = attributes.getNamedItem("name");

			/**
			 * Возвращаем значение атрибута.
			 */
			return nameAttrib.getNodeValue();
		}
	}

	/**
	 * Объектное представление сущности метод класса.
	 */
	public static class MethodNode {

		Node node;

		/**
		 * Создаем новый экземпляр объекта на основе Node узла.
		 */
		public MethodNode(Node node) {
			this.node = node;
		}

		/**
		 * Возвращает имя метода.
		 */
		public String getName() {

			/**
			 * Получаем атрибуты узла метода.
			 */
			NamedNodeMap attributes = node.getAttributes();

			/**
			 * Получаем узел аттрибута.
			 */
			Node nameAttrib = attributes.getNamedItem("name");

			/**
			 * Возвращаем значение атрибута.
			 */
			return nameAttrib.getNodeValue();
		}

	}
}
