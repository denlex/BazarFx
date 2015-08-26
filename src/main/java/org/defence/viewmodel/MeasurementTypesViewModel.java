package org.defence.viewmodel;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class MeasurementTypesViewModel {
    private final ListProperty<MeasurementTypeViewModel> types;

    private final ObjectProperty<EventHandler<ActionEvent>> addBtn;
    private final ObjectProperty<EventHandler<ActionEvent>> deleteBtn;

    private final ObjectProperty<MeasurementTypeViewModel> type;

    private ObjectProperty<MeasurementTypeViewModel> selectedRow = new SimpleObjectProperty<>();

    private final DbHelper dbHelper = DbHelper.getInstance();

    public MeasurementTypesViewModel(MeasurementTypeViewModel viewModel) {
        type = new SimpleObjectProperty<>(viewModel);

        List<MeasurementTypeViewModel> typeList = new ArrayList<>();
        for (MeasurementType type : dbHelper.importAllMeasurementTypes()) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types = new SimpleListProperty<>(new ObservableListWrapper(typeList));

        addBtn = new SimpleObjectProperty<>(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MeasurementType typeEntity = new MeasurementType(type.get().getCode(), type.get().getName());

                if (dbHelper.exportMeasurementType(typeEntity) != false) {
                    types.get().add(new MeasurementTypeViewModel(typeEntity));
                    type.get().setCode(null);
                    type.get().setName(null);
                }
            }
        });

        deleteBtn = new SimpleObjectProperty<>(event -> {

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
        });
    }

    public javafx.collections.ObservableList<MeasurementTypeViewModel> getTypes() {
        return types.get();
    }

    public ListProperty<MeasurementTypeViewModel> typesProperty() {
        return types;
    }

    public EventHandler<ActionEvent> getAddBtn() {
        return addBtn.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> addBtnProperty() {
        return addBtn;
    }

    public MeasurementTypeViewModel getType() {
        return type.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> typeProperty() {
        return type;
    }

    public EventHandler<ActionEvent> getDeleteBtn() {
        return deleteBtn.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> deleteBtnProperty() {
        return deleteBtn;
    }

    public MeasurementTypeViewModel getSelectedRow() {
        return selectedRow.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedRowProperty() {
        return selectedRow;
    }

    public void setSelectedRow(MeasurementTypeViewModel selectedRow) {
        this.selectedRow.set(selectedRow);
    }
}
