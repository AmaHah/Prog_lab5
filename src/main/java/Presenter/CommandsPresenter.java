package Presenter;

import Model.DataClasses.Flat;
import Model.Exceptions.LoadFailedException;
import Model.IModel;
import Presenter.Commands.*;
import Presenter.Exceptions.*;
import View.IView;

import java.util.Stack;

public class CommandsPresenter implements IPresenter {

    private Stack<IView> views;
    private IModel model;
    private DataList<Flat> collection;

    /**
     *
     * @param view Отображение
     * @param model Модель данных
     */
    public CommandsPresenter(IView view, IModel model) {
        views = new Stack<IView>();
        addView(view);
        this.model = model;
        view.setPresenter(this);
        initCollection();
    }

    public Stack<IView> getViews() {
        return views;
    }

    private void initCollection() {
        try {
            this.collection = new DataList<Flat>(this.model.loadData());
        } catch (LoadFailedException e) {
            getView().showError(String.format("%s. Будет создана пустая коллекция", e.getMessage()));
            this.collection = new DataList<Flat>();
        }
    }

    @Override
    public ICommand[] getCommands() {
        return new ICommand[] {
                new HelpCommand(),
                new InfoCommand(),
                new ShowCommand(),
                new AddCommand(),
                new UpdateCommand(),
                new RemoveByIdCommand(),
                new ClearCommand(),
                new SaveCommand(),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new AddMinCommand(),
                new RemoveLast(),
                new PrintSumNumberOfRoomsCommand(),
                new CountGreaterTransportCommand(),
                new FilterGreaterHouseCommand(),
                new HistoryCommand()

        };
    }

    @Override
    public IView getView() {
        return this.views.peek();
    }

    @Override
    public void addView(IView view) {
        this.views.push(view);
    }

    @Override
    public IModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public DataList<Flat> getCollection() {
        return this.collection;
    }

    @Override
    public void start() {
        if (this.views.isEmpty())
            return;

        IView currView = this.views.peek();

        while (!this.views.isEmpty() && currView == this.views.peek()) {
            ICommand currentCommand;
            try {
                currentCommand = currView.readCommand();
            } catch (CommandNotFoundException | BadCommandArgException | BadCommandArgsNumberException e) {
                currView.showError(e.getMessage());
                continue;
            } catch (NullCommandException e) {
                continue;
            } catch (InputEndedException e) {
                stop();
                break;
            }

            if (currentCommand != null) {
                if (currView.getIsScriptMode()) {
                    currView.showInfo(String.format("Исполнение команды \"%s%s\"", currentCommand.getName(), getCommandArgs(currentCommand)));
                }

                currentCommand.execute(this);
            }
        }
    }

    @Override
    public void stop() {
        if (!this.views.isEmpty())
            this.views.pop();
    }

    @Override
    public ICommand getCommandByName(String[] commandWithArgs) throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException {
        String commandName = commandWithArgs[0];
        for (ICommand command : getCommands()) {
            if (command.getName().equals(commandName)) {
                if (command.getArgsNames() != null) {
                    if (command.getArgsNames().length != commandWithArgs.length - 1)
                        throw new BadCommandArgsNumberException(commandName, commandWithArgs.length - 1, command.getArgsNames().length);
                    for (int i = 0; i < command.getArgsNames().length; i++) {
                        String arg = command.getArgsNames()[i];
                        command.setArg(arg, commandWithArgs[i + 1]);
                    }
                }

                return command;
            }
        }
        throw new CommandNotFoundException(commandName);
    }

    private String getCommandArgs(ICommand command) {
        StringBuilder commandArgs = new StringBuilder();
        try {
            if (command.getArgsNames() != null && command.getArgsNames().length != 0) {
                for (String argName : command.getArgsNames()) {
                    commandArgs.append(" " + command.getArg(argName).toString());
                }
            }
        } catch (CommandArgNotFound e) {
            getView().showError(e.getMessage());
        }
        return commandArgs.toString();
    }
}

