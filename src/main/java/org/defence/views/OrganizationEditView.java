package org.defence.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.defence.viewmodels.OrganizationEditViewModel;

/**
 * Created by root on 10/22/15.
 */
public class OrganizationEditView implements FxmlView<OrganizationEditViewModel> {
	@FXML
	TextField codeTextField;

	@FXML
	TextField nameTextField;

	@FXML
	ComboBox<String> typeComboBox;

	@InjectViewModel
	OrganizationEditViewModel viewModel;



	public void initialize() {

	}
}
