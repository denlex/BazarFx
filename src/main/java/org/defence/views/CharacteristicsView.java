package org.defence.views;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.FxmlView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.defence.domain.entities.Characteristic;
import org.defence.infrastructure.DbHelper;
import org.defence.viewmodels.CharacteristicViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicsView implements FxmlView<CharacteristicViewModel> {
    @FXML
    private TableView<Characteristic> characteristicsTableView;

    DbHelper dbHelper = DbHelper.getInstance();

    private void characteristicsTableViewRegisterEvents() {
    }

    private ObservableList<Characteristic> getAllCharacteristics() {
        return new ObservableListWrapper<Characteristic>(dbHelper.importAllCharacteristics());
    }

    public void initialize() {
        characteristicsTableView.setItems(getAllCharacteristics());

        characteristicsTableViewRegisterEvents();
    }
}
