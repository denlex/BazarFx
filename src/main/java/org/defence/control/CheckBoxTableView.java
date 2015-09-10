package org.defence.control;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import javax.annotation.PostConstruct;

/**
 * Created by root on 9/4/15.
 */
public class CheckBoxTableView<S> extends TableView {
    public CheckBoxTableView() {
        super();
        addLastColumn();
        System.out.println("standard constructor");
    }

    public CheckBoxTableView(ObservableList<S> items) {
        super(items);
    }

    @PostConstruct
    public void addLastColumn() {
        TableColumn<CheckBox, Boolean> checkBoxTableColumn = new TableColumn<>();
        checkBoxTableColumn.setCellFactory(param -> new TableCell<>());
        checkBoxTableColumn.setText("TEST");

        TableColumn<String, Boolean> testColumn = new TableColumn<>();
        testColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        testColumn.editableProperty().setValue(true);

        this.getColumns().add(testColumn);
        System.out.println("New column was added");
    }
}
