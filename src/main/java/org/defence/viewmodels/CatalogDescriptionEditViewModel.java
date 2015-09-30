package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicValue;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/25/15.
 */
public class CatalogDescriptionEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<CharacteristicValueViewModel> values = new SimpleListProperty<>();

	//	private final SetProperty<CharacteristicValueViewModel> characteristics = new SimpleSetProperty<>();
	private final ObjectProperty<CharacteristicValueViewModel> selectedCharacteristicValue = new SimpleObjectProperty<>();
	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private final ObjectProperty<AssertedNameViewModel> selectedName = new SimpleObjectProperty<>();
	private CatalogDescriptionViewModel editedDescription;

	DbHelper dbHelper = DbHelper.getInstance();

	MainViewModel parentViewModel;
	Command saveCommand;

	public CatalogDescriptionEditViewModel() {

		shownWindow = new SimpleObjectProperty<>(event -> {
			List<Characteristic> characteristics = dbHelper.getCharacteristicsByAssertedNameId(parentViewModel
					.getSelectedName().getId());

			/*ObservableList<CharacteristicValueViewModel> list = FXCollections.observableArrayList();
			for (Characteristic characteristic : characteristics) {
				CharacteristicValue value = new CharacteristicValue(characteristic, "1236");
				CharacteristicValueViewModel valueViewModel = new CharacteristicValueViewModel(value);

				list.add(valueViewModel);
			}*/
			List<CharacteristicValueViewModel> list = new ArrayList<>();
			for (Characteristic characteristic : characteristics) {
				CharacteristicValue value = new CharacteristicValue(characteristic, "1236");
				CharacteristicValueViewModel valueViewModel = new CharacteristicValueViewModel(value);
				list.add(valueViewModel);
			}
//			values.setValue(list);
			values.set(new ObservableListWrapper<>(list));

			for (CharacteristicValueViewModel value : values) {
//				value.setValue("456");
				System.out.println(value.getCharacteristic().getName());
				System.out.println(value.getValue());
			}
		});

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText(selectedCharacteristicValue.getValue().getCharacteristic().getName());
				alert.showAndWait();
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

	/*public String getCode() {
		return code.get();
	}

	public StringProperty codeProperty() {
		return code;
	}

	public void setCode(String code) {
		this.code.set(code);
	}*/

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public ObservableList<CharacteristicValueViewModel> getValues() {
		return values.get();
	}

	public ListProperty<CharacteristicValueViewModel> valuesProperty() {
		return values;
	}

	public void setValues(ObservableList<CharacteristicValueViewModel> values) {
		this.values.set(values);
	}

	public AssertedNameViewModel getSelectedName() {
		return selectedName.get();
	}

	public ObjectProperty<AssertedNameViewModel> selectedNameProperty() {
		return selectedName;
	}

	public void setSelectedName(AssertedNameViewModel selectedName) {
		this.selectedName.set(selectedName);
	}

	public MainViewModel getParentViewModel() {
		return parentViewModel;
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

	public EventHandler<WindowEvent> getShownWindow() {
		return shownWindow.get();
	}

	public ObjectProperty<EventHandler<WindowEvent>> shownWindowProperty() {
		return shownWindow;
	}

	public void setShownWindow(EventHandler<WindowEvent> shownWindow) {
		this.shownWindow.set(shownWindow);
	}

	public CharacteristicValueViewModel getSelectedCharacteristicValue() {
		return selectedCharacteristicValue.get();
	}

	public ObjectProperty<CharacteristicValueViewModel> selectedCharacteristicValueProperty() {
		return selectedCharacteristicValue;
	}

	public void setSelectedCharacteristicValue(CharacteristicValueViewModel selectedCharacteristicValue) {
		this.selectedCharacteristicValue.set(selectedCharacteristicValue);
	}
}
