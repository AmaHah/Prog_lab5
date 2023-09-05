package Presenter.Commands;

import Model.DataClasses.Flat;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;


/**
 * Команда удаления объекта коллекции по id
 */
public class RemoveByIdCommand implements ICommand {
    
    private int id;

    @Override
    public void execute(IPresenter presenter) {
        boolean found = false;
        
        for (Flat element : presenter.getCollection()) {
            if (element.getId() == this.id) {
                presenter.getCollection().remove(element);
                found = true;
                presenter.getView().showResult(String.format("Элемент с id = %d удалён из коллекции.", this.id));
                break;
            }
        }

        if (!found)
            presenter.getView().showError(String.format("Не удалось найти объект с id = %d", this.id));
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
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
                    this.id = Integer.parseInt(valueString);
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
