package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.Measurement;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final SetProperty<MeasurementViewModel> measurements = new SimpleSetProperty<>();
    private final StringProperty measurementText = new SimpleStringProperty();
    private final BooleanProperty isBelong = new SimpleBooleanProperty();

    public CharacteristicViewModel() {
    }

	public CharacteristicViewModel(Integer id, String code, String name, Set<Measurement> measurements) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);
//		this.measurements.setValue(new ObservableSetWrapper<>(characteristic.getMeasurements()));
		StringBuilder text = new StringBuilder();

		if (measurements != null) {
			Set<MeasurementViewModel> set = new LinkedHashSet<>();
			for (Measurement measurement : measurements) {
				set.add(new MeasurementViewModel(measurement));
				text.append(measurement.getShortName());
				text.append(";");
			}

			this.measurements.setValue(FXCollections.observableSet(set));
		}

		/*for (Measurement elem : measurements.getValue()) {
			text.append(elem.getShortName());
			text.append(";");
		}*/

		// if last index of text is ';', then delete this character
		if (text.length() > 1 && text.charAt(text.length() - 1) == ';') {
			text.deleteCharAt(text.length() - 1);
		}

		measurementText.setValue(text.toString());
	}

    public CharacteristicViewModel(Characteristic characteristic) {
		this(characteristic.getId(), characteristic.getCode(), characteristic.getName(), characteristic
				.getMeasurements());
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

	public ObservableSet<MeasurementViewModel> getMeasurements() {
		return measurements.get();
	}

	public SetProperty<MeasurementViewModel> measurementsProperty() {
		return measurements;
	}

	public void setMeasurements(ObservableSet<MeasurementViewModel> measurements) {
		this.measurements.set(measurements);
	}

	public String getMeasurementText() {
        return measurementText.get();
    }

    public StringProperty measurementTextProperty() {
        return measurementText;
    }

    public void setMeasurementText(String measurementText) {
        this.measurementText.set(measurementText);
    }

    public boolean getIsBelong() {
        return isBelong.get();
    }

    public BooleanProperty isBelongProperty() {
        return isBelong;
    }

    public void setIsBelong(boolean isBelong) {
        this.isBelong.set(isBelong);
    }
}
