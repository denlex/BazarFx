package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.defence.viewmodels.OrganizationCatalogViewModel;
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

	private void initializeOrganizationsTableView() {
		organizationsTableView.itemsProperty().bindBidirectional(viewModel.organizationsProperty());
		nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
		codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
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
