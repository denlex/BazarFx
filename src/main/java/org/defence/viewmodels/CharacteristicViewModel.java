package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 06.09.15.
 */
public class CharacteristicViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<MeasurementViewModel> measurements = new SimpleListProperty<>();
	private final StringProperty measurementText = new SimpleStringProperty();
	private final BooleanProperty isBelong = new SimpleBooleanProperty();

	public CharacteristicViewModel() {
	}

	public CharacteristicViewModel(Integer id, String code, String name, List<Measurement> measurements) {
		this.id.setValue(id);
		this.code.setValue(code);
		this.name.setValue(name);
//		this.measurements.setValue(new ObservableSetWrapper<>(characteristic.getMeasurements()));
		StringBuilder text = new StringBuilder();

		if (measurements != null) {
			List<MeasurementViewModel> list = new ArrayList<>();
			for (Measurement measurement : measurements) {
				list.add(new MeasurementViewModel(measurement));
				text.append(measurement.getShortName());
				text.append(";");
			}

			this.measurements.setValue(FXCollections.observableArrayList(list));
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

	public ObservableList<MeasurementViewModel> getMeasurements() {
		return measurements.get();
	}

	public ListProperty<MeasurementViewModel> measurementsProperty() {
		return measurements;
	}

	public void setMeasurements(ObservableList<MeasurementViewModel> measurements) {
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

	public Characteristic toModel() {
		Characteristic result = new Characteristic();

		result.setId(id.getValue());
		result.setName(name.getValue());
		result.setCode(code.getValue());
		result.setMeasurements(measurements.stream().map(MeasurementViewModel::toModel).collect(Collectors.toList()));

		return result;
	}
}
