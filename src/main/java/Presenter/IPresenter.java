package Presenter;

import Model.DataClasses.Flat;
import Model.IModel;
import Presenter.Commands.ICommand;
import Presenter.Exceptions.BadCommandArgException;
import Presenter.Exceptions.BadCommandArgsNumberException;
import Presenter.Exceptions.CommandNotFoundException;
import View.IView;

import java.util.Stack;

public interface IPresenter {

    /**
     * @return Все команды
     */
    Stack<IView> getViews();

    /**
     * @return Все доступные команды
     */
    ICommand[] getCommands();

    /**
     *
     * @param commandWithArgs Имя команды и аргументы в виде отдельных строк
     * @return Команда с этим именем и присвоенными аргументами
     * @throws CommandNotFoundException Если нет команды с таким именем
     * @throws BadCommandArgException Если аргумент не удалось присовить
     * @throws BadCommandArgsNumberException При некорректном числе переданных аргументов
     */
    ICommand getCommandByName(String[] commandWithArgs) throws CommandNotFoundException, BadCommandArgException, BadCommandArgsNumberException;

    /**
     *
     * @return Отображение
     */
    IView getView();

    /**
     * Добавить отображение в стек исполнения отображений
     *
     * @param view Отображение для добавления
     */
    void addView(IView view);

    /**
     *
     * @return Модель данных
     */
    IModel getModel();

    /**
     *
     * @param model Модель для присоединения
     */
    void setModel(IModel model);

    /**
     *
     * @return Коллекция данных
     */
    DataList <Flat> getCollection();

    /**
     * Начать работу с текущим представлением
     */
    void start();

    /**
     * Остановить работу с текущим представлением
     */
    void stop();

}
