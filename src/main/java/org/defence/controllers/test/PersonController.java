package org.defence.controllers.test;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.defence.tests.entities.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by root on 15.08.15.
 */
public class PersonController implements Initializable {
    private SessionFactory factory;

    @FXML
    private ListView personsListView;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private Button addPersonBtn;

    ObservableList<Person> persons;


    private void addPersonBtnRegisterEvents() {
        addPersonBtn.setOnAction(event -> {

            Person person = new Person(firstNameTextField.getText(), lastNameTextField.getText(),
                    Integer.parseInt(ageTextField.getText()));
            persons.add(person);

            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
            session.close();
        });
    }

    private void personsListViewRegisterEvents() {


        // Define rendering of the list of values in ComboBox drop down.
        personsListView.setCellFactory((listView) -> new ListCell<Person>() {
            @Override
            protected void updateItem(Person item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getFirstName() + " " + item.getLastName() + "    " + item.getAge());
                }
            }
        });

        // Define rendering of selected value shown in ComboBox.
        /*personsListView.setConverter(new StringConverter<MeasurementType>() {
            @Override
            public String toString(MeasurementType measurementType) {
                if (measurementType == null) {
                    return null;
                } else {
                    return measurementType.getName();
                }
            }

            @Override
            public MeasurementType fromString(String measurementTypeString) {
                return null; // No conversion fromString needed.
            }
        });*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        factory = new Configuration().configure().buildSessionFactory();
        persons = FXCollections.observableArrayList(new ArrayList<>());
        persons.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        System.out.println();
                    }
                }
            }
        });
        personsListView.setItems(persons);
        personsListViewRegisterEvents();
        addPersonBtnRegisterEvents();
    }
}
