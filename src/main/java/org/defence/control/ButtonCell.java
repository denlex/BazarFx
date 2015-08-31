package org.defence.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import org.defence.tests.JavaFXDynTable;

import javax.swing.*;

/**
 * Created by Denis on 28.08.2015.
 */
//Define the button cell
public class ButtonCell extends TableCell<JavaFXDynTable.Record, Boolean> {
    MenuButton btn = new MenuButton("Delete");
    MenuItem menuItemCut = new MenuItem("Закрыть");


    public ButtonCell() {
        menuItemCut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Denchik is Java Developer");
            }
        });
        btn.getItems().addAll(menuItemCut);
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(btn);
        }

    }
}
