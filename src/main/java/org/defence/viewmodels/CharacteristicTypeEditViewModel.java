package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 9/7/15.
 */
public class CharacteristicTypeEditViewModel implements ViewModel {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty code = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    private String cachedCode;
    private String cachedName;

    private final DbHelper dbHelper = DbHelper.getInstance();

    private Command saveCommand;
    private Command cancelCommand;

    private CharacteristicCatalogViewModel parentViewModel;

    public CharacteristicTypeEditViewModel() {
        saveCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {

                if (id.getValue() == 0) {
                    // add characteristic type
                    if (dbHelper.addCharacteristicType(code.getValue(), name.getValue()) != null) {
                        System.out.println("type was added");

                        if (parentViewModel != null) {
                            System.out.println(parentViewModel.getClass().getName());
                        }
                    }
                } else {
                    // change exist characteristic type
                    // TODO: Сделать проверку на пустой ввод данных о типе измерения
                    System.out.println("Type was changed");
                    dbHelper.updateCharacteristicType(id.getValue(), code.getValue(), name.getValue());
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

    public Command getSaveCommand() {
        return saveCommand;
    }

    public void setSaveCommand(Command saveCommand) {
        this.saveCommand = saveCommand;
    }

    public Command getCancelCommand() {
        return cancelCommand;
    }

    public void setCancelCommand(Command cancelCommand) {
        this.cancelCommand = cancelCommand;
    }

    public CharacteristicCatalogViewModel getParentViewModel() {
        return parentViewModel;
    }

    public void setParentViewModel(CharacteristicCatalogViewModel parentViewModel) {
        this.parentViewModel = parentViewModel;
    }
}
