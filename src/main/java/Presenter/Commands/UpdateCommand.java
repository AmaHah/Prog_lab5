package Presenter.Commands;

import Model.DataClasses.Flat;
import Model.Exceptions.BadIdException;
import Model.Exceptions.UserInputException;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;


/**
 * Команда для обновления объекта коллекции с заданным полем id
 */
public class UpdateCommand implements ICommand {

    private Long id;

    @Override
    public void execute(IPresenter presenter) {
        boolean found = false;
        
        for (Flat element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                try {
                    Flat replaceFlat = AddCommand.readFlat(this.id, presenter.getView());
                    if (replaceFlat == null)
                        return;
                    element.setName(replaceFlat.getName());
                    element.setCoordinates(replaceFlat.getCoordinates());
                    element.setCreationDate(replaceFlat.getCreationDate());
                    element.setArea(replaceFlat.getArea());
                    element.setNumberOfRooms(replaceFlat.getNumberOfRooms());
                    element.setFloor(replaceFlat.getFloor());
                    element.setNumberOfBathrooms(replaceFlat.getNumberOfBathrooms());
                    element.setTransport(replaceFlat.getTransport());
                    element.setHouse(replaceFlat.getHouse());
                    found = true;

                    presenter.getView().showResult(String.format("Элемент c id = %d был обновлён.", this.id));
                    break;
                } catch (NoSuchMethodException | UserInputException | BadIdException e) {
                    presenter.getView().showError(e.getMessage());
                }
            }
        }

        if (!found)
            presenter.getView().showError(String.format("Не удалось найти объект с id = %d", this.id));
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"id"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        try {
            switch (argName) {
                case "id":
                    this.id = Long.parseLong(valueString);
                    break;
            }
        } catch (NumberFormatException e) {
            throw new BadCommandArgException(getName(), argName, Integer.class.getSimpleName());
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        switch (argName) {
            case "id":
                return this.id;
            default:
                throw new CommandArgNotFound(getName(), argName);
        }
    }

}
