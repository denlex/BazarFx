package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.defence.viewmodels.CharacteristicTypeViewModel;
import org.defence.viewmodels.CharacteristicCatalogViewModel;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicCatalogView implements FxmlView<CharacteristicCatalogViewModel> {

    @FXML
    private TableView<CharacteristicTypeViewModel> typesTableView;

    @FXML
    private TableColumn<CharacteristicTypeViewModel, Integer> idTypeTableColumn;

    @FXML
    private TableColumn<CharacteristicTypeViewModel, String> codeTypeTableColumn;

    @FXML
    private TableColumn<CharacteristicTypeViewModel, String> nameTypeTableColumn;

    @FXML
    private TableView<CharacteristicCatalogViewModel> characteristicsTableView;

    @FXML
    private TableColumn<CharacteristicCatalogViewModel, Integer> idTableColumn;

    @FXML
    private TableColumn<CharacteristicCatalogViewModel, String> codeTableColumn;

    @FXML
    private TableColumn<CharacteristicCatalogViewModel, String> nameTableColumn;

    @InjectViewModel
    CharacteristicCatalogViewModel viewModel;

    private void initializeTypesTableView() {
        typesTableView.itemsProperty().bindBidirectional(viewModel.typesProperty());
        idTypeTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTypeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTypeTableColumn.setCellValueFactory(new PropertyValueFactory("name"));

        typesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedTypeProperty().unbind();
            viewModel.selectedTypeProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    private void initializeMeasurementsTableView() {
        measurementsTableView.itemsProperty().bindBidirectional(viewModel.measurementsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        shortNameTableColumn.setCellValueFactory(new PropertyValueFactory("shortName"));

        measurementsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedMeasurementProperty().unbind();
            viewModel.selectedMeasurementProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    public void initialize() {

    }
}
