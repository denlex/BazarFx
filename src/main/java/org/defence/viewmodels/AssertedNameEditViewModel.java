package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/16/15.
 */
public class AssertedNameEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ObjectProperty<DescriptionFormatViewModel> selectedFormat = new SimpleObjectProperty<>();
	private AssertedNameViewModel editedName;

	DbHelper dbHelper = DbHelper.getInstance();

	MainViewModel parentViewModel;
	Command saveCommand;

	public AssertedNameEditViewModel() {
		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				DescriptionFormatViewModel format = parentViewModel.getSelectedFormat();
				System.out.println(format.getName());

				// if user choose descriptionFormat for adding assertedName, else - edit existed assertedName
				if (format == null) {
					editedName = new AssertedNameViewModel(dbHelper.updateAssertedName(id.getValue(), code.getValue(),
							name.getValue()));

					// TODO: неплохо было бы перенести инициализацию в класс AssertedName
					parentViewModel.getSelectedName().setId(id.getValue());
					parentViewModel.getSelectedName().setCode(code.getValue());
					parentViewModel.getSelectedName().setName(name.getValue());
				} else {

					editedName = new AssertedNameViewModel(dbHelper.addAssertedName(format.getId(), code.getValue(),
							name.getValue()));

					// add new assertedName
					if (format.getAssertedNames() == null) {
						List<AssertedNameViewModel> list = new ArrayList<>();
						list.add(editedName);

						format.assertedNamesProperty().setValue(FXCollections.observableList(list));
					} else {
						format.getAssertedNames().add(editedName);
					}
				}


				/*Integer formatId = parentViewModel.getSelectedFormat().getId();

				if (formatId == null || formatId == 0) {
					return;
				}

				System.out.println("id = " + id);
				System.out.println("code = " + code);
				System.out.println("name = " + name);

				if (id.getValue() == 0) {
					// add assertedName
					editedName = new AssertedNameViewModel(dbHelper.addAssertedName(formatId, code.getValue(), name
							.getValue()));
					parentViewModel.getSelectedFormat().getAssertedNames().add(editedName);
				} else {
					// change exist assertedName
					// TODO: Сделать проверку на пустой ввод данных об УН
					editedName = new AssertedNameViewModel(dbHelper.updateAssertedName(formatId, id.getValue(), code
							.getValue(), name.getValue()));

//					parentViewModel.getSelectedFormat().getAssertedNames()
				}*/

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

	public AssertedNameViewModel getEditedName() {
		return editedName;
	}
}
