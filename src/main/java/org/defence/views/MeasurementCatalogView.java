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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.defence.MainApp;
import org.defence.viewmodels.MeasurementCatalogViewModel;
import org.defence.viewmodels.MeasurementTypeEditViewModel;
import org.defence.viewmodels.MeasurementTypeViewModel;
import org.defence.viewmodels.MeasurementViewModel;

/**
 * Created by root on 8/12/15.
 */
public class MeasurementCatalogView implements FxmlView<MeasurementCatalogViewModel> {
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
    private Button deleteTypeButton;

    @FXML
    private Button addMeasurementButton;

    @FXML
    private Button editMeasurementButton;

    @FXML
    private Button deleteMeasurementButton;

    @InjectViewModel
    MeasurementCatalogViewModel viewModel;

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
            viewModel.selectedMeasurementProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    public void addTypeButtonClick(Event event) {
        // TODO: Добавить позиционирование добавленой записи в таблице
        ViewTuple<MeasurementTypeEditView, MeasurementTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementTypeEditView.class).load();
        viewTuple.getViewModel().setParentViewModel(viewModel);
        Parent root = viewTuple.getView();

        Stage dialog = new Stage();
        viewTuple.getCodeBehind().setStage(dialog);

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(MainApp.mainStage);
        dialog.setResizable(false);

        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.ANY, event1 -> {
            if (event1.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });

        dialog.setScene(scene);
        dialog.showAndWait();

        // set current position in typesTableView
        if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
            int lastRowIndex = viewModel.getTypes().size() - 1;
            typesTableView.scrollTo(lastRowIndex);
            typesTableView.selectionModelProperty().get().select(lastRowIndex);
            typesTableView.requestFocus();
        }
    }

    public void editTypeButtonClick(Event event) {
        ViewTuple<MeasurementTypeEditView, MeasurementTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementTypeEditView.class).load();
        viewTuple.getViewModel().setParentViewModel(viewModel);
        Parent root = viewTuple.getView();

        Stage dialog = new Stage();
        viewTuple.getCodeBehind().setStage(dialog);

        viewTuple.getViewModel().idProperty().bindBidirectional(viewModel.selectedTypeProperty().get().idProperty());
        viewTuple.getViewModel().codeProperty().bindBidirectional(viewModel.selectedTypeProperty().get().codeProperty());
        viewTuple.getViewModel().nameProperty().bindBidirectional(viewModel.selectedTypeProperty().get().nameProperty());

        viewTuple.getViewModel().setCachedCode(viewModel.selectedTypeProperty().get().codeProperty().getValue());
        viewTuple.getViewModel().setCachedName(viewModel.selectedTypeProperty().get().nameProperty().getValue());

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(MainApp.mainStage);
        dialog.setResizable(false);

        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.ANY, event1 -> {
            if (event1.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });

        dialog.setScene(scene);
        dialog.showAndWait();

        typesTableView.requestFocus();
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

    public void typesTableViewMouseRelease(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                editTypeButtonClick(event);
            }
        }
    }

    public void initialize() {
        initializeTypesTableView();
        initializeMeasurementsTableView();
    }
}
