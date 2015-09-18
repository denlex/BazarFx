package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
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

import javax.swing.*;
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

	@InjectViewModel
	private MainViewModel viewModel;

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
		TreeItem<Object> root = new TreeItem<>("Каталог СФО");

		for (Object object : viewModel.getFormats()) {
			root.getChildren().add(createNode(object));
		}

		treeView.setRoot(root);
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.setSelectedFormat(null);
			viewModel.setSelectedName(null);
			viewModel.setSelectedDescription(null);

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
		});
		treeView.setCellFactory(p -> new TreeCellFactory());

		root.setExpanded(true);
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

		public TreeCellFactory() {
			MenuItem addAssertedNameMenuItem = new MenuItem("Добавить УН");
			addAssertedNameMenuItem.setOnAction(event -> addAssertedName());

			MenuItem editAssertedNameMenuItem = new MenuItem("Редактировать УН");
			editAssertedNameMenuItem.setOnAction(event -> editAssertedName());

			MenuItem removeAssertedNameMenuItem = new MenuItem("Удалить УН");
			descriptionFormatMenu.getItems().addAll(addAssertedNameMenuItem, editAssertedNameMenuItem,
					removeAssertedNameMenuItem);

			MenuItem addCatalogDescriptionMenuItem = new MenuItem("Добавить КО");
			MenuItem editCatalogDescriptionMenuItem = new MenuItem("Редактировать КО");
			MenuItem removeCatalogDescriptionMenuItem = new MenuItem("Удалить КО");
			assertedNameMenu.getItems().addAll(addCatalogDescriptionMenuItem, editCatalogDescriptionMenuItem,
					removeCatalogDescriptionMenuItem);
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

		private void editAssertedName() {
			if (viewModel.getSelectedName() != null) {
				JOptionPane.showMessageDialog(null, viewModel.getSelectedName().getName());
			}

			/*if (viewModel.getSelectedDescription() != null) {
				JOptionPane.showMessageDialog(null, viewModel.getSelectedDescription().getName());
				return;
			}

			if (viewModel.getSelectedFormat() != null) {
				JOptionPane.showMessageDialog(null, viewModel.getSelectedFormat().getName());
				return;
			}

			if (viewModel.getSelectedName() != null) {
				JOptionPane.showMessageDialog(null, viewModel.getSelectedName().getName());
				return;
			}*/
			/*ViewTuple<AssertedNameEditView, AssertedNameEditViewModel> viewTuple = FluentViewLoader.fxmlView
					(AssertedNameEditView.class).load();
			viewTuple.getViewModel().setParentViewModel(viewModel);

			Property<AssertedNameViewModel> t = viewModel.selectedNameProperty();
			viewTuple.getViewModel().idProperty().bindBidirectional(t.getValue().idProperty());
			viewTuple.getViewModel().codeProperty().bindBidirectional(t.getValue().codeProperty());
			viewTuple.getViewModel().nameProperty().bindBidirectional(t.getValue().nameProperty());

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
			dialog.showAndWait();*/
		}
	}


	public void testButtonClicked() {
		viewModel.getTestCommand().execute();
	}
}


