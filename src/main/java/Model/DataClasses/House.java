package Model.DataClasses;

import Model.Exceptions.UserInputException;


public class House implements Comparable<House>{
    /** Поле название дома */
    private String name; //Поле может быть null
    /** Поле возраст дома */
    private long year; //Максимальное значение поля: 307, Значение поля должно быть больше 0
    /** Поле количество лифтов */
    private Integer numberOfLifts; //Значение поля должно быть больше 0


    public House(String name, long year, Integer numberOfLifts) throws UserInputException {
        setName(name);
        setYear(year);
        setNumberOfLifts(numberOfLifts);
    }

    public void setName(String name) throws UserInputException {
        checkName(name);
        this.name = name;
    }

    /**
     * Возвращает значение поля name
     */
    public String getName() {
        return this.name;
    }

    public static void checkName(String name) throws UserInputException {
        if (name == null)
            throw new UserInputException("Название дома не может быть пустым");
    }

    public void setYear(long year) throws UserInputException {
        checkYear(year);
        this.year = year;
    }

    public long getYear() {
        return year;
    }

    public static void checkYear(long year) throws UserInputException {
        if (year <= 0)
            throw new UserInputException("Возраст дома должен быть больше 0");
        if (year > 307)
            throw new UserInputException("Возраст дома превышает максимальное значение - 307");
    }

    public void setNumberOfLifts(int numberOfLifts) throws UserInputException {
        checkNumberOfLifts(numberOfLifts);
        this.numberOfLifts = numberOfLifts;
    }

    public int getNumberOfLifts() {
        return numberOfLifts;
    }

    public static void checkNumberOfLifts(Integer numberOfLifts) throws UserInputException {
        if (numberOfLifts <= 0)
            throw new UserInputException("Количество лифтов в доме должно быть больше 0");
    }

    /**
     * Сравнить два дома по возрасту, если одинаково, то по количеству лифтов в домах
     */
    @Override
    public int compareTo(House o) {
        if (Long.compare(this.year, o.year) == 0)
            return Integer.compare(this.getNumberOfLifts(), o.getNumberOfLifts());
        else
            return Long.compare(this.year, o.year);
    }
}