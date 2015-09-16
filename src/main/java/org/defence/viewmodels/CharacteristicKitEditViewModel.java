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
import org.defence.domain.entities.CharacteristicKit;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 9/10/15.
 */
public class CharacteristicKitEditViewModel implements ViewModel {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();

    private final ListProperty<CharacteristicViewModel> allCharacteristics = new SimpleListProperty<>();
    private final ObjectProperty<CharacteristicViewModel> selectedCharacteristic = new SimpleObjectProperty<>();

    private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

    private Command saveCommand;
    private final DbHelper dbHelper = DbHelper.getInstance();

    private DescriptionFormatEditViewModel parentViewModel;

    public CharacteristicKitEditViewModel() {
        List<Characteristic> allCharacteristicsFromDb = dbHelper.getAllCharacteristics();
        List<CharacteristicViewModel> list = new LinkedList<>();

        for (Characteristic characteristic : allCharacteristicsFromDb) {
            list.add(new CharacteristicViewModel(characteristic));
        }

        allCharacteristics.setValue(new ObservableListWrapper<>(list));

        // filling checkBoxes in table with corresponded values
        shownWindow = new SimpleObjectProperty<>(event -> {
            // if modification open window mode (not creation new characteristic)
            if (id != null && id.getValue() != 0) {
                CharacteristicKit current = dbHelper.getCharacteristicKitById(id.getValue());
                for (Characteristic elem : current.getCharacteristics()) {
                    CharacteristicViewModel m = allCharacteristics.getValue().stream().filter(p -> p.getId() == elem.getId()).findFirst().get();

                    m.setIsBelong(true);
                }
            } else {

            }
        });

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

				List<Integer> characteristicIdList = new LinkedList<>();
				for (CharacteristicViewModel elem : allCharacteristics) {
					if (elem.getIsBelong()) {
						characteristicIdList.add(elem.getId());
					}
				}

				if (id.getValue() == 0) {
					// add characteristicKit
					dbHelper.addCharacteristicKit(name.getValue(), characteristicIdList);
				} else {
					// change exist characteristicKit
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					dbHelper.updateCharacteristicKit(id.getValue(), name.getValue(), characteristicIdList);
				}

				// TODO: реализовать возможность обновления списка наборов характеристик
//                parentViewModel.loadCharacteristicKitsBySelectedFormat();
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

	public void setParentViewModel(DescriptionFormatEditViewModel parentViewModel) {
		this.parentViewModel = parentViewModel;
	}
}
