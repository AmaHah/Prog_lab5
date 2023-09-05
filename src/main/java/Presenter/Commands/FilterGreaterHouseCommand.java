package Presenter.Commands;


import Model.DataClasses.Flat;
import Model.DataClasses.House;
import Model.Exceptions.UserInputException;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.Exceptions.InputEndedException;
import Presenter.IPresenter;
import View.IView;

import java.util.Iterator;


/**
 *  Вывести элементы, значение поля house которых больше заданного
 */
public class FilterGreaterHouseCommand implements ICommand {


    @Override
    public void execute(IPresenter presenter) {
        House house;
        String houseName;
        long year;
        Integer numberOfLifts;
        IView view = presenter.getView();
        try {
            houseName = view.readSimpleField("название дома", House.class.getMethod("checkName", String.class), String.class, 0);
            year = view.readSimpleField("возраст дома", House.class.getMethod("checkYear", long.class), Long.class, 0);
            numberOfLifts = view.readSimpleField("количество лифтов в доме", House.class.getMethod("checkNumberOfLifts", Integer.class), Integer.class, 0);
            house = new House(houseName, year, numberOfLifts);
        } catch (NoSuchMethodException | InputEndedException | UserInputException e) {
            view.showError(e.getMessage());
            return;
        }
        Iterator<Flat> it = presenter.getCollection().iterator();
        StringBuilder result = new StringBuilder();
        while (it.hasNext()) {
            Flat element = it.next();
            if (element.getHouse().compareTo(house) > 0)
            {
                result.append(element.toString()).append("\n");
            }
        }
        if (result.isEmpty())
            view.showResult("Таких элементов в коллекции нет.");
        else
            view.showResult(result.toString());
    }

    @Override
    public String getName() {
        return "filter_greater_than_house";
    }

    @Override
    public String getDescription() {
        return "Вывести элементы, значение поля house которых больше заданного";
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
        return null;
    }

}
