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

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty shortName = new SimpleStringProperty();

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

                Integer typeId = parentViewModel.getSelectedType().getId();

                if (typeId == null || typeId == 0) {
                    return;
                }

                if (id.getValue() == 0) {
                    // add measurement
                    dbHelper.addMeasurement(typeId, code.getValue(), name.getValue(), shortName.getValue());
                } else {
                    // change exist measurement
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateMeasurement(typeId, id.getValue(), code.getValue(), name.getValue(), shortName.getValue());
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

    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
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
