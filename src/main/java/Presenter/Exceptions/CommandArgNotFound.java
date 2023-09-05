package Presenter.Exceptions;

public class CommandArgNotFound extends Exception {
    public CommandArgNotFound(String commandName, String argName) {
        super(String.format("У команды \"%s\" нет аргумента \"%s\"", commandName, argName));
    }
}
