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
				if (characteristicIdList != null && characteristicIdList.size() > 0) {
					for (CharacteristicViewModel elem : allCharacteristics) {
						if (elem.getIsBelong()) {
							characteristicIdList.add(elem.getId());
						}
					}
				}

				// if window was opened in adding mode
				if (parentViewModel.getSelectedFormat() == null) {
					dbHelper.addDescriptionFormat(code.getValue(), name.getValue(), characteristicIdList);
				} else {
					// change exist descriptionFormat
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					dbHelper.updateDescriptionFormat(id.getValue(), code.getValue(), name.getValue(),
							characteristicIdList);
				}
				// TODO: реализовать возможность обновления списка наборов характеристик
//                parentViewModel.loadCharacteristics;
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
}
