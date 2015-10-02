package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
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
		treeView.rootProperty().bindBidirectional(viewModel.rootProperty());
		viewModel.getRoot().setValue("Каталог СФО");

		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.setSelectedFormat(null);
			viewModel.setSelectedName(null);
			viewModel.setSelectedDescription(null);

			if (newValue != null) {
				if (newValue.getValue() instanceof DescriptionFormatViewModel) {
					viewModel.selectedFormatProperty().unbind();
					viewModel.selectedFormatProperty().bindBidirectional(new SimpleObjectProperty<>(
							(DescriptionFormatViewModel) newValue.getValue()));
//					System.out.println(viewModel.getSelectedFormat().getName());
				} else {
					if (newValue.getValue() instanceof AssertedNameViewModel) {
						viewModel.selectedNameProperty().unbind();
						viewModel.selectedNameProperty().bindBidirectional(new SimpleObjectProperty<>(
								(AssertedNameViewModel)
										newValue.getValue()));
//						System.out.println(viewModel.getSelectedName());
					} else {
						if (newValue.getValue() instanceof CatalogDescriptionViewModel) {
							viewModel.selectedDescriptionProperty().unbind();
							viewModel.selectedDescriptionProperty().bindBidirectional(new SimpleObjectProperty<>(
									(CatalogDescriptionViewModel) newValue.getValue()));
						}
					}
				}
			}
		});
		treeView.setCellFactory(p -> new TreeCellFactory());

		root.setExpanded(true);
	}

	private void selectEditedDescriptionFormat(DescriptionFormatViewModel format) {
		ObservableList<TreeItem<Object>> formats = treeView.getRoot().getChildren();
		for (TreeItem<Object> object : formats) {
			if (format.getId() == ((DescriptionFormatViewModel) object.getValue()).getId()) {
				treeView.getSelectionModel().select(object);
				break;
			}
		}
	}

	private void selectEditedAssertedName(DescriptionFormatViewModel format, AssertedNameViewModel name) {
		ObservableList<TreeItem<Object>> formatItems = treeView.getRoot().getChildren();

		for (TreeItem<Object> formatItem : formatItems) {
			if (((DescriptionFormatViewModel) formatItem.getValue()).getId() == format.getId()) {
				ObservableList<TreeItem<Object>> nameItems = formatItem.getChildren();

				for (TreeItem<Object> nameItem : nameItems) {
					if (name.getId() == ((AssertedNameViewModel) nameItem.getValue()).getId()) {
						treeView.getSelectionModel().select(nameItem);
						break;
					}
				}
				break;
			}
		}
	}

	private void addDescriptionFormat() {
		ViewTuple<DescriptionFormatEditView, DescriptionFormatEditViewModel> viewTuple = FluentViewLoader.fxmlView
				(DescriptionFormatEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);

		Parent root = viewTuple.getView();
		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
		viewTuple.getCodeBehind().initializeStage();

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
			viewModel.displayFormats();
			// select new added format in treeView
			selectEditedDescriptionFormat(viewTuple.getViewModel().getEditedFormat());
			treeView.scrollTo(treeView.getSelectionModel() != null ? treeView.getSelectionModel().getSelectedIndex() : 0);
		}
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
			removeDescriptionFormatMenuItem.setOnAction(event -> {
				viewModel.getDeleteDescriptionFormatCommand().execute();
				treeView.getSelectionModel().select(0);
			});

			descriptionFormatMenu.getItems().addAll(addAssertedNameMenuItem, editDescriptionFormatMenuItem,
					removeDescriptionFormatMenuItem);

			MenuItem addCatalogDescriptionMenuItem = new MenuItem("Добавить КО");
			addCatalogDescriptionMenuItem.setOnAction(event -> addCatalogDescription());
			MenuItem editAssertedNameMenuItem = new MenuItem("Редактировать УН");
			editAssertedNameMenuItem.setOnAction(event -> editAssertedName());

			MenuItem removeAssertedNameMenuItem = new MenuItem("Удалить УН");
			removeAssertedNameMenuItem.setOnAction(event -> {
				viewModel.getDeleteAssertedNameCommand().execute();
			});

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

			// select edited format in treeView
			if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
				selectEditedDescriptionFormat(viewTuple.getViewModel().getEditedFormat());
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

			if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
				DescriptionFormatViewModel selectedFormat = viewModel.getSelectedFormat();
				viewModel.displayFormats();
				// select new added assertedName in treeView
				selectEditedAssertedName(selectedFormat, viewTuple.getViewModel().getEditedName());
			}
		}

		private void editAssertedName() {
			if (viewModel.getSelectedName() == null) {
				return;
			}

			ViewTuple<AssertedNameEditView, AssertedNameEditViewModel> viewTuple = FluentViewLoader.fxmlView
					(AssertedNameEditView.class).load();
			viewTuple.getViewModel().setParentViewModel(viewModel);

			Parent root = viewTuple.getView();
			Stage dialog = new Stage();
			viewTuple.getCodeBehind().setStage(dialog);
			viewTuple.getCodeBehind().initializeStage();

			Property<AssertedNameViewModel> f = viewModel.selectedNameProperty();
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

			// select edited format in treeView
			if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
//				TreeItem<Object> selectedItem = treeView.getSelectionModel().getSelectedItem();
				int index = treeView.getSelectionModel().getSelectedIndex();
				viewModel.displayFormats();
//				treeView.getSelectionModel().select(selectedItem);
				treeView.getSelectionModel().select(index);
				treeView.getFocusModel().focus(index);
				// TODO: не работает прокрутка
				treeView.scrollTo(treeView.getSelectionModel() != null ? treeView.getSelectionModel().getSelectedIndex
						() : 0);
			}
		}

		private void addCatalogDescription() {
			ViewTuple<CatalogDescriptionEditView, CatalogDescriptionEditViewModel> viewTuple = FluentViewLoader.fxmlView
					(CatalogDescriptionEditView.class).load();
			viewTuple.getViewModel().setParentViewModel(viewModel);
			Parent root = viewTuple.getView();

			Stage dialog = new Stage();
			viewTuple.getCodeBehind().setStage(dialog);
			viewTuple.getCodeBehind().initializeStage();

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
				/*DescriptionFormatViewModel selectedFormat = viewModel.getSelectedFormat();
				viewModel.displayFormats();
				// select new added assertedName in treeView
				selectEditedCatalogDescription(selectedFormat, viewTuple.getViewModel().getEditedName());*/
			}
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initializeStage() {
		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void testButtonClicked() {
		viewModel.getTestCommand().execute();
	}
}