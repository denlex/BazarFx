package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.MeasurementTypeEditViewModel;

/**
 * Created by root on 8/31/15.
 */
public class MeasurementTypeEditView implements FxmlView<MeasurementTypeEditViewModel> {

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    @InjectViewModel
    MeasurementTypeEditViewModel viewModel;

    private Stage stage;

    public void initialize() {

        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
    }

    public void saveButtonClick() {
        viewModel.getSaveCommand().execute();
        stage.close();
    }

    public void cancelButtonClick() {
        viewModel.getCancelCommand().execute();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
