package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Measurement;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 30.08.15.
 */
public class CharacteristicEditViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ObjectProperty<MeasurementViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private String cachedCode;
    private String cachedName;

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    CharacteristicCatalogViewModel parentViewModel;

    public CharacteristicEditViewModel() {

        List<Measurement> allMeasurements = dbHelper.getAllMeasurements();
        List<MeasurementViewModel> list = new LinkedList<>();

        for (Measurement measurement : allMeasurements) {
            list.add(new MeasurementViewModel(measurement));
        }


        measurements.setValue(new ObservableListWrapper<>(list));

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

                Integer typeId = parentViewModel.getSelectedType().getId();

                if (typeId == null || typeId == 0) {
                    return;
                }

                if (id.getValue() == 0) {
                    // add measurement
                    dbHelper.addCharacteristic(typeId, code.getValue(), name.getValue());
                } else {
                    // change exist measurement
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateCharacteristic(typeId, id.getValue(), code.getValue(), name.getValue());
                }

                parentViewModel.loadCharacteristicsBySelectedType();
            }
        });

        cancelCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

            }
        });
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCachedCode() {
        return cachedCode;
    }

    public void setCachedCode(String cachedCode) {
        this.cachedCode = cachedCode;
    }

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }

    public Command getSaveCommand() {
        return saveCommand;
    }

    public void setSaveCommand(Command saveCommand) {
        this.saveCommand = saveCommand;
    }

    public Command getCancelCommand() {
        return cancelCommand;
    }

    public void setCancelCommand(Command cancelCommand) {
        this.cancelCommand = cancelCommand;
    }

    public CharacteristicCatalogViewModel getParentViewModel() {
        return parentViewModel;
    }

    public void setParentViewModel(CharacteristicCatalogViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }

    public ObservableList<MeasurementViewModel> getMeasurements() {
        return measurements.get();
    }

    public ListProperty<MeasurementViewModel> measurementsProperty() {
        return measurements;
    }

    public void setMeasurements(ObservableList<MeasurementViewModel> measurements) {
        this.measurements.set(measurements);
    }

    public MeasurementViewModel getSelectedMeasurement() {
        return selectedMeasurement.get();
    }

    public ObjectProperty<MeasurementViewModel> selectedMeasurementProperty() {
        return selectedMeasurement;
    }

    public void setSelectedMeasurement(MeasurementViewModel selectedMeasurement) {
        this.selectedMeasurement.set(selectedMeasurement);
    }
}
