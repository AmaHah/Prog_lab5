package Presenter.Commands;

import Model.DataClasses.*;
import Model.Exceptions.BadIdException;
import Model.Exceptions.UserInputException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;
import View.IView;

import java.util.Collections;


/**
 * Команда добавления объекта в коллекцию если он меньше минимального
 */
public class AddMinCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        IView view = presenter.getView();
        Flat flat = null;
        try {
            Long id = presenter.getCollection().generateUniqueId();
            flat = AddCommand.readFlat(id, view);
            if (flat.compareTo(Collections.min(presenter.getCollection())) < 0)
            {
                view.showResult("Элемент был добавлен в коллекцию.");
                presenter.getCollection().add(flat);
            }
            else
                view.showResult("Элемент не минимальный.");
        } catch (NoSuchMethodException | UserInputException | BadIdException | NullPointerException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если он меньше наименьшего в коллекции";
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
