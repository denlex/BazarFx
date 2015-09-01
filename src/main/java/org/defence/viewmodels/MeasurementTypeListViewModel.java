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
import javafx.scene.control.ButtonType;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by root on 8/20/15.
 */
public class MeasurementTypeListViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ObjectProperty<MeasurementTypeViewModel> type = new SimpleObjectProperty<>();

    private ObjectProperty<MeasurementTypeViewModel> selectedRow = new SimpleObjectProperty<>();

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command addTypeCommand;
    private Command deleteTypeCommand;

    public MeasurementTypeListViewModel() {
        List<MeasurementTypeViewModel> typeList = new ArrayList<>();
        for (MeasurementType type : dbHelper.importAllMeasurementTypes()) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));

        addTypeCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                MeasurementType typeEntity = new MeasurementType(type.get().getCode(), type.get().getName());

                if (dbHelper.exportMeasurementType(typeEntity) != false) {
                    types.get().add(new MeasurementTypeViewModel(typeEntity));
                    type.get().setCode(null);
                    type.get().setName(null);
                }
            }
        });

        deleteTypeCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                if (getSelectedRow() == null) {
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление типа единицы измерения");
                alert.setHeaderText(null);
                alert.setContentText("Вы действительно хотите удалить тип:\nНаименование:   " + getSelectedRow().getName());

                ButtonType yes = new ButtonType("Удалить");
                ButtonType no = new ButtonType("Отмена");

                alert.getButtonTypes().setAll(yes, no);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == yes) {
                    for (MeasurementTypeViewModel type : types) {
                        if (type.equals(getSelectedRow())) {
                            if (dbHelper.removeMeasurementType(type.getId())) {
                                types.get().remove(type);
                            }
                            break;
                        }
                    }
                }
            }
        });
    }

    public ObservableList<MeasurementTypeViewModel> getTypes() {
        return types.get();
    }

    public void setType(MeasurementTypeViewModel type) {

        this.type.set(type);
    }

    public MeasurementTypeViewModel getType() {
        return type.get();
    }

    public MeasurementTypeViewModel getSelectedRow() {
        return selectedRow.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedRowProperty() {
        return selectedRow;
    }

    public Command getAddTypeCommand() {
        return addTypeCommand;
    }

    public Command getDeleteTypeCommand() {
        return deleteTypeCommand;
    }
}
