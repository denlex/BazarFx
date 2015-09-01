package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.defence.domain.entities.MeasurementType;
import org.defence.viewmodels.MeasurementListViewModel;
import org.defence.viewmodels.MeasurementTypeViewModel;
import org.defence.viewmodels.MeasurementViewModel;

/**
 * Created by root on 8/12/15.
 */
public class MeasurementListView implements FxmlView<MeasurementListViewModel> {
    @FXML
    private ComboBox<MeasurementType> measurementTypeComboBox;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<MeasurementTypeViewModel> typesTableView;

    @FXML
    private TableColumn<MeasurementTypeViewModel, Integer> idTypeTableColumn;

    @FXML
    private TableColumn<MeasurementTypeViewModel, String> codeTypeTableColumn;

    @FXML
    private TableColumn<MeasurementTypeViewModel, String> nameTypeTableColumn;

    @FXML
    private TableView<MeasurementViewModel> measurementsTableView;

    @FXML
    private TableColumn<MeasurementViewModel, Integer> idTableColumn;

    @FXML
    private TableColumn<MeasurementViewModel, String> codeTableColumn;

    @FXML
    private TableColumn<MeasurementViewModel, String> nameTableColumn;

    @InjectViewModel
    MeasurementListViewModel viewModel;

    private void measurementTypeComboBoxRegisterEvents() {
        measurementTypeComboBox.setOnAction(event -> {
//                JOptionPane.showMessageDialog(null, measurementTypeComboBox.getValue().getName());
        });

        // Define rendering of the list of values in ComboBox drop down.
        measurementTypeComboBox.setCellFactory((comboBox) -> new ListCell<MeasurementType>() {
            @Override
            protected void updateItem(MeasurementType item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        // Define rendering of selected value shown in ComboBox.
        measurementTypeComboBox.setConverter(new StringConverter<MeasurementType>() {
            @Override
            public String toString(MeasurementType measurementType) {
                if (measurementType == null) {
                    return null;
                } else {
                    return measurementType.getName();
                }
            }

            @Override
            public MeasurementType fromString(String measurementTypeString) {
                return null; // No conversion fromString needed.
            }
        });
    }

    private void initializeTypesTableView() {
        typesTableView.itemsProperty().bindBidirectional(viewModel.typesProperty());
        idTypeTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTypeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTypeTableColumn.setCellValueFactory(new PropertyValueFactory("name"));

//        typesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            viewModel.selectedRowProperty().unbind();
//            viewModel.selectedRowProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
//        });
    }

    private void initializeMeasurementsTableView() {
        measurementsTableView.itemsProperty().bindBidirectional(viewModel.measurementsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
    }

    public void initialize() {

        initializeTypesTableView();
        initializeMeasurementsTableView();
    }
}
