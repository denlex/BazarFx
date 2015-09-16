package org.defence.viewmodels;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.defence.domain.entities.DescriptionFormat;
import org.defence.infrastructure.DbHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 30.08.15.
 */
public class MainViewModel implements ViewModel {
    private Command exitCommand;
	private ListProperty<DescriptionFormatViewModel> formats = new SimpleListProperty<>();
	private DbHelper dbHelper = DbHelper.getInstance();
	private ObjectProperty<DescriptionFormatViewModel> root = new SimpleObjectProperty<>();

    public MainViewModel() {
		root.setValue(new DescriptionFormatViewModel("code", "ROOT"));

		List<DescriptionFormat> allFormatsFromDb = dbHelper.getAllDescriptionFormats();
		List<DescriptionFormatViewModel> list = new LinkedList<>();

		for (DescriptionFormat elem : allFormatsFromDb) {
			list.add(new DescriptionFormatViewModel(elem));
		}

		formats.setValue(new ObservableListWrapper<>(list));

		exitCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				System.out.println("Inside action");
				DbHelper.terminateDbConnection();
				Platform.exit();
				System.exit(0);
			}
		});
	}

    public Command getExitCommand() {
        return exitCommand;
    }

	public ObservableList<DescriptionFormatViewModel> getFormats() {
		return formats.get();
	}

	public ListProperty<DescriptionFormatViewModel> formatsProperty() {
		return formats;
	}

	public void setFormats(ObservableList<DescriptionFormatViewModel> formats) {
		this.formats.set(formats);
	}

	public DescriptionFormatViewModel getRoot() {
		return root.get();
	}

	public ObjectProperty<DescriptionFormatViewModel> rootProperty() {
		return root;
	}

	public void setRoot(DescriptionFormatViewModel root) {
		this.root.set(root);
	}
}
