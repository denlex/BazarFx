package org.defence.viewmodel;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;


/**
 * Created by root on 8/20/15.
 */
public class TypesViewModel {
    private final ListProperty<MeasurementTypeViewModel> types;

    private final ObjectProperty<EventHandler<ActionEvent>> btn;

    private final ObjectProperty<MeasurementTypeViewModel> type;

    public TypesViewModel(MeasurementTypeViewModel viewModel) {
        type = new SimpleObjectProperty<>();
        type.set(viewModel);

        ArrayList<MeasurementTypeViewModel> list = new ArrayList<>();

        types = new SimpleListProperty<>();
        types.setValue(new ObservableListWrapper<>(list));

        btn = new SimpleObjectProperty<>(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MeasurementTypeViewModel vm = new MeasurementTypeViewModel(type.get().getCode(), type.get().getName());

//                list.add(new MeasurementTypeViewModel());
                types.get().add(vm);

                System.out.println("code = " + type.get().getCode());
                System.out.println("name = " + type.get().getName());
                System.out.println("Button was fired~~~~!!!!");
            }
        });
    }

    public javafx.collections.ObservableList<MeasurementTypeViewModel> getTypes() {
        return types.get();
    }

    public ListProperty<MeasurementTypeViewModel> typesProperty() {
        return types;
    }

    public EventHandler<ActionEvent> getBtn() {
        return btn.get();
    }

    public ObjectProperty<EventHandler<ActionEvent>> btnProperty() {
        return btn;
    }

    public MeasurementTypeViewModel getType() {
        return type.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> typeProperty() {
        return type;
    }

    /*public void setType(MeasurementTypeViewModel type) {
        this.type.set(type);
    }*/

}
