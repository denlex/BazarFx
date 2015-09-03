package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.defence.viewmodels.MeasurementEditViewModel;

/**
 * Created by root on 9/3/15.
 */
public class MeasurementEditView implements FxmlView<MeasurementEditViewModel>, Returnable {

    @FXML
    private TextField shortNameTextField;

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
}
