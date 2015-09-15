package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.defence.viewmodels.AssertedNameViewModel;
import org.defence.viewmodels.CatalogDescriptionViewModel;
import org.defence.viewmodels.MainViewModel;

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
        TreeItem<Object> root = new TreeItem<>("Formats");

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

			private ObservableList<TreeItem<Object>> buildChildren(TreeItem<Object> treeItem) {
				if (treeItem.getValue() instanceof AssertedNameViewModel) {
					ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

					for (Object object : ((AssertedNameViewModel) treeItem.getValue()).getCatalogDescriptions()) {
						children.add(createNode(object));
					}

					return children;
				}

				return FXCollections.emptyObservableList();
			}
		};
	}

	private final class TreeCellFactory extends TreeCell<Object> {
		private ContextMenu topicMenu = new ContextMenu();
		private ContextMenu groupMenu = new ContextMenu();

		public TreeCellFactory() {
			MenuItem topicMenuItem = new MenuItem("- Item 1 -");
			topicMenu.getItems().add(topicMenuItem);

			MenuItem groupMenuItem = new MenuItem("- Item 1 -");
			groupMenu.getItems().add(groupMenuItem);
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
			} else {
				setText(item.toString());
			}

			if (item instanceof CatalogDescriptionViewModel) {
				setContextMenu(topicMenu);
			}

			if (item instanceof DescriptionFormatEditView) {
				setContextMenu(groupMenu);
			}
		}


	}
}


