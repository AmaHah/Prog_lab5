package Model;

import Model.DataClasses.Flat;
import Model.Exceptions.LoadFailedException;
import Model.Exceptions.SaveFailedException;

import java.text.DateFormat;
import java.util.Locale;

public interface IModel {

    /**
     * Сохранить набор данных
     *
     * @param data Набор данных из объектов Flat
     * @return В случае успеха путь к данным или какую-то информацию
     * @throws SaveFailedException Если не удалось сохранить
     */
    String saveData(Flat[] data) throws SaveFailedException;

    /**
     * Загрузить набор данных
     *
     * @return Набор данных из объектов Flat
     * @throws LoadFailedException Если не удалось загрузить
     */
    Flat[] loadData() throws LoadFailedException;

    /**
     * Полуить формат дат по умолчанию
     *
     * @return Формат дат
     */
    static DateFormat getDefaultDateFormat() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale("ru"));
    };
}
