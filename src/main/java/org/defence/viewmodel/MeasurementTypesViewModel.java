package org.defence.viewmodel;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;


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

        types = new SimpleListProperty<>(new ObservableListWrapper<>(new ArrayList<>()));

//        selectedRow = new SimpleObjectProperty<>();

        addBtn = new SimpleObjectProperty<>(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dbHelper.exportMeasurementType(new MeasurementType(type.get().getCode(), type.get().getName()));
                types.get().add(new MeasurementTypeViewModel(viewModel));
            }
        });

        deleteBtn = new SimpleObjectProperty<>(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

//                dbHelper.exportMeasurementType(new MeasurementType(type.get().getCode(), type.get().getName()));
//                types.get().add(new MeasurementTypeViewModel(type.get().getCode(), type.get().getName()));
                System.out.println(selectedRow.get().getCode());
//                System.out.println(selectedRow.get().getSelectedItem().getName());
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

    /*public void setType(MeasurementTypeViewModel type) {
        this.type.set(type);
    }*/

    public MeasurementTypeViewModel getSelectedRow() {
        return selectedRow.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedRowProperty() {
        return selectedRow;
    }

}
