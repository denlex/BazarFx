package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 8/31/15.
 */
public class MeasurementTypeEditViewModel implements ViewModel {

    /*private final IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty code = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();*/

    private final ObjectProperty<MeasurementTypeViewModel> type = new SimpleObjectProperty<>();

    private String cachedCode;
    private String cachedName;

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    private MeasurementCatalogViewModel parentViewModel;

    public MeasurementTypeEditViewModel() {

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                MeasurementTypeViewModel t = type.getValue();

                if (t.getId() == 0) {
                    // add measurement type
                    if (dbHelper.addMeasurementType(t.getCode(), t.getName())) {
                        if (parentViewModel != null) {
                            System.out.println(parentViewModel.getClass().getName());
                        }
                    }
                } else {
                    // change exist measurement type
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateMeasurementType(t.getId(), t.getCode(), t.getName());
                }

                parentViewModel.loadAllTypes();

                /*
                * simple addition to typesList
                if (id.getValue() == 0) {
                    parentViewModel.getTypes().add(new MeasurementTypeViewModel(code.getValue(), name.getValue()));
                } else {
                    parentViewModel.getTypes().stream().findFirst().filter(measurementTypeViewModel -> measurementTypeViewModel.getId() == id.getValue());
                }*/
            }
        });

        cancelCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                MeasurementTypeViewModel t = type.get();

                // if dialog is opened in modification mode (editing)
                if (t.idProperty().getValue() != null) {
                    t.setCode(cachedCode);
                    t.setName(cachedName);
                }
            }
        });
    }

    public Command getSaveCommand() {
        return saveCommand;
    }

    public Command getCancelCommand() {
        return cancelCommand;
    }

    public MeasurementTypeViewModel getType() {
        return type.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> typeProperty() {
        return type;
    }

    public void setType(MeasurementTypeViewModel type) {
        this.type.set(type);
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

    public MeasurementCatalogViewModel getParentViewModel() {
        return parentViewModel;
    }

    public void setParentViewModel(MeasurementCatalogViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }
}
