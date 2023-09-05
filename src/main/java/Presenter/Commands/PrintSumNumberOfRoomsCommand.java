package Presenter.Commands;

import Model.DataClasses.Flat;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;

import java.util.*;


/**
 * Команда вывода суммы значений поля numberOfRooms для всех элементов коллекции
 */
public class PrintSumNumberOfRoomsCommand implements ICommand {
    @Override
    public void execute(IPresenter presenter) {
        Iterator<Flat> it = presenter.getCollection().iterator();
        long result = 0;

        try {
            while (it.hasNext()) {
                Flat element = it.next();
                result += element.getNumberOfRooms();
            }
        } catch (NullPointerException e) {
            presenter.getView().showError(e.getMessage());
        }
        presenter.getView().showResult(String.valueOf(result));
    }

    @Override
    public String getName() {
        return "sum_of_number_of_rooms";
    }

    @Override
    public String getDescription() {
        return "вывести сумму значений поля numberOfRooms для всех элементов коллекции";
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
