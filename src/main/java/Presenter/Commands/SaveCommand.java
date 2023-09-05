package Presenter.Commands;

import Model.DataClasses.Flat;
import Model.Exceptions.SaveFailedException;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;


/**
 * Команда сохранения коллекции
 */
public class SaveCommand implements ICommand {
    
    @Override
    public void execute(IPresenter presenter) {
        Flat[] arr = presenter.getCollection().toArray(new Flat[presenter.getCollection().size()]);
        String filepath;
        try {
            filepath = presenter.getModel().saveData(arr);
        } catch (SaveFailedException e) {
            presenter.getView().showError(e.getMessage());
            return;
        }

        presenter.getView().showResult(String.format("Данные были записаны в файл \"%s\"", filepath));
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
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
