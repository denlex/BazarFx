package org.defence.control;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by root on 9/4/15.
 */
public class CheckBoxTableView<S> extends TableView {
    public CheckBoxTableView() {
        super();
    }

    public CheckBoxTableView(ObservableList<S> items) {
        super(items);
        this.getColumns().add(new TableColumn("CheckBox"));
    }
}
