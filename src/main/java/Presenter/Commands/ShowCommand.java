package Presenter.Commands;

import Model.DataClasses.Flat;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;

import java.util.StringJoiner;
import java.util.stream.Collectors;


/**
 * Команда вывода коллекции
 */
public class ShowCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        StringJoiner joiner = new StringJoiner("\n===\n");
        for (Flat flat : presenter.getCollection()) {
            String string = flat.toString();
            joiner.add(string);
        }
        String result = joiner.toString();
        if (result.isBlank()) result = "Коллекция пустая.";
        presenter.getView().showResult(result);
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
