package Presenter.Exceptions;

public class BadCommandArgException extends Exception{
    public BadCommandArgException(String commandName, String argName, String expectedTypeName) {
        super(String.format("Неподходящее значение для аргумента \"%s\" команды \"%s\" (ожидалось значение типа: %s)", argName, commandName, expectedTypeName));
    }
}
