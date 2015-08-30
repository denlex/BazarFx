package org.defence.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.defence.domain.entities.AssertedName;

/**
 * Created by root on 8/19/15.
 */
public class AssertedNameViewModel extends AbstractViewModel<AssertedName> {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty number = new SimpleStringProperty();
}
