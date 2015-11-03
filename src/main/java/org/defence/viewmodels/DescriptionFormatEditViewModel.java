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
import org.defence.domain.entities.DescriptionFormat;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatEditViewModel implements ViewModel {

	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();

	private final ListProperty<CharacteristicViewModel> allCharacteristics = new SimpleListProperty<>();
	private final ObjectProperty<CharacteristicViewModel> selectedCharacteristic = new SimpleObjectProperty<>();
	private DescriptionFormatViewModel editedFormat;

	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private Command saveCommand;
	private final DbHelper dbHelper = DbHelper.getInstance();

	private MainViewModel parentViewModel;

	public DescriptionFormatEditViewModel() {
		List<Characteristic> allCharacteristicsFromDb = dbHelper.getAllCharacteristics();
		List<CharacteristicViewModel> list = new LinkedList<>();

		for (Characteristic characteristic : allCharacteristicsFromDb) {
			list.add(new CharacteristicViewModel(characteristic));
		}

		allCharacteristics.setValue(new ObservableListWrapper<>(list));

		// filling checkBoxes in table with corresponded values
		shownWindow = new SimpleObjectProperty<>(event -> {
			System.out.println("Inside ShownWindow");

			// if modification open window mode (not creation new description format)
			if (id != null && id.getValue() != 0) {
				DescriptionFormat current = dbHelper.getDescriptionFormatById(id.getValue());
				for (Characteristic elem : current.getCharacteristics()) {
					CharacteristicViewModel m = allCharacteristics.getValue().stream().filter(p -> p.getId() ==
							elem.getId()).findFirst().get();

					m.setIsBelong(true);
				}
			} else {
				loadAllCharacteristics();
			}
		});

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				// TODO: Заменить на id выбранного формата описания
				List<Integer> characteristicIdList = new LinkedList<>();
				for (CharacteristicViewModel elem : allCharacteristics) {
					if (elem.getIsBelong()) {
						characteristicIdList.add(elem.getId());
					}
				}

				DescriptionFormatViewModel selectedFormat = parentViewModel.getSelectedFormat();

				// if window was opened in adding mode
				if (selectedFormat == null) {
					editedFormat = new DescriptionFormatViewModel(dbHelper.addDescriptionFormat(parentViewModel
							.getSelectedClass().getId(), code.getValue(), name.getValue(), characteristicIdList));

					parentViewModel.getSelectedClass().getFormats().add(editedFormat);

				} else {
					// change exist descriptionFormat
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					editedFormat = new DescriptionFormatViewModel(dbHelper.updateDescriptionFormat(id.getValue(), code
							.getValue(), name.getValue(), characteristicIdList));
					selectedFormat.setCode(editedFormat.getCode());
					selectedFormat.setName(editedFormat.getName());
					selectedFormat.setCharacteristics(editedFormat.getCharacteristics());
					selectedFormat.setAssertedNames(editedFormat.getAssertedNames());
				}
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

	public ObservableList<CharacteristicViewModel> getAllCharacteristics() {
		return allCharacteristics.get();
	}

	public ListProperty<CharacteristicViewModel> allCharacteristicsProperty() {
		return allCharacteristics;
	}

	public void setAllCharacteristics(ObservableList<CharacteristicViewModel> allCharacteristics) {
		this.allCharacteristics.set(allCharacteristics);
	}

	public CharacteristicViewModel getSelectedCharacteristic() {
		return selectedCharacteristic.get();
	}

	public ObjectProperty<CharacteristicViewModel> selectedCharacteristicProperty() {
		return selectedCharacteristic;
	}

	public void setSelectedCharacteristic(CharacteristicViewModel selectedCharacteristic) {
		this.selectedCharacteristic.set(selectedCharacteristic);
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

	public Command getSaveCommand() {
		return saveCommand;
	}

	public void setSaveCommand(Command saveCommand) {
		this.saveCommand = saveCommand;
	}

	public void loadAllCharacteristics() {
		List<Characteristic> characteristicsFromDb = dbHelper.getAllCharacteristics();
		List<CharacteristicViewModel> characteristics = new LinkedList<>();

		for (Characteristic elem : characteristicsFromDb) {
			characteristics.add(new CharacteristicViewModel(elem));
		}

		allCharacteristics.setValue(new ObservableListWrapper<>(characteristics));

	}

	public MainViewModel getParentViewModel() {
		return parentViewModel;
	}

	public void setParentViewModel(MainViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
	}

	public DescriptionFormatViewModel getEditedFormat() {
		return editedFormat;
	}
}
