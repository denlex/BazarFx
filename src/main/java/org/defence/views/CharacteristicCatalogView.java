package org.defence.views;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.defence.MainApp;
import org.defence.viewmodels.*;

/**
 * Created by root on 8/12/15.
 */
public class CharacteristicCatalogView implements FxmlView<CharacteristicCatalogViewModel> {

	@FXML
	private TableView<CharacteristicTypeViewModel> typesTableView;

	@FXML
	private TableColumn<CharacteristicTypeViewModel, Integer> idTypeTableColumn;

	@FXML
	private TableColumn<CharacteristicTypeViewModel, String> codeTypeTableColumn;

	@FXML
	private TableColumn<CharacteristicTypeViewModel, String> nameTypeTableColumn;

	@FXML
	private TableView<CharacteristicViewModel> characteristicsTableView;

	@FXML
	private TableColumn<CharacteristicViewModel, Integer> idTableColumn;

	@FXML
	private TableColumn<CharacteristicViewModel, String> codeTableColumn;

	@FXML
	private TableColumn<CharacteristicViewModel, String> nameTableColumn;

	@FXML
	private TableColumn<CharacteristicViewModel, String> measurementsTableColumn;

	@FXML
	private Button addTypeButton;

	@FXML
	private Button editTypeButton;

	@FXML
	private Button deleteTypeButton;

	@FXML
	private Button addCharacteristicButton;

	@FXML
	private Button editCharacteristicButton;

	@FXML
	private Button deleteCharacteristicButton;

	@InjectViewModel
	CharacteristicCatalogViewModel viewModel;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void initializeTypesTableView() {
		typesTableView.itemsProperty().bindBidirectional(viewModel.typesProperty());
		idTypeTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
		codeTypeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
		nameTypeTableColumn.setCellValueFactory(new PropertyValueFactory("name"));

		typesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.selectedTypeProperty().unbind();
			viewModel.selectedTypeProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});
	}

	private void initializeCharacteristicTableView() {
		characteristicsTableView.itemsProperty().bindBidirectional(viewModel.characteristicsProperty());
		idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
		codeTableColumn.setCellValueFactory(new PropertyValueFactory("code"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
		measurementsTableColumn.setCellValueFactory(new PropertyValueFactory("measurementText"));

		characteristicsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
				newValue) -> {
			viewModel.selectedCharacteristicProperty().unbind();
			viewModel.selectedCharacteristicProperty().bindBidirectional(new SimpleObjectProperty<>(newValue));
		});
	}

	public void addTypeButtonClicked(Event event) {
		ViewTuple<CharacteristicTypeEditView, CharacteristicTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView
                (CharacteristicTypeEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);
		Parent root = viewTuple.getView();

		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(MainApp.mainStage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, event1 -> {
			if (event1.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		dialog.setScene(scene);
		dialog.showAndWait();

		// set current position in typesTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			viewModel.loadCharacteristicsBySelectedType();
			int lastRowIndex = viewModel.getTypes().size() - 1;
			typesTableView.scrollTo(lastRowIndex);
			typesTableView.selectionModelProperty().get().select(lastRowIndex);
			typesTableView.requestFocus();
		}
	}

	public void editTypeButtonClicked(Event event) {
		ViewTuple<CharacteristicTypeEditView, CharacteristicTypeEditViewModel> viewTuple = FluentViewLoader.fxmlView
                (CharacteristicTypeEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);

		Property<CharacteristicTypeViewModel> t = viewModel.selectedTypeProperty();
		viewTuple.getViewModel().idProperty().bindBidirectional(t.getValue().idProperty());
		viewTuple.getViewModel().codeProperty().bindBidirectional(t.getValue().codeProperty());
		viewTuple.getViewModel().nameProperty().bindBidirectional(t.getValue().nameProperty());

		Parent root = viewTuple.getView();
		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(MainApp.mainStage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, event1 -> {
			if (event1.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});


		int selectedItemIndex = typesTableView.getSelectionModel().getFocusedIndex();

		dialog.setScene(scene);
		dialog.showAndWait();

		// if types were modified, then refresh typesTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			typesTableView.refresh();
		}

		typesTableView.scrollTo(selectedItemIndex);
		typesTableView.selectionModelProperty().get().select(selectedItemIndex);
		typesTableView.requestFocus();
	}

	public void addCharacteristicButtonClicked(Event event) {
		ViewTuple<CharacteristicEditView, CharacteristicEditViewModel> viewTuple = FluentViewLoader.fxmlView
                (CharacteristicEditView.class).load();
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

		// set current position in characteristicsTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			int lastRowIndex = viewModel.getCharacteristics().size() - 1;
			characteristicsTableView.scrollTo(lastRowIndex);
			characteristicsTableView.selectionModelProperty().get().select(lastRowIndex);
			characteristicsTableView.requestFocus();
		}
	}

	public void editCharacteristicButtonClicked(Event event) {
		Property<CharacteristicViewModel> m = viewModel.selectedCharacteristicProperty();

		// if item in measurementTableView was not selected
		if (m.getValue() == null) {
			return;
		}

		ViewTuple<CharacteristicEditView, CharacteristicEditViewModel> viewTuple = FluentViewLoader.fxmlView
                (CharacteristicEditView.class).load();
		viewTuple.getViewModel().setParentViewModel(viewModel);
		Parent root = viewTuple.getView();

		Stage dialog = new Stage();
		viewTuple.getCodeBehind().setStage(dialog);
		viewTuple.getCodeBehind().initializeStage();

		viewTuple.getViewModel().idProperty().bindBidirectional(m.getValue().idProperty());
		viewTuple.getViewModel().codeProperty().bindBidirectional(m.getValue().codeProperty());
		viewTuple.getViewModel().nameProperty().bindBidirectional(m.getValue().nameProperty());
        /*viewTuple.getViewModel().setCachedCode(m.getValue().codeProperty().getValue());
        viewTuple.getViewModel().setCachedName(m.getValue().nameProperty().getValue());*/

		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.initOwner(MainApp.mainStage);
		dialog.setResizable(false);

		Scene scene = new Scene(root);
		scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
			if (keyEvent.getCode() == KeyCode.ESCAPE) {
				dialog.close();
			}
		});

		int selectedItemIndex = characteristicsTableView.getSelectionModel().getFocusedIndex();

		dialog.setScene(scene);
		dialog.showAndWait();

		// if characteristics were modified, then refresh characteristicsTableView
		if (viewTuple.getCodeBehind().getModalResult() == DialogResult.OK) {
			characteristicsTableView.refresh();
		}

		characteristicsTableView.scrollTo(selectedItemIndex);
		characteristicsTableView.selectionModelProperty().get().select(selectedItemIndex);
		characteristicsTableView.requestFocus();
	}

	public void deleteCharacteristicButtonClicked(Event event) {
		viewModel.getDeleteCharacteristicCommand().execute();
		characteristicsTableView.requestFocus();
	}

	public void typesTableViewClicked() {
		viewModel.loadCharacteristicsBySelectedType();
	}

	public void typesTableViewMouseReleased(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				editTypeButtonClicked(event);
			}
		}
	}

	public void characteristicsTableViewMouseRelease(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				editCharacteristicButtonClicked(event);
			}
		}
	}

	public void typesTableViewKeyReleased(KeyEvent event) {
		KeyCode keyCode = event.getCode();

		if (keyCode.isNavigationKey() || keyCode.isArrowKey()) {
			viewModel.loadCharacteristicsBySelectedType();
			return;
		}
	}

	public void typesTableViewKeyPressed(KeyEvent event) {
        /*if (event.getCode() == KeyCode.DELETE) {
            deleteCharacteristicButtonClicked(event);
        }*/

		KeyCode keyCode = event.getCode();

		if (keyCode == KeyCode.INSERT) {
			addTypeButtonClicked(event);
			return;
		}

		if (keyCode == KeyCode.ENTER) {
			editTypeButtonClicked(event);
			return;
		}
	}

	public void characteristicsTableViewKeyPressed(KeyEvent event) {
		KeyCode keyCode = event.getCode();

		if (keyCode == KeyCode.INSERT) {
			addCharacteristicButtonClicked(event);
		}

		if (keyCode == KeyCode.DELETE) {
			deleteCharacteristicButtonClicked(event);
		}

		if (keyCode == KeyCode.ENTER) {
			editCharacteristicButtonClicked(event);
		}
	}

	public void initialize() {
		initializeTypesTableView();
		initializeCharacteristicTableView();

		editTypeButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());
		deleteTypeButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());

		addCharacteristicButton.disableProperty().bind(viewModel.selectedTypeProperty().isNull());
		editCharacteristicButton.disableProperty().bind(viewModel.selectedCharacteristicProperty().isNull());
		deleteCharacteristicButton.disableProperty().bind(viewModel.selectedCharacteristicProperty().isNull());
	}
}
