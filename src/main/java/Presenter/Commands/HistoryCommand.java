package Presenter.Commands;

import Model.DataClasses.Flat;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;
import View.IView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/**
 * Команда вывести последние 9 команд (без их аргументов)
 */
public class HistoryCommand implements ICommand {
    

    @Override
    public void execute(IPresenter presenter) {
        String result = "";
        ArrayList<ICommand> commands = presenter.getView().getCommands();
        String endOfLine = "\n";
        if (commands.size() == 1)
            return;
        if (commands.size() >= 9)
            for (int i = commands.size()-10; i < commands.size() - 1; i++) {
                result += commands.get(i).getName() + endOfLine;
            }
        else
            for (int i = 0; i < commands.size() - 1; i++) {
                result += commands.get(i).getName() + endOfLine;
            }
        presenter.getView().showResult(result.substring(0, result.lastIndexOf("\n")));
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "вывести последние 9 команд (без их аргументов)";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
