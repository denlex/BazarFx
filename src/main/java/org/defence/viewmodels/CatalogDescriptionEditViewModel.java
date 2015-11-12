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
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import org.defence.domain.entities.*;
import org.defence.infrastructure.DbHelper;
import org.defence.tools.DateConverter;

import java.time.LocalDate;
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


	private final StringProperty test = new SimpleStringProperty();

	//	private final ObjectProperty<RegistrationInfoViewModel> registrationInfo = new SimpleObjectProperty<>(new
	// RegistrationInfoViewModel());
	private final ObjectProperty<OrganizationViewModel> organization = new SimpleObjectProperty<>(new
			OrganizationViewModel());
	private final ObjectProperty<OrganizationViewModel> selectedOrganization = new SimpleObjectProperty<>(new
			OrganizationViewModel());
	private final ListProperty<CharacteristicValueViewModel> values = new SimpleListProperty<>();
	private final ListProperty<OrganizationViewModel> organizations = new SimpleListProperty<>(FXCollections
			.observableArrayList());

	private final StringProperty applicationNumber = new SimpleStringProperty();
	private final StringProperty registrationNumber = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> registrationDate = new SimpleObjectProperty<>();

//	private final ListProperty<CharacteristicValueViewModel> characteristics = new SimpleListProperty<>();

	private final ObjectProperty<CharacteristicValueViewModel> selectedCharacteristicValue = new
			SimpleObjectProperty<>();

	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;

	private final ObjectProperty<AssertedNameViewModel> selectedName = new SimpleObjectProperty<>();
	private CatalogDescriptionViewModel editedDescription;

	DbHelper dbHelper = DbHelper.getInstance();

	MainViewModel parentViewModel;
	Command saveCommand;
	private Boolean saveCommandSuccess = false;

	public CatalogDescriptionEditViewModel() {

		shownWindow = new SimpleObjectProperty<>(event -> {
			List<Characteristic> characteristics = null;
			List<CharacteristicValueViewModel> list = new ArrayList<>();

			// adding catalogDescription
			if (parentViewModel.getSelectedName() != null) {
				characteristics = dbHelper.getCharacteristicsByAssertedNameId(parentViewModel.getSelectedName().getId
						());

				for (Characteristic characteristic : characteristics) {
					list.add(new CharacteristicValueViewModel(new CharacteristicValue(characteristic, null)));
				}
			} else {
				CatalogDescriptionViewModel description = parentViewModel.getSelectedDescription();

				if (description != null) {
					characteristics = dbHelper.getCharacteristicsByCatalogDescriptionId(description.getId());


					// fill characteristics of catalogDescriptions with values
					for (Characteristic characteristic : characteristics) {
						boolean flag = false;
						for (CharacteristicValueViewModel value : description.getValues()) {
							if (value.getCharacteristic().getId() == characteristic.getId()) {
								list.add(new CharacteristicValueViewModel(new CharacteristicValue(characteristic, value.getValue())));
								flag = true;
							}
						}

						// if characteristic does not have value - display it
						if (!flag) {
							list.add(new CharacteristicValueViewModel(new CharacteristicValue(characteristic, null)));
						}
					}

					OrganizationViewModel org = description.getOrganization();

					if (org != null && org.getId() != 0) {
						organization.setValue(new OrganizationViewModel(org.getId(), org.getCode(), org.getName(),
								org.getType()));
					}
				}
			}

			values.set(new ObservableListWrapper<>(list));


			List<Organization> organizationList = dbHelper.getAllOrganizations();

			for (Organization organization : organizationList) {
				organizations.add(new OrganizationViewModel(organization));
			}
		});

		saveCommand = new DelegateCommand(() -> new Action() {

			@Override
			protected void action() throws Exception {
				AssertedNameViewModel assertedName = parentViewModel.getSelectedName();
				saveCommandSuccess = true;

				String errorMsg = "";

				// input check
				if (getCode() == null || getCode().equals("")) {
					errorMsg = "Введите код КО";
					saveCommandSuccess = false;
				} else {
					if (getName() == null || getName().equals("")) {
						errorMsg = "Введите наименование КО";
						saveCommandSuccess = false;
					} else {
						if (selectedOrganization.getValue().getId() == 0) {
							errorMsg = "Выберите организацию";
							saveCommandSuccess = false;
						} else {
							if (registrationDate == null || getRegistrationDate() == null) {
								errorMsg = "Выберите дату регистрации";
								saveCommandSuccess = false;
							}
						}
					}
				}

				if (!saveCommandSuccess) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText(errorMsg);
					alert.showAndWait();
					return;
				}

				// editting catalogDescription
				if (assertedName == null) {
					editedDescription = new CatalogDescriptionViewModel(dbHelper.updateCatalogDescription(id.getValue
							(), code.getValue(), name.getValue(), values.stream().map
							(CharacteristicValueViewModel::toModel).collect(Collectors.toList()), selectedOrganization
							.getValue().getId(), new RegistrationInfo(applicationNumber.getValue(), registrationNumber
							.getValue(), DateConverter.toDate(registrationDate.getValue()))));
					CatalogDescriptionViewModel selectedDescription = parentViewModel.getSelectedDescription();

					if (selectedDescription != null) {
						selectedDescription.setId(editedDescription.getId());
						selectedDescription.setName(editedDescription.getName());
						selectedDescription.setValues(editedDescription.getValues());
					}
				} else {
					editedDescription = new CatalogDescriptionViewModel(dbHelper.addCatalogDescription(assertedName
							.getId(), code.getValue(), name.getValue(), values.stream().map
							(CharacteristicValueViewModel::toModel).collect(Collectors.toList()), selectedOrganization
							.getValue().getId(), new RegistrationInfo(applicationNumber.getValue(), registrationNumber
							.getValue(), DateConverter.toDate(registrationDate.getValue()))));

					if (assertedName.getCatalogDescriptions() == null) {
						List<CatalogDescriptionViewModel> list = new ArrayList<>();
						list.add(editedDescription);
						assertedName.setCatalogDescriptions(FXCollections.observableArrayList(list));
					} else {
						assertedName.getCatalogDescriptions().add(editedDescription);
						System.out.println("Новое КО добавлено");
					}
				}
				// TODO: доделать позиционирование на отредактированную запись в treeView
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
		this.code.set(code.trim());
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name.trim());
	}

	public OrganizationViewModel getOrganization() {
		return organization.get();
	}

	public ObjectProperty<OrganizationViewModel> organizationProperty() {
		return organization;
	}

	public void setOrganization(OrganizationViewModel organization) {
		this.organization.set(organization);
	}

	public OrganizationViewModel getSelectedOrganization() {
		return selectedOrganization.get();
	}

	public ObjectProperty<OrganizationViewModel> selectedOrganizationProperty() {
		return selectedOrganization;
	}

	public void setSelectedOrganization(OrganizationViewModel organization) {
		this.selectedOrganization.set(organization);
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

	public ObservableList<OrganizationViewModel> getOrganizations() {
		return organizations.get();
	}

	public ListProperty<OrganizationViewModel> organizationsProperty() {
		return organizations;
	}

	public void setOrganizations(ObservableList<OrganizationViewModel> organizations) {
		this.organizations.set(organizations);
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

	public String getTest() {
		return test.get();
	}

	public StringProperty testProperty() {
		return test;
	}

	public void setTest(String test) {
		this.test.set(test);
	}

	public String getApplicationNumber() {
		return applicationNumber.get();
	}

	public StringProperty applicationNumberProperty() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber.set(applicationNumber);
	}

	public String getRegistrationNumber() {
		return registrationNumber.get();
	}

	public StringProperty registrationNumberProperty() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber.set(registrationNumber);
	}

	public LocalDate getRegistrationDate() {
		return registrationDate.get();
	}

	public ObjectProperty<LocalDate> registrationDateProperty() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate.set(registrationDate);
	}

	public Boolean getSaveCommandSuccess() {
		return saveCommandSuccess;
	}
}
