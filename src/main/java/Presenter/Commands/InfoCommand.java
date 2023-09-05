package Presenter.Commands;

import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IDataCollection;
import Presenter.IPresenter;
import View.IView;

import java.time.format.DateTimeFormatter;


/**
 * Команда вывода информации о коллекции
 */
public class InfoCommand implements ICommand {

    @Override
    public void execute(IPresenter presenter) {
        IDataCollection collection = presenter.getCollection();
        IView view = presenter.getView();
        String initDate = collection.getInitDate().format(DateTimeFormatter.ofPattern("dd MM yyyy HH:mm"));
        String modificationDate = collection.getModificationDate().format(DateTimeFormatter.ofPattern("dd MM yyyy HH:mm"));
        view.showResult(String.format(
            "Тип коллекции: %s\n" +
            "Дата инициализации: %s\n" +
            "Дата последнего изменения: %s\n" +
            "Количество элементов: %s",
            collection.getTypeName(), initDate, modificationDate, collection.getSize()
        ));
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
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
