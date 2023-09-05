package Presenter.Commands;

import Model.DataClasses.Flat;
import Model.Exceptions.BadIdException;
import Model.Exceptions.UserInputException;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;

import java.util.Iterator;


/**
 * Команда удаления последнего элемента коллекции
 */
public class RemoveLast implements ICommand {

    @Override
    public void execute(IPresenter presenter) {
        try {
            presenter.getCollection().remove(presenter.getCollection().size() - 1);
            presenter.getView().showResult("Последний элемент был успешно удалён.");
        } catch (NullPointerException e) {
            presenter.getView().showError(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getDescription() {
        return "удаляет последний элемент коллекции";
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
