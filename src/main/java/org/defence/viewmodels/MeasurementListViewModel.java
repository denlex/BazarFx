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
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 9/1/15.
 */
public class MeasurementListViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ObjectProperty<MeasurementTypeViewModel> selectedType = new SimpleObjectProperty<>();
    private final ObjectProperty<MeasurementViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private DbHelper dbHelper = DbHelper.getInstance();

    private Command addTypeCommand;
    private Command deleteTypeCommand;
    private Command testCommand;

    public MeasurementListViewModel() {
        List<MeasurementTypeViewModel> typeList = new ArrayList<>();
        for (MeasurementType type : dbHelper.getAllMeasurementTypes()) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));

        loadMeasurementsBySelectedType();

        testCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
//                JOptionPane.showMessageDialog(null, selectedType.get().codeProperty() + "\n" + selectedType.get().nameProperty());
                List<Measurement> list = dbHelper.getMeasurementsByMeasurementTypeId(selectedType.getValue().getId());
                StringBuilder out = new StringBuilder();

                for (Measurement elem : list) {
                    out.append(elem.getName() + "\n");
                }

                JOptionPane.showMessageDialog(null, out.toString());
            }
        });

        /*
        addTypeCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                MeasurementType typeEntity = new MeasurementType(type.get().getCode(), type.get().getName());

                if (dbHelper.addMeasurementType(typeEntity) != false) {
                    types.get().add(new MeasurementTypeViewModel(typeEntity));
                    type.get().setCode(null);
                    type.get().setName(null);
                }
            }
        });

        deleteTypeCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                if (getSelectedType() == null) {
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
        */
    }

    public void loadMeasurementsBySelectedType() {
        if (selectedTypeProperty().get() != null) {
            List<MeasurementViewModel> measurementList = new LinkedList<>();

            for (Measurement measurement : dbHelper.getMeasurementsByMeasurementTypeId(selectedType.getValue().getId())) {
                measurementList.add(new MeasurementViewModel(measurement));
            }

            measurements.set(new ObservableListWrapper<>(measurementList));
        }
    }

    private ObservableList<MeasurementType> getAllMeasurementTypes() {
        return new ObservableListWrapper<>(dbHelper.getAllMeasurementTypes());
    }

    private int importMeasurementTypesIntoComboBox() {
        for (MeasurementType measurementType : getAllMeasurementTypes()) {

//            measurementTypeComboBox.getItems().add(measurementType);
        }

//        measurementTypeComboBox.getItems().

//        return measurementTypeComboBox.getItems().size();
        return 0;
    }

    public ObservableList<MeasurementTypeViewModel> getTypes() {
        return types.get();
    }

    public ListProperty<MeasurementTypeViewModel> typesProperty() {
        return types;
    }

    public void setTypes(ObservableList<MeasurementTypeViewModel> types) {
        this.types.set(types);
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

    public MeasurementTypeViewModel getSelectedType() {
        return selectedType.get();
    }

    public ObjectProperty<MeasurementTypeViewModel> selectedTypeProperty() {
        return selectedType;
    }

    public void setSelectedType(MeasurementTypeViewModel selectedType) {
        this.selectedType.set(selectedType);
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

    public Command getTestCommand() {
        return testCommand;
    }
}