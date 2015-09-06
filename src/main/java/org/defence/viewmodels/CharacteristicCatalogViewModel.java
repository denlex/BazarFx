package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicType;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by root on 30.08.15.
 */
public class CharacteristicCatalogViewModel implements ViewModel {
    private final ListProperty<CharacteristicTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<CharacteristicViewModel> characteristics = new SimpleListProperty<>();

    private final ObjectProperty<CharacteristicTypeViewModel> selectedType = new SimpleObjectProperty<>();
    private final ObjectProperty<CharacteristicViewModel> selectedCharacteristic = new SimpleObjectProperty<>();

    private final DbHelper dbHelper = DbHelper.getInstance();
    private Command deleteCharacteristicCommand;

    public CharacteristicCatalogViewModel() {
        deleteCharacteristicCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление типа характеристики");
                alert.setHeaderText(null);
                alert.setContentText("Вы действительно хотите удалить тип:\nНаименование:   " + getSelectedCharacteristic().getName());

                ButtonType yes = new ButtonType("Удалить");
                ButtonType no = new ButtonType("Отмена");

                alert.getButtonTypes().setAll(yes, no);
                ((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == yes) {
                    dbHelper.deleteMeasurement(getSelectedCharacteristic().getId());
                    loadCharacteristicBySelectedType();
                }
            }
        });
        loadAllTypes();
    }

    public CharacteristicViewModel getSelectedCharacteristic() {
        return selectedCharacteristic.get();
    }

    public ObjectProperty<CharacteristicViewModel> selectedCharacteristicProperty() {
        return selectedCharacteristic;
    }

    public void setSelectedCharacteristic(CharacteristicViewModel selectedCharacteristic) {
        this.selectedCharacteristic.set(selectedCharacteristic);
    }

    public CharacteristicTypeViewModel getSelectedType() {
        return selectedType.get();
    }

    public ObjectProperty<CharacteristicTypeViewModel> selectedTypeProperty() {
        return selectedType;
    }

    public void setSelectedType(CharacteristicTypeViewModel selectedType) {
        this.selectedType.set(selectedType);
    }

    public ObservableList<CharacteristicViewModel> getCharacteristics() {
        return characteristics.get();
    }

    public ListProperty<CharacteristicViewModel> characteristicsProperty() {
        return characteristics;
    }

    public void setCharacteristics(ObservableList<CharacteristicViewModel> characteristics) {
        this.characteristics.set(characteristics);
    }

    public ObservableList<CharacteristicTypeViewModel> getTypes() {
        return types.get();
    }

    public ListProperty<CharacteristicTypeViewModel> typesProperty() {
        return types;
    }

    public void setTypes(ObservableList<CharacteristicTypeViewModel> types) {
        this.types.set(types);
    }

    public Command getDeleteCharacteristicCommand() {
        return deleteCharacteristicCommand;
    }

    public void setDeleteCharacteristicCommand(Command deleteCharacteristicCommand) {
        this.deleteCharacteristicCommand = deleteCharacteristicCommand;
    }

    public void loadAllTypes() {
        List<CharacteristicTypeViewModel> typeList = new LinkedList<>();

        for (CharacteristicType type : dbHelper.getAllCharacteristicTypes()) {
            typeList.add(new CharacteristicTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));
    }

    public void loadCharacteristicBySelectedType() {
        // if user selected any type in typeTableView
        if (selectedTypeProperty().get() != null) {
            List<CharacteristicViewModel> characteristicList = new LinkedList<>();

            for (Characteristic characteristic : dbHelper.getCharacteristicsByTypeId(selectedType.getValue().getId())) {
                characteristicList.add(new CharacteristicViewModel(characteristic));
            }

            characteristics.set(new ObservableListWrapper<>(characteristicList));
        }
    }
}
