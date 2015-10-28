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
 * Created by root on 10/27/15.
 */
public class CatalogClassEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private CatalogClassViewModel editedClass;

	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private Command saveCommand;
	private final DbHelper dbHelper = DbHelper.getInstance();

	private MainViewModel parentViewModel;

	public CatalogClassEditViewModel() {

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

				CatalogClassViewModel selectedClass = parentViewModel.getSelectedClass();

				// if window was opened in adding mode
				if (selectedClass == null) {
					editedClass = new CatalogClassViewModel(dbHelper.addCatalogClass(code.getValue(), name
							.getValue()));
					parentViewModel.getClasses().add(editedClass);
				} else {
					// change exist descriptionFormat
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					editedClass = new CatalogClassViewModel(dbHelper.updateCatalogClass(id.getValue(), code
							.getValue(), name.getValue()));
					selectedClass.setCode(editedClass.getCode());
					selectedClass.setName(editedClass.getName());
					selectedClass.setFormats(editedClass.getFormats());
				}
				// TODO: реализовать возможность обновления списка наборов характеристик

//				parentViewModel.displayFormats();
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

	public Command getSaveCommand() {
		return saveCommand;
	}

	public void setSaveCommand(Command saveCommand) {
		this.saveCommand = saveCommand;
	}

	public MainViewModel getParentViewModel() {
		return parentViewModel;
	}

	public void setParentViewModel(MainViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
	}
}
