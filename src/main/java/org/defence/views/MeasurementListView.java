package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.defence.MainApp;
import org.defence.domain.entities.MeasurementType;
import org.defence.viewmodels.MeasurementListViewModel;
import org.defence.viewmodels.MeasurementTypeEditViewModel;
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

    @FXML
    private Button addTypeButton;

    @FXML
    private Button editTypeButton;

    @FXML
    private Button daleteTypeButton;

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

        measurementsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedMeasurementProperty().unbind();
//            viewModel.loadMeasurementsBySelectedType();
            viewModel.selectedMeasurementProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    public void addTypeButtonClick(Event event) {
        ViewTuple<MeasurementTypeEditView, MeasurementTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementTypeEditView.class).load();
        Parent root = viewTuple.getView();

        Stage dialog = new Stage();
        viewTuple.getCodeBehind().setStage(dialog);

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(MainApp.mainStage);
        dialog.setResizable(false);

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    public void testButtonClick() {
        viewModel.getTestCommand().execute();
    }

    public void typesTableViewClick() {
        viewModel.loadMeasurementsBySelectedType();
    }

    public void typesTableViewKeyUp(Event event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyCode keyCode = ((KeyEvent) event).getCode();

            if (keyCode.isNavigationKey() || keyCode.isArrowKey()) {
                viewModel.loadMeasurementsBySelectedType();
            }
        }
    }

    public void initialize() {
        initializeTypesTableView();
        initializeMeasurementsTableView();
    }
}
