package org.defence.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.defence.domain.entities.Characteristic;
import org.defence.infrastructure.DbHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicsController implements Initializable {
    @FXML
    private TableView<Characteristic> characteristicsTableView;

    DbHelper dbHelper = new DbHelper();

    private void characteristicsTableViewRegisterEvents() {
    }

    private ObservableList<Characteristic> getAllCharacteristics() {
        return new ObservableListWrapper<Characteristic>(dbHelper.importAllCharacteristics());
    }

    public void initialize(URL location, ResourceBundle resources) {
        characteristicsTableView.setItems(getAllCharacteristics());

        characteristicsTableViewRegisterEvents();
    }
}
