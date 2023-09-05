package Presenter.Exceptions;

/**
 * Ошибка окончания ввода (например, дошли до EOF)
 */
public class InputEndedException extends Exception {
    public InputEndedException(String message) {
        super(message);
    }
}
