package Presenter.Commands;

import Model.DataClasses.Flat;
import Model.DataClasses.Transport;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.CommandArgNotFound;
import Presenter.IPresenter;

import java.util.HashSet;
import java.util.Iterator;



/**
 * Команда вывода всех Transport больше указанного
 */
public class CountGreaterTransportCommand implements ICommand {

    private Transport transport;

    @Override
    public void execute(IPresenter presenter) {
        Iterator<Flat> it = presenter.getCollection().iterator();
        HashSet<Transport> passedTransports = new HashSet<Transport>();
        try {
            while (it.hasNext()) {
                Flat element = it.next();
                if (element.getTransport().compareTo(transport) > 0)
                {
                    passedTransports.add(element.getTransport());
                }
            }
        } catch (NullPointerException e) {
            presenter.getView().showError(e.getMessage());
        }
        int result = 0;
        for (Transport value : passedTransports) {
            result += 1;
        }
        presenter.getView().showResult(Integer.toString(result));
    }

    @Override
    public String getName() {
        return "count_greater_than_transport";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов, значение поля transport которых больше заданного";
    }

    @Override
    public String[] getArgsNames() {
        return new String[] {"transport"};
    }

    @Override
    public void setArg(String argName, String valueString) throws BadCommandArgException {
        try {
            switch (argName) {
                case "transport":
                    this.transport = Transport.valueOf(valueString);
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new BadCommandArgException(getName(), argName, Enum.class.getSimpleName());
        }
    }

    @Override
    public Object getArg(String argName) throws CommandArgNotFound {
        throw new CommandArgNotFound(getName(), argName);
    }

}
