package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.defence.viewmodels.MeasurementTypeViewModel;
import org.defence.viewmodels.MeasurementTypesViewModel;

import javax.swing.*;

/**
 * Created by root on 8/12/15.
 */
public class MeasurementTypesView implements FxmlView<MeasurementTypesViewModel> {
    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    TableView<MeasurementTypeViewModel> typesTableView;

    @FXML
    TableColumn<MeasurementTypeViewModel, IntegerProperty> idTableColumn;

    @FXML
    TableColumn<MeasurementTypeViewModel, StringProperty> codeTableColumn;

    @FXML
    TableColumn<MeasurementTypeViewModel, StringProperty> nameTableColumn;

    @InjectViewModel
    MeasurementTypesViewModel viewModel;

    public void initialize() {

        /*UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change change) -> {
            System.out.println(change.getControlNewText());

            if (typeViewModel.validAgeInput(change.getControlNewText())) {
                // accept
                return change ;
            } else {
                // reject
                return null ;
            }
        };

        TextFormatter<Integer> ageFormatter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
        age.setTextFormatter(ageFormatter);
        ageFormatter.valueProperty().bindBidirectional(typeViewModel.ageProperty().asObject());*/

        MeasurementTypeViewModel typeViewModel = new MeasurementTypeViewModel();
        codeTextField.textProperty().bindBidirectional(typeViewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(typeViewModel.nameProperty());

        viewModel.setType(typeViewModel);

        // make addBtn disable if codeTextField and nameTextField are empty
        addBtn.disableProperty().bind(typeViewModel.isActionPossibleProperty());

        // binding ObservableCollection "Types" on the TableView
        typesTableView.itemsProperty().bindBidirectional(new SimpleObjectProperty<>(viewModel.getTypes()));

        initializeTableView();

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JOptionPane.showMessageDialog(null, typesTableView.getItems().get(0).getName());
            }
        });
    }

    private void initializeTableView() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));

        typesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedRowProperty().unbind();
            viewModel.selectedRowProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

    public void deleteButtonClick() {
        viewModel.getDeleteTypeCommand().execute();
    }

    public void addButtonClick() {
        viewModel.getAddTypeCommand().execute();
        int lastRowIndex = viewModel.getTypes().size() - 1;
        typesTableView.scrollTo(lastRowIndex);
        typesTableView.selectionModelProperty().get().select(lastRowIndex);
        codeTextField.requestFocus();
    }
}
