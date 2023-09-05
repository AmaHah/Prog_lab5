package Presenter.Commands;

import Model.DataClasses.*;
import Model.Exceptions.BadIdException;
import Model.Exceptions.UserInputException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.Exceptions.InputEndedException;
import Presenter.IPresenter;
import View.IView;

import java.io.IOException;


/**
 * Команда добавления объекта в коллекцию
 */
public class AddCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        IView view = presenter.getView();
        Flat flat = null;

        try {
            Long id = presenter.getCollection().generateUniqueId();
            flat = readFlat(id, view);
        } catch (NoSuchMethodException | UserInputException | BadIdException e) {
            view.showError(e.getMessage());
        }

        presenter.getCollection().add(flat);
        presenter.getView().showResult("Новый элемент был добавлен в коллекцию.");
    }

    static Flat readFlat(Long id, IView view) throws NoSuchMethodException, UserInputException, BadIdException {
        String name;
        Coordinates coordinates;
        float area;
        long numberOfRooms;
        int floor;
        long numberOfBathrooms;
        Transport transport;
        House house;
        try {
            name = view.readSimpleField("Название квартиры", Flat.class.getMethod("checkName", String.class), String.class, -1);
            Integer x = view.readSimpleField("координату X", Coordinates.class.getMethod("checkX", Integer.class), Integer.class, 0);
            Double y = view.readSimpleField("координату Y", Coordinates.class.getMethod("checkY", Double.class), Double.class, 0);
            coordinates = new Coordinates(x, y);
            area = view.readSimpleField("размер квартиры", Flat.class.getMethod("checkArea", float.class), Float.class, 0);
            numberOfRooms = view.readSimpleField("количество комнат в квартире", Flat.class.getMethod("checkNumberOfRooms", long.class), Long.class, 0);
            floor = view.readSimpleField("этаж квартиры", Flat.class.getMethod("checkFloor", int.class), Integer.class, 0);
            numberOfBathrooms = view.readSimpleField("количество ванных комнат в квартире", Flat.class.getMethod("checkNumberOfBathrooms", long.class), Long.class, 0);
            transport = view.readEnumConstant("количество машин", Flat.class.getMethod("checkTransport", Transport.class), Transport.class, 0);
            String houseName = view.readSimpleField("название дома", House.class.getMethod("checkName", String.class), String.class, 0);
            long year = view.readSimpleField("возраст дома", House.class.getMethod("checkYear", long.class), Long.class, 0);
            Integer numberOfLifts = view.readSimpleField("количество лифтов в доме", House.class.getMethod("checkNumberOfLifts", Integer.class), Integer.class, 0);
            house = new House(houseName, year, numberOfLifts);
        }
        catch (InputEndedException e) {
            return null;
        }

        return new Flat(id, name, coordinates, area, numberOfRooms, floor, numberOfBathrooms, transport, house);
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "HUY[хуйхуйхуй";
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
