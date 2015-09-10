package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.CharacteristicTypeEditViewModel;

/**
 * Created by root on 9/7/15.
 */
public class CharacteristicTypeEditView implements FxmlView<CharacteristicTypeEditViewModel>, Returnable {
    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button yesBtn;

    @FXML
    private Button noBtn;

    @InjectViewModel
    CharacteristicTypeEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    public void saveButtonClicked() {
        viewModel.getSaveCommand().execute();
        dialogResult = DialogResult.OK;
        stage.close();
    }

    public void cancelButtonClicked() {
        viewModel.getCancelCommand().execute();
        dialogResult = DialogResult.CANCEL;
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public DialogResult getModalResult() {
        return dialogResult;
    }

    public void initialize() {
        codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
    }
}
