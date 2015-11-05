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
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by root on 9/2/15.
 */
public class MeasurementCatalogViewModel implements ViewModel {
    private final ListProperty<MeasurementTypeViewModel> types = new SimpleListProperty<>();
    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ObjectProperty<MeasurementTypeViewModel> selectedType = new SimpleObjectProperty<>();
    private final ObjectProperty<MeasurementViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private final DbHelper dbHelper = DbHelper.getInstance();
    private Command deleteMeasurementCommand;
    private Command deleteTypeCommand;

    public MeasurementCatalogViewModel() {
		deleteTypeCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Удаление типа единицы измерения");
				alert.setHeaderText(null);
				alert.setContentText("Вы действительно хотите удалить тип единицы измерения:\nНаименование:   " +
						getSelectedType().getName());

				ButtonType yes = new ButtonType("Удалить");
				ButtonType no = new ButtonType("Отмена");

				alert.getButtonTypes().setAll(yes, no);
				((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == yes) {
					if (dbHelper.deleteMeasurementType(getSelectedType().getId())) {
						types.remove(getSelectedType());
					}
//					loadCharacteristicsBySelectedType();
				}
			}
		});

        deleteMeasurementCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление типа единицы измерения");
                alert.setHeaderText(null);
                alert.setContentText("Вы действительно хотите удалить тип:\nНаименование:   " + getSelectedMeasurement().getName());

                ButtonType yes = new ButtonType("Удалить");
                ButtonType no = new ButtonType("Отмена");

                alert.getButtonTypes().setAll(yes, no);
                ((Button) alert.getDialogPane().lookupButton(yes)).setDefaultButton(true);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == yes) {
                    dbHelper.deleteMeasurement(getSelectedMeasurement().getId());
                    loadMeasurementsBySelectedType();
                }
            }
        });
        loadAllTypes();
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

    public Command getDeleteMeasurementCommand() {
        return deleteMeasurementCommand;
    }

    public Command getDeleteTypeCommand() {
        return deleteTypeCommand;
    }

    public void setDeleteTypeCommand(Command deleteTypeCommand) {
        this.deleteTypeCommand = deleteTypeCommand;
    }

    public void loadAllTypes() {
        List<MeasurementTypeViewModel> typeList = new LinkedList<>();
        List<MeasurementType> allTypes = dbHelper.getAllMeasurementTypes();

        for (MeasurementType type : allTypes) {
            typeList.add(new MeasurementTypeViewModel(type));
        }

        types.set(new ObservableListWrapper<>(typeList));
    }

    public void loadMeasurementsBySelectedType() {
        // if user selected any type in typeTableView
        if (selectedTypeProperty().get() != null) {
            List<MeasurementViewModel> measurementList = new LinkedList<>();
            List<Measurement> filteredMeasurements = dbHelper.getMeasurementsByTypeId(selectedType.getValue().getId());

            for (Measurement measurement : filteredMeasurements) {
                measurementList.add(new MeasurementViewModel(measurement));
            }

            measurements.set(new ObservableListWrapper<>(measurementList));
        }
    }
}
