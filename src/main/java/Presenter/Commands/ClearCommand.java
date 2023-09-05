package Presenter.Commands;

import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;


/**
 * Команда очистки коллекции
 */
public class ClearCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        presenter.getCollection().clear();
        presenter.getView().showResult("Коллекция очищена.");
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public String[] getArgsNames() {
        return null;
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        return;
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
