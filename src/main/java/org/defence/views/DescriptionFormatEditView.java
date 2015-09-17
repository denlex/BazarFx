package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.defence.MainApp;
import org.defence.viewmodels.CharacteristicKitEditViewModel;
import org.defence.viewmodels.CharacteristicKitViewModel;
import org.defence.viewmodels.DescriptionFormatEditViewModel;

/**
 * Created by root on 9/11/15.
 */
public class DescriptionFormatEditView implements FxmlView<DescriptionFormatEditViewModel>, Returnable {

    @FXML
    TextField codeTextField;

    @FXML
    TextField nameTextField;

    @FXML
    TableView<CharacteristicKitViewModel> characteristicKitsTableView;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> idTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> codeTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Integer> nameTableColumn;

    @FXML
    TableColumn<CharacteristicKitViewModel, Boolean> checkBoxTableColumn;

    @InjectViewModel
    DescriptionFormatEditViewModel viewModel;

    private Stage stage;
    private DialogResult dialogResult = DialogResult.CANCEL;

    private void initializeTableView() {
        characteristicKitsTableView.setEditable(true);

        characteristicKitsTableView.itemsProperty().bindBidirectional(viewModel.allCharacteristicKitsProperty());
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        checkBoxTableColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        checkBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("isBelong"));

        characteristicKitsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.selectedCharacteristicKitProperty().unbind();
            viewModel.selectedCharacteristicKitProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
        });
    }

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public DialogResult getModalResult() {
		return null;
	}

	public void addCharacteristicKitClicked(Event event) {
		ViewTuple<CharacteristicKitEditView, CharacteristicKitEditViewModel> viewTuple = FluentViewLoader.fxmlView
				(CharacteristicKitEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);
		Parent root = viewTuple.getView();

		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
		viewTuple.getCodeBehind().initializeStage();

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(MainApp.mainStage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		dialog.setScene(scene);
		dialog.showAndWait();


		// TODO: не за ходит в условие (не возвращает результат DialogResult)
		// set current position in characteristicKitsTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			System.out.println("INSIDE");

			int lastRowIndex = viewModel.getAllCharacteristicKits().size() - 1;
			characteristicKitsTableView.scrollTo(lastRowIndex);
			characteristicKitsTableView.selectionModelProperty().get().select(lastRowIndex);
			characteristicKitsTableView.requestFocus();
		}
	}

	public void saveButtonClicked() {
		viewModel.getSaveCommand().execute();
		dialogResult = DialogResult.OK;
	}

	public void cancelButtonClicked() {
		dialogResult = DialogResult.CANCEL;
		stage.close();
	}

	public void initializeStage() {
		stage.onShownProperty().bindBidirectional(viewModel.shownWindowProperty());
	}

	public void initialize() {
		codeTextField.textProperty().bindBidirectional(viewModel.codeProperty());
		nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());

		initializeTableView();
	}
}
