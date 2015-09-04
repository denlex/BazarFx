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
import org.defence.viewmodels.*;

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
    private TableColumn<MeasurementViewModel, String> shortNameTableColumn;

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
        shortNameTableColumn.setCellValueFactory(new PropertyValueFactory("shortName"));

        measurementsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedMeasurementProperty().unbind();
            viewModel.selectedMeasurementProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    public void addTypeButtonClicked(Event event) {
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

    public void editTypeButtonClicked(Event event) {
        ViewTuple<MeasurementTypeEditView, MeasurementTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementTypeEditView.class).load();
        viewTuple.getViewModel().setParentViewModel(viewModel);

        Property<MeasurementTypeViewModel> t = viewModel.selectedTypeProperty();
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
        scene.addEventHandler(KeyEvent.ANY, event1 -> {
            if (event1.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });


        int selectedItemIndex = typesTableView.getSelectionModel().getFocusedIndex();

        dialog.setScene(scene);
        dialog.showAndWait();

        // if types were modified, then refresh typesTableView
        if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
            typesTableView.refresh();
        }

        typesTableView.scrollTo(selectedItemIndex);
        typesTableView.selectionModelProperty().get().select(selectedItemIndex);
        typesTableView.requestFocus();
    }

    public void addMeasurementButtonClicked(Event event) {
        ViewTuple<MeasurementEditView, MeasurementEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementEditView.class).load();
        viewTuple.getViewModel().setParentViewModel(viewModel);
        Parent root = viewTuple.getView();

        Stage dialog = new Stage();
        viewTuple.getCodeBehind().setStage(dialog);

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(MainApp.mainStage);
        dialog.setResizable(false);

        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });

        dialog.setScene(scene);
        dialog.showAndWait();

        // set current position in measurementsTableView
        if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
            int lastRowIndex = viewModel.getMeasurements().size() - 1;
            measurementsTableView.scrollTo(lastRowIndex);
            measurementsTableView.selectionModelProperty().get().select(lastRowIndex);
            measurementsTableView.requestFocus();
        }
    }

    public void editMeasurementButtonClicked(Event event) {
        Property<MeasurementViewModel> m = viewModel.selectedMeasurementProperty();

        // if item in measurementTableView was not selected
        if (m.getValue() == null) {
            return;
        }

        ViewTuple<MeasurementEditView, MeasurementEditViewModel> viewTuple = FluentViewLoader.fxmlView(MeasurementEditView.class).load();
        viewTuple.getViewModel().setParentViewModel(viewModel);
        Parent root = viewTuple.getView();

        Stage dialog = new Stage();
        viewTuple.getCodeBehind().setStage(dialog);


        viewTuple.getViewModel().idProperty().bindBidirectional(m.getValue().idProperty());
        viewTuple.getViewModel().codeProperty().bindBidirectional(m.getValue().codeProperty());
        viewTuple.getViewModel().nameProperty().bindBidirectional(m.getValue().nameProperty());
        viewTuple.getViewModel().shortNameProperty().bindBidirectional(m.getValue().shortNameProperty());
        /*viewTuple.getViewModel().setCachedCode(m.getValue().codeProperty().getValue());
        viewTuple.getViewModel().setCachedName(m.getValue().nameProperty().getValue());*/

        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(MainApp.mainStage);
        dialog.setResizable(false);

        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });

        int selectedItemIndex = measurementsTableView.getSelectionModel().getFocusedIndex();

        dialog.setScene(scene);
        dialog.showAndWait();

        // if measurements were modified, then refresh measurementsTableView
        if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
            measurementsTableView.refresh();
        }

        measurementsTableView.scrollTo(selectedItemIndex);
        measurementsTableView.selectionModelProperty().get().select(selectedItemIndex);
        measurementsTableView.requestFocus();
    }

    public void deleteMeasurementButtonClicked(Event event) {
        viewModel.getDeleteMeasurementCommand().execute();
        measurementsTableView.requestFocus();
    }

    public void typesTableViewClicked() {
        viewModel.loadMeasurementsBySelectedType();
    }

    public void typesTableViewMouseReleased(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                editTypeButtonClicked(event);
            }
        }
    }

    public void measurementsTableViewMouseRelease(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                editMeasurementButtonClicked(event);
            }
        }
    }

    public void typesTableViewKeyReleased(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode.isNavigationKey() || keyCode.isArrowKey()) {
            viewModel.loadMeasurementsBySelectedType();
            return;
        }
    }

    public void typesTableViewKeyPressed(KeyEvent event) {
        /*if (event.getCode() == KeyCode.DELETE) {
            deleteMeasurementButtonClicked(event);
        }*/

        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.INSERT) {
            addTypeButtonClicked(event);
            return;
        }

        if (keyCode == KeyCode.ENTER) {
            editTypeButtonClicked(event);
            return;
        }
    }

    public void measurementsTableViewKeyPressed(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.INSERT) {
            addMeasurementButtonClicked(event);
        }

        if (keyCode == KeyCode.DELETE) {
            deleteMeasurementButtonClicked(event);
        }

        if (keyCode == KeyCode.ENTER) {
            editMeasurementButtonClicked(event);
        }
    }

    public void initialize() {
        initializeTypesTableView();
        initializeMeasurementsTableView();

        editTypeButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());
        deleteTypeButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());

        addMeasurementButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());
        editMeasurementButton.disableProperty().bind(viewModel.selectedMeasurementProperty().isNull());
        deleteMeasurementButton.disableProperty().bind(viewModel.selectedMeasurementProperty().isNull());
    }
}
