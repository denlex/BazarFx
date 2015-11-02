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
 * Created by root on 8/31/15.
 */
public class MeasurementTypeEditViewModel implements ViewModel {

    private final IntegerProperty idTemp = new SimpleIntegerProperty();
    private StringProperty codeTemp = new SimpleStringProperty();
    private StringProperty nameTemp = new SimpleStringProperty();

    private final IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty code = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    private String cachedCode;
    private String cachedName;

	private ObjectProperty<EventHandler<WindowEvent>> shownWindow;
    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    private MeasurementCatalogViewModel parentViewModel;

    public MeasurementTypeEditViewModel() {
		shownWindow = new SimpleObjectProperty<>(event -> {
			System.out.println("Inside shownWindow");

			idTemp.setValue(id.getValue());
			codeTemp.setValue(code.getValue());
			nameTemp.setValue(name.getValue());
		});

		saveCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

				if (id.getValue() == 0) {
					// add measurement type
					if (dbHelper.addMeasurementType(codeTemp.getValue(), nameTemp.getValue()) != null) {
						System.out.println("type was added");

						if (parentViewModel != null) {
							System.out.println(parentViewModel.getClass().getName());
						}
					}
				} else {
					// change exist measurement type
					// TODO: Сделать проверку на пустой ввод данных о типе измерения
					System.out.println("Type was changed");
					dbHelper.updateMeasurementType(idTemp.getValue(), codeTemp.getValue(), nameTemp.getValue());
				}

				parentViewModel.loadAllTypes();
			}
		});

		cancelCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {

			}
		});
	}

    public Command getSaveCommand() {
        return saveCommand;
    }

    public Command getCancelCommand() {
        return cancelCommand;
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

    public String getCachedCode() {
        return cachedCode;
    }

    public void setCachedCode(String cachedCode) {
        this.cachedCode = cachedCode;
    }

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }

    public MeasurementCatalogViewModel getParentViewModel() {
        return parentViewModel;
    }

    public void setParentViewModel(MeasurementCatalogViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }

	public int getIdTemp() {
		return idTemp.get();
	}

	public IntegerProperty idTempProperty() {
		return idTemp;
	}

	public void setIdTemp(int idTemp) {
		this.idTemp.set(idTemp);
	}

	public String getCodeTemp() {
		return codeTemp.get();
	}

	public StringProperty codeTempProperty() {
		return codeTemp;
	}

	public void setCodeTemp(String codeTemp) {
		this.codeTemp.set(codeTemp);
	}

	public String getNameTemp() {
		return nameTemp.get();
	}

	public StringProperty nameTempProperty() {
		return nameTemp;
	}

	public void setNameTemp(String nameTemp) {
		this.nameTemp.set(nameTemp);
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
}
