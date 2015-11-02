package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 9/3/15.
 */
public class MeasurementEditViewModel implements ViewModel {

	private final IntegerProperty idTemp = new SimpleIntegerProperty();
	private final StringProperty codeTemp = new SimpleStringProperty();
	private final StringProperty nameTemp = new SimpleStringProperty();
	private final StringProperty shortNameTemp = new SimpleStringProperty();

	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty shortName = new SimpleStringProperty();

	private String cachedCode;
	private String cachedName;

	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;
	private final DbHelper dbHelper = DbHelper.getInstance();

	private Command saveCommand;
	private Command cancelCommand;

	MeasurementCatalogViewModel parentViewModel;

	public MeasurementEditViewModel() {

		shownWindow = new SimpleObjectProperty<>(event -> {
			idTemp.setValue(id.getValue());
			codeTemp.setValue(code.getValue());
			nameTemp.setValue(name.getValue());
			shortNameTemp.setValue(shortName.getValue());
		});

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

				Integer typeId = parentViewModel.getSelectedType().getId();

				if (typeId == null || typeId == 0) {
					return;
				}

				if (idTemp.getValue() == 0) {
					// add measurement
					dbHelper.addMeasurement(typeId, codeTemp.getValue(), nameTemp.getValue(), shortNameTemp.getValue
							());
				} else {
					// change exist measurement
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					dbHelper.updateMeasurement(typeId, idTemp.getValue(), codeTemp.getValue(), nameTemp.getValue(),
							shortNameTemp.getValue());
				}

				parentViewModel.loadMeasurementsBySelectedType();
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

	public String getShortName() {
		return shortName.get();
	}

	public StringProperty shortNameProperty() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName.set(shortName);
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

	public MeasurementCatalogViewModel getParentViewModel() {
		return parentViewModel;
	}

	public void setParentViewModel(MeasurementCatalogViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
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

	public String getShortNameTemp() {
		return shortNameTemp.get();
	}

	public StringProperty shortNameTempProperty() {
		return shortNameTemp;
	}

	public void setShortNameTemp(String shortNameTemp) {
		this.shortNameTemp.set(shortNameTemp);
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
}
