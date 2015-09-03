package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.MeasurementEditViewModel;

/**
 * Created by root on 9/3/15.
 */
public class MeasurementEditView implements FxmlView<MeasurementEditViewModel>, Returnable {

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField shortNameTextField;

    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    @InjectViewModel
    MeasurementEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    public MeasurementEditView() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public DialogResult getModalResult() {
        return dialogResult;
    }

    public void saveButtonClick() {
        viewModel.getSaveCommand().execute();
        dialogResult = DialogResult.OK;
        stage.close();
    }

    public void cancelButtonClick() {
        viewModel.getCancelCommand().execute();
        dialogResult = DialogResult.CANCEL;
        stage.close();
    }

    public void initialize() {

        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
        shortNameTextField.textProperty().bindBidirectional(viewModel.shortNameProperty());
    }
}
