package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.defence.MainApp;
import org.defence.viewmodels.*;

import java.util.Set;

/**
 * Created by root on 22.07.15.
 */
public class MainView implements FxmlView<MainViewModel> {
	@FXML
	private MenuItem exitBtn;

	@FXML
	TabPane tabs;

	@FXML
	TreeView<Object> treeView;

	private TreeItem<Object> root = new TreeItem<>("Каталог");

	@InjectViewModel
	private MainViewModel viewModel;

	private Stage stage;

	private void exitBtnRegisterEvents() {
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
	}

	public void exitButtonClicked(Event event) {
		viewModel.getExitCommand().execute();
	}

	public void initialize() {


		/*for (Object object : viewModel.getFormats()) {

			root.getChildren().add(createNode(object));
		}*/

//		rootProperty.setValue(root);

		treeView.rootProperty().bindBidirectional(viewModel.rootProperty());

//		root.setExpanded(true);
//		treeView.setRoot(root);

		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.setSelectedFormat(null);
			viewModel.setSelectedName(null);
			viewModel.setSelectedDescription(null);

			if (newValue != null) {
				if (newValue.getValue() instanceof DescriptionFormatViewModel) {
					viewModel.selectedFormatProperty().unbind();
					viewModel.selectedFormatProperty().bindBidirectional(new SimpleObjectProperty<>(
							(DescriptionFormatViewModel) newValue.getValue()));
				}


				if (newValue.getValue() instanceof AssertedNameViewModel) {
					viewModel.selectedNameProperty().unbind();
					viewModel.selectedNameProperty().bindBidirectional(new SimpleObjectProperty<>((AssertedNameViewModel)
							newValue.getValue()));
				}

				if (newValue.getValue() instanceof CatalogDescriptionViewModel) {
					viewModel.selectedDescriptionProperty().unbind();
					viewModel.selectedDescriptionProperty().bindBidirectional(new SimpleObjectProperty<>(
							(CatalogDescriptionViewModel) newValue.getValue()));
				}
			}
		});
		treeView.setCellFactory(p -> new TreeCellFactory());

		root.setExpanded(true);
	}

	private void addDescriptionFormat() {

		ViewTuple<DescriptionFormatEditView, DescriptionFormatEditViewModel> viewTuple = FluentViewLoader.fxmlView
				(DescriptionFormatEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);

		Parent root = viewTuple.getView();
		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
		viewTuple.getCodeBehind().initializeStage();

			/*Property<DescriptionFormatViewModel> f = viewModel.selectedFormatProperty();
			System.out.println(f.getValue().getId());
			System.out.println(f.getValue().getCode());
			System.out.println(f.getValue().getName());

			viewTuple.getViewModel().idProperty().bindBidirectional(f.getValue().idProperty());
			viewTuple.getViewModel().codeProperty().bindBidirectional(f.getValue().codeProperty());
			viewTuple.getViewModel().nameProperty().bindBidirectional(f.getValue().nameProperty());*/


		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(MainApp.mainStage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		dialog.setScene(scene);
		dialog.showAndWait();

		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			treeView.refresh();
		}
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

	private final class TreeCellFactory extends TreeCell<Object> {

		private ContextMenu descriptionFormatMenu = new ContextMenu();
		private ContextMenu assertedNameMenu = new ContextMenu();
		private ContextMenu rootMenu = new ContextMenu();

		public TreeCellFactory() {
			MenuItem addAssertedNameMenuItem = new MenuItem("Добавить УН");
			addAssertedNameMenuItem.setOnAction(event -> addAssertedName());

			MenuItem editDescriptionFormatMenuItem = new MenuItem("Редактировать СФО");
			editDescriptionFormatMenuItem.setOnAction(event -> editDescriptionFormat());

			MenuItem removeDescriptionFormatMenuItem = new MenuItem("Удалить СФО");
			descriptionFormatMenu.getItems().addAll(addAssertedNameMenuItem, editDescriptionFormatMenuItem,
					removeDescriptionFormatMenuItem);

			MenuItem addCatalogDescriptionMenuItem = new MenuItem("Добавить КО");
			MenuItem editAssertedNameMenuItem = new MenuItem("Редактировать УН");
			MenuItem removeAssertedNameMenuItem = new MenuItem("Удалить УН");
			assertedNameMenu.getItems().addAll(addCatalogDescriptionMenuItem, editAssertedNameMenuItem,
					removeAssertedNameMenuItem);

			MenuItem addDescriptionFormatItem = new MenuItem("Добавить СФО");
			addDescriptionFormatItem.setOnAction(event -> addDescriptionFormat());
			rootMenu.getItems().add(addDescriptionFormatItem);
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
			} else {
				setText(item.toString());
			}

			if (item instanceof DescriptionFormatViewModel) {
				setContextMenu(descriptionFormatMenu);
			}

			if (item instanceof AssertedNameViewModel) {
				setContextMenu(assertedNameMenu);
			}

			if (item instanceof String) {
				setContextMenu(rootMenu);
			}
		}

		private void addAssertedName() {
			ViewTuple<AssertedNameEditView, AssertedNameEditViewModel> viewTuple = FluentViewLoader.fxmlView
					(AssertedNameEditView.class).load();
			viewTuple.getViewModel().setParentViewModel(viewModel);
			Parent root = viewTuple.getView();

			Stage dialog = new Stage();
			viewTuple.getCodeBehind().setStage(dialog);

			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(MainApp.mainStage);
			dialog.setResizable(false);

			Scene scene = new Scene(root);
			scene.addEventHandler(KeyEvent.ANY, event -> {
				if (event.getCode() == KeyCode.ESCAPE) {
					dialog.close();
				}
			});

			dialog.setScene(scene);
			dialog.showAndWait();
		}

		private void editDescriptionFormat() {
			// TODO: ��������� ���-�� ��� ������ �������� (������ �� ������������ ��� � TreeView)
			if (viewModel.getSelectedFormat() == null) {
				return;
			}

			ViewTuple<DescriptionFormatEditView, DescriptionFormatEditViewModel> viewTuple = FluentViewLoader.fxmlView
					(DescriptionFormatEditView.class).load();
			viewTuple.getViewModel().setParentViewModel(viewModel);

			Parent root = viewTuple.getView();
			Stage dialog = new Stage();
			viewTuple.getCodeBehind().setStage(dialog);
			viewTuple.getCodeBehind().initializeStage();

			Property<DescriptionFormatViewModel> f = viewModel.selectedFormatProperty();
			System.out.println(f.getValue().getId());
			System.out.println(f.getValue().getCode());
			System.out.println(f.getValue().getName());

			viewTuple.getViewModel().idProperty().bindBidirectional(f.getValue().idProperty());
			viewTuple.getViewModel().codeProperty().bindBidirectional(f.getValue().codeProperty());
			viewTuple.getViewModel().nameProperty().bindBidirectional(f.getValue().nameProperty());


			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(MainApp.mainStage);
			dialog.setResizable(false);

			Scene scene = new Scene(root);
			scene.addEventHandler(KeyEvent.ANY, event -> {
				if (event.getCode() == KeyCode.ESCAPE) {
					dialog.close();
				}
			});

			dialog.setScene(scene);
			dialog.showAndWait();
		}
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initializeStage() {
		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void testButtonClicked() {
//		treeView.refresh();
		viewModel.getTestCommand().execute();
	}
}