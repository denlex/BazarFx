package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 9/16/15.
 */
public class AssertedNameEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ObjectProperty<DescriptionFormatViewModel> selectedFormat = new SimpleObjectProperty<>();

	DbHelper dbHelper = DbHelper.getInstance();

	MainViewModel parentViewModel;
	Command saveCommand;

	public AssertedNameEditViewModel() {
		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Integer formateId = parentViewModel.getSelectedFormat().getId();

				if (formateId == null || formateId == 0) {
					return;
				}

				if (id.getValue() == 0) {
					// add assertedName
					dbHelper.addAssertedName(formateId, code.getValue(), name.getValue());
				} else {
					// change exist assertedName
					// TODO: Сделать проверку на пустой ввод данных об УН
					dbHelper.updateAssertedName(formateId, id.getValue(), code.getValue(), name.getValue());
				}

//				parentViewModel.loadMeasurementsBySelectedType();
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

	public void setParentViewModel(MainViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
	}

	public Command getSaveCommand() {
		return saveCommand;
	}

	public void setSaveCommand(Command saveCommand) {
		this.saveCommand = saveCommand;
	}
}
