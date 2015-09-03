package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 9/3/15.
 */
public class MeasurementEditViewModel implements ViewModel {

    private final ObjectProperty<MeasurementViewModel> measurement = new SimpleObjectProperty<>();

    private String cachedCode;
    private String cachedName;

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    MeasurementCatalogViewModel parentViewModel;

    public MeasurementEditViewModel() {

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                MeasurementViewModel m = measurement.getValue();

                if (m.getId() == 0) {
                    // add measurement
                    dbHelper.addMeasurement(m.getCode(), m.getName(), m.getShortName());
                } else {
                    // change exist measurement
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateMeasurement(m.getId(), m.getCode(), m.getName(), m.getShortName());
                }

                parentViewModel.loadMeasurementsBySelectedType();
            }
        });

        cancelCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

            }
        });
    }

    public MeasurementViewModel getMeasurement() {
        return measurement.get();
    }

    public ObjectProperty<MeasurementViewModel> measurementProperty() {
        return measurement;
    }

    public void setMeasurement(MeasurementViewModel measurement) {
        this.measurement.set(measurement);
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

    public MeasurementCatalogViewModel getParentViewModel() {
        return parentViewModel;
    }

    public void setParentViewModel(MeasurementCatalogViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }
}
