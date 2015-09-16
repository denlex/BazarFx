package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.defence.viewmodels.AssertedNameViewModel;
import org.defence.viewmodels.CatalogDescriptionViewModel;
import org.defence.viewmodels.DescriptionFormatViewModel;
import org.defence.viewmodels.MainViewModel;

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
			MenuItem editAssertedNameMenuItem = new MenuItem("Редактировать УН");
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


	}
}


