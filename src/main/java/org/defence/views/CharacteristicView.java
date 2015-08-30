package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.defence.domain.entities.Characteristic;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodels.CharacteristicsViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicView implements FxmlView<CharacteristicsViewModel> {
    @FXML
    private Button okBtn;

    @FXML
    private TextField codeTextField;

    @FXML
    private TextField nameTextField;

    private DbHelper dbHelper = DbHelper.getInstance();

    private void setOkBtnRegisterEvents() {
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Characteristic characteristic = new Characteristic(codeTextField.getText(), nameTextField.getText());
                dbHelper.exportCharacteristic(characteristic);
            }
        });
    }

    public void initialize() {
        setOkBtnRegisterEvents();
    }
}
