package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.defence.viewmodels.OrganizationCatalogViewModel;
import org.defence.viewmodels.OrganizationEditViewModel;
import org.defence.viewmodels.OrganizationViewModel;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationCatalogView implements FxmlView<OrganizationCatalogViewModel> {
	@FXML
	private TableView<OrganizationViewModel> organizationsTableView;

	@FXML
	private TableColumn<OrganizationViewModel, String> nameTableColumn;

	@FXML
	private TableColumn<OrganizationViewModel, String> codeTableColumn;

	@FXML
	private TableColumn<OrganizationViewModel, String> typeTableColumn;

	@InjectViewModel
	OrganizationCatalogViewModel viewModel;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void addOrganizationButtonClicked(Event event) {
		ViewTuple<OrganizationEditView, OrganizationEditViewModel> viewTuple = FluentViewLoader.fxmlView
				(OrganizationEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);
		Parent root = viewTuple.getView();

		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
//		viewTuple.getCodeBehind().initializeStage();

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		dialog.setScene(scene);
		dialog.showAndWait();

		// set current position in characteristicsTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			int lastRowIndex = viewModel.getOrganizations().size() - 1;
			organizationsTableView.scrollTo(lastRowIndex);
			organizationsTableView.selectionModelProperty().get().select(lastRowIndex);
			organizationsTableView.requestFocus();
		}
	}

	public void editOrganizationButtonClicked(Event event) {
		System.out.println("INSIDE");

		Property<OrganizationViewModel> org = viewModel.selectedOrganizationProperty();

		// if item in measurementTableView was not selected
		if (org.getValue() == null) {
			return;
		}

		ViewTuple<OrganizationEditView, OrganizationEditViewModel> viewTuple = FluentViewLoader.fxmlView
				(OrganizationEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);
		Parent root = viewTuple.getView();

		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
//		viewTuple.getCodeBehind().initializeStage();

		viewTuple.getViewModel().idProperty().bindBidirectional(org.getValue().idProperty());
		viewTuple.getViewModel().codeProperty().bindBidirectional(org.getValue().codeProperty());
		viewTuple.getViewModel().nameProperty().bindBidirectional(org.getValue().nameProperty());
        /*viewTuple.getViewModel().setCachedCode(m.getValue().codeProperty().getValue());
        viewTuple.getViewModel().setCachedName(m.getValue().nameProperty().getValue());*/

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(stage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		int selectedItemIndex = organizationsTableView.getSelectionModel().getFocusedIndex();

		dialog.setScene(scene);
		dialog.showAndWait();

		// if characteristics were modified, then refresh characteristicsTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			organizationsTableView.refresh();
		}

		organizationsTableView.scrollTo(selectedItemIndex);
		organizationsTableView.selectionModelProperty().get().select(selectedItemIndex);
		organizationsTableView.requestFocus();
	}

	public void organizationTableViewKeyPressed(KeyEvent event) {
		KeyCode keyCode = event.getCode();

		if (keyCode == KeyCode.INSERT) {
			addOrganizationButtonClicked(event);
			return;
		}

		if (keyCode == KeyCode.ENTER) {
			editOrganizationButtonClicked(event);
			return;
		}

		if (event.getCode() == KeyCode.DELETE) {
			deleteOrganizationButtonClicked(event);
		}
	}

	public void organizationsTableViewMouseRelease(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				editOrganizationButtonClicked(event);
			}
		}
	}

	public void deleteOrganizationButtonClicked(Event event) {
		viewModel.getDeleteCommand().execute();
		organizationsTableView.requestFocus();
	}

	public void cancelButtonClicked() {
		stage.close();
	}

	private void initializeOrganizationsTableView() {
		organizationsTableView.itemsProperty().bindBidirectional(viewModel.organizationsProperty());
		codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
		typeTableColumn.setCellValueFactory(new PropertyValueFactory("type"));

		organizationsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.selectedOrganizationProperty().unbind();
			viewModel.selectedOrganizationProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});
	}

	public void initialize() {
		initializeOrganizationsTableView();
	}
}
