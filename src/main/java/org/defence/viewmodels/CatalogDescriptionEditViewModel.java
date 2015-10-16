package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicValue;
import org.defence.infrastructure.DbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 9/25/15.
 */
public class CatalogDescriptionEditViewModel implements ViewModel {
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final ListProperty<CharacteristicValueViewModel> values = new SimpleListProperty<>();

//	private final ListProperty<CharacteristicValueViewModel> characteristics = new SimpleListProperty<>();

	private final ObjectProperty<CharacteristicValueViewModel> selectedCharacteristicValue = new
			SimpleObjectProperty<>();
	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private final ObjectProperty<AssertedNameViewModel> selectedName = new SimpleObjectProperty<>();
	private CatalogDescriptionViewModel editedDescription;

	DbHelper dbHelper = DbHelper.getInstance();

	MainViewModel parentViewModel;
	Command saveCommand;

	public CatalogDescriptionEditViewModel() {

		shownWindow = new SimpleObjectProperty<>(event -> {
			List<Characteristic> characteristics = null;
			List<CharacteristicValueViewModel> list = new ArrayList<>();


			if (parentViewModel.getSelectedName() != null) {
				characteristics = dbHelper.getCharacteristicsByAssertedNameId(parentViewModel.getSelectedName().getId());

				for (Characteristic characteristic : characteristics) {
					list.add(new CharacteristicValueViewModel(new CharacteristicValue(characteristic, null)));
				}
			} else {
				if (parentViewModel.getSelectedDescription() != null) {
					List<CharacteristicValue> characteristicValues = dbHelper.getCharacteristicValuesByCatalogDescriptionId(parentViewModel
							.getSelectedDescription().getId());

					for (CharacteristicValue value : characteristicValues) {
						list.add(new CharacteristicValueViewModel(new CharacteristicValue(value.getCharacteristic(),
								value.getValue())));
					}
				}
			}

			values.set(new ObservableListWrapper<>(list));
		});

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				CharacteristicValueViewModel valueViewModel = selectedCharacteristicValue.getValue();
				AssertedNameViewModel assertedName = parentViewModel.getSelectedName();

				/*dbHelper.addCatalogDescription(assertedName.getId(), name.getValue(), values.stream().map
						(CharacteristicValueViewModel::toModel)
						.collect(Collectors.toList()));*/

				// editting catalogDescription
				if (assertedName == null) {
					editedDescription = new CatalogDescriptionViewModel(dbHelper.updateCatalogDescription(id.getValue
							(), name.getValue(), values.stream().map(CharacteristicValueViewModel::toModel)
							.collect(Collectors.toList())));
					CatalogDescriptionViewModel selectedDescription = parentViewModel.getSelectedDescription();

					if (selectedDescription != null) {
						selectedDescription.setId(editedDescription.getId());
						selectedDescription.setName(editedDescription.getName());
						selectedDescription.setValues(editedDescription.getValues());
					}
				} else {
					editedDescription = new CatalogDescriptionViewModel(dbHelper.addCatalogDescription(assertedName
							.getId(), name.getValue(), values.stream().map(CharacteristicValueViewModel::toModel)
							.collect(Collectors.toList())));

					if (assertedName.getCatalogDescriptions() == null) {
						List<CatalogDescriptionViewModel> list = new ArrayList<>();
						list.add(editedDescription);
						assertedName.setCatalogDescriptions(FXCollections.observableArrayList(list));
					} else {
						assertedName.getCatalogDescriptions().add(editedDescription);
					}
				}

				// TODO: доделать позиционирование на отредактированную запись в treeView
				parentViewModel.displayFormats();


				/*if (assertedName == null) {
					editedDescription = new CharacteristicValueViewModel(dbHelper.updateCharacteristicValue
							(valueViewModel.getId(), valueViewModel.getCharacteristic(), valueViewModel.getValue()));

					parentViewModel.getSelectedDescription().setId(editedDescription.getId());
					parentViewModel.getSelectedDescription().setName(editedDescription.getName());
					parentViewModel.getSelectedDescription().setValues(editedDescription.getValues());
				} else {
					editedDescription = new CharacteristicValueViewModel(dbHelper.addCharacteristicValue
							(valueViewModel.getCharacteristic(), valueViewModel.getValue()));

					if (assertedName.getCatalogDescriptions() == null) {
						List<CatalogDescriptionViewModel> list = new ArrayList<>();
						list.add(editedDescription);
						assertedName.setCatalogDescriptions(FXCollections.observableArrayList(list));
					} else {
						assertedName.getCatalogDescriptions().add(editedDescription);
					}
				}*/
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
