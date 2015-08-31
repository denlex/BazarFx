package org.defence.viewmodels;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.Platform;
import org.defence.infrastructure.DbHelper;

/**
 * Created by root on 30.08.15.
 */
public class MainViewModel implements ViewModel {
    private Command exitCommand;

    public MainViewModel() {
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
}
