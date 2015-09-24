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
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.DescriptionFormat;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by root on 30.08.15.
 */
public class MainViewModel implements ViewModel {
	private Command exitCommand;
	private final ListProperty<DescriptionFormatViewModel> formats = new SimpleListProperty<>();
	private DbHelper dbHelper = DbHelper.getInstance();

	private final ObjectProperty<DescriptionFormatViewModel> selectedFormat = new SimpleObjectProperty<>();
	private final ObjectProperty<AssertedNameViewModel> selectedName = new SimpleObjectProperty<>();
	private final ObjectProperty<CatalogDescriptionViewModel> selectedDescription = new SimpleObjectProperty<>();
	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private ObjectProperty<TreeItem<Object>> root = new SimpleObjectProperty<>();

	private Command deleteFormatCommand;
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

		deleteFormatCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление СФО");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить СФО:\nНаименование:   " + getSelectedFormat().getName());

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

		testCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
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

	public Command getDeleteFormatCommand() {
		return deleteFormatCommand;
	}

	public void setDeleteFormatCommand(Command deleteFormatCommand) {
		this.deleteFormatCommand = deleteFormatCommand;
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

		Set<AssertedNameViewModel> names = format.getAssertedNames();

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

			private ObservableSet<TreeItem<Object>> buildChildren(TreeItem<Object> treeItem) {
				if (treeItem.getValue() instanceof DescriptionFormatViewModel) {
					DescriptionFormatViewModel format = (DescriptionFormatViewModel) treeItem.getValue();

					if (format != null) {
						Set<AssertedNameViewModel> assertedNames = format.getAssertedNames();

						if (assertedNames != null) {
							ObservableSet<TreeItem<Object>> children = FXCollections.observableSet();

							for (AssertedNameViewModel name : assertedNames) {
								children.add(createNode(name));
							}
							return children;
						}
					}
				}

				if (treeItem.getValue() instanceof AssertedNameViewModel) {
					AssertedNameViewModel name = (AssertedNameViewModel) treeItem.getValue();

					if (name != null) {
						Set<CatalogDescriptionViewModel> catalogDescriptions = name.getCatalogDescriptions();

						if (catalogDescriptions != null) {
							ObservableSet<TreeItem<Object>> children = FXCollections.observableSet();

							for (CatalogDescriptionViewModel description : catalogDescriptions) {
								children.add(createNode(description));
							}
							return children;
						}
					}
				}

				return FXCollections.emptyObservableSet();
			}
		};
	}
}
