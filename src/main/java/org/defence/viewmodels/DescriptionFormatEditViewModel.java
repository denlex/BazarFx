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
import org.defence.domain.entities.CharacteristicKit;
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

    private final ListProperty<CharacteristicKitViewModel> allCharacteristicKits = new SimpleListProperty<>();
    private final ObjectProperty<CharacteristicKitViewModel> selectedCharacteristicKit = new SimpleObjectProperty<>();

    private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

    private Command saveCommand;
    private final DbHelper dbHelper = DbHelper.getInstance();

    public DescriptionFormatEditViewModel() {
        List<CharacteristicKit> allCharacteristicKitsFromDb = dbHelper.getAllCharacteristicKits();
        List<CharacteristicKitViewModel> list = new LinkedList<>();

        for (CharacteristicKit characteristicKit : allCharacteristicKitsFromDb) {
            list.add(new CharacteristicKitViewModel(characteristicKit));
        }

        allCharacteristicKits.setValue(new ObservableListWrapper<>(list));

        // filling checkBoxes in table with corresponded values
        shownWindow = new SimpleObjectProperty<>(event -> {
            // if modification open window mode (not creation new description format)
            if (id != null && id.getValue() != 0) {
                DescriptionFormat current = dbHelper.getDescriptionFormatById(id.getValue());
                for (CharacteristicKit elem : current.getCharacteristicKits()) {
                    CharacteristicKitViewModel m = allCharacteristicKits.getValue().stream().filter(p -> p.getId() == elem.getId()).findFirst().get();

                    m.setIsBelong(true);
                }
            } else {
				loadAllCharacteristicKits();
            }
        });

        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                // TODO: Заменить на id выбранного формата описания
                Integer formatId = 1;//parentViewModel.getSelectedType().getId();

                if (formatId == null || formatId == 0) {
                    return;
                }

                List<Integer> characteristicIdList = new LinkedList<>();
                for (CharacteristicKitViewModel elem : allCharacteristicKits) {
                    if (elem.getIsBelong()) {
                        characteristicIdList.add(elem.getId());
                    }
                }

                if (id.getValue() == 0) {
                    // add characteristic
                    dbHelper.addDescriptionFormat(code.getValue(), name.getValue(), null, characteristicIdList);
                } else {
                    // change exist measurement
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    dbHelper.updateDescriptionFormat(id.getValue(), code.getValue(), name.getValue(), null, characteristicIdList);
                }

                // TODO: реализовать возможность обновления списка наборов характеристик
//                parentViewModel.loadCharacteristicKitKitsBySelectedFormat();
            }
        });
    }

//    private CharacteristicKitKitCatalogViewModel parentViewModel;


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

    public ObservableList<CharacteristicKitViewModel> getAllCharacteristicKits() {
        return allCharacteristicKits.get();
    }

    public ListProperty<CharacteristicKitViewModel> allCharacteristicKitsProperty() {
        return allCharacteristicKits;
    }

    public void setAllCharacteristicKits(ObservableList<CharacteristicKitViewModel> allCharacteristicKits) {
        this.allCharacteristicKits.set(allCharacteristicKits);
    }

    public CharacteristicKitViewModel getSelectedCharacteristicKit() {
        return selectedCharacteristicKit.get();
    }

    public ObjectProperty<CharacteristicKitViewModel> selectedCharacteristicKitProperty() {
        return selectedCharacteristicKit;
    }

    public void setSelectedCharacteristicKit(CharacteristicKitViewModel selectedCharacteristicKit) {
        this.selectedCharacteristicKit.set(selectedCharacteristicKit);
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

    public void loadAllCharacteristicKits() {
		List<CharacteristicKit> kitsFromDb = dbHelper.getAllCharacteristicKits();
		List<CharacteristicKitViewModel> kits = new LinkedList<>();

		for (CharacteristicKit elem : kitsFromDb) {
			kits.add(new CharacteristicKitViewModel(elem));
		}

		allCharacteristicKits.setValue(new ObservableListWrapper<CharacteristicKitViewModel>(kits));

	}
}
