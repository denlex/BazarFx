package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.Measurement;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 30.08.15.
 */
public class CharacteristicEditViewModel implements ViewModel {
	private final IntegerProperty idTemp = new SimpleIntegerProperty();
	private final StringProperty codeTemp = new SimpleStringProperty();
	private final StringProperty nameTemp = new SimpleStringProperty();

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
//    private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();

    private final ListProperty<MeasurementViewModel> allMeasurements = new SimpleListProperty<>();
    private final ObjectProperty<MeasurementViewModel> selectedMeasurement = new SimpleObjectProperty<>();

    private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

    private String cachedCode;
    private String cachedName;

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    private CharacteristicCatalogViewModel parentViewModel;

    public CharacteristicEditViewModel() {

        List<Measurement> allMeasurementsFromDb = dbHelper.getAllMeasurements();
        List<MeasurementViewModel> list = new LinkedList<>();

        for (Measurement measurement : allMeasurementsFromDb) {
            list.add(new MeasurementViewModel(measurement));
        }

        allMeasurements.setValue(new ObservableListWrapper<>(list));

        // filling checkBoxes in table with corresponded values
        shownWindow = new SimpleObjectProperty<>(event -> {
            idTemp.setValue(id.getValue());
            codeTemp.setValue(code.getValue());
            nameTemp.setValue(name.getValue());

            // if modification open window mode (not creation new characteristic)
            if (id != null && id.getValue() != 0) {
                Characteristic current = dbHelper.getCharacteristicById(id.getValue());
                for (Measurement elem : current.getMeasurements()) {
                    MeasurementViewModel m = allMeasurements.getValue().stream().filter(p -> p.getId() == elem.getId()).findFirst().get();

                    m.setIsBelong(true);
                }
            } else {

            }
        });

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

                Integer typeId = parentViewModel.getSelectedType().getId();

                if (typeId == null || typeId == 0) {
                    return;
                }

                List<Integer> measurementIdList = new LinkedList<>();
                for (MeasurementViewModel elem : allMeasurements) {
                    if (elem.getIsBelong()) {
                        measurementIdList.add(elem.getId());
                    }
                }

                if (idTemp.getValue() == 0) {
                    // add measurement
                    dbHelper.addCharacteristic(typeId, codeTemp.getValue(), nameTemp.getValue(), measurementIdList);
                } else {
                    // change exist measurement
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateCharacteristic(typeId, idTemp.getValue(), codeTemp.getValue(), nameTemp.getValue(), measurementIdList);
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

    /*public ObservableList<MeasurementViewModel> getMeasurements() {
        return measurements.get();
    }

    public ListProperty<MeasurementViewModel> measurementsProperty() {
        return measurements;
    }

    public void setMeasurements(ObservableList<MeasurementViewModel> measurements) {
        this.measurements.set(measurements);
    }*/

    public MeasurementViewModel getSelectedMeasurement() {
        return selectedMeasurement.get();
    }

    public ObjectProperty<MeasurementViewModel> selectedMeasurementProperty() {
        return selectedMeasurement;
    }

    public void setSelectedMeasurement(MeasurementViewModel selectedMeasurement) {
        this.selectedMeasurement.set(selectedMeasurement);
    }

    public ObservableList<MeasurementViewModel> getAllMeasurements() {
        return allMeasurements.get();
    }

    public ListProperty<MeasurementViewModel> allMeasurementsProperty() {
        return allMeasurements;
    }

    public void setAllMeasurements(ObservableList<MeasurementViewModel> allMeasurements) {
        this.allMeasurements.set(allMeasurements);
    }

    public EventHandler<WindowEvent> getShownWindow() {
        return shownWindow.get();
    }

    public ObjectProperty<EventHandler<WindowEvent>> shownWindowProperty() {
        return shownWindow;
    }

    public void setShownWindow(EventHandler<WindowEvent> shownWindow) {
        this.shownWindow.set(shownWindow);
    }

	public int getIdTemp() {
		return idTemp.get();
	}

	public IntegerProperty idTempProperty() {
		return idTemp;
	}

	public void setIdTemp(int idTemp) {
		this.idTemp.set(idTemp);
	}

	public String getCodeTemp() {
		return codeTemp.get();
	}

	public StringProperty codeTempProperty() {
		return codeTemp;
	}

	public void setCodeTemp(String codeTemp) {
		this.codeTemp.set(codeTemp);
	}

	public String getNameTemp() {
		return nameTemp.get();
	}

	public StringProperty nameTempProperty() {
		return nameTemp;
	}

	public void setNameTemp(String nameTemp) {
		this.nameTemp.set(nameTemp);
	}
}
