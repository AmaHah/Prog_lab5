package Model.DataClasses;

import Model.Exceptions.BadIdException;
import Model.Exceptions.UserInputException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Дракон, основной объект данных
 *
 */
public class Flat implements Comparable<Flat> {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private long numberOfRooms; //Значение поля должно быть больше 0
    private int floor; //Значение поля должно быть больше 0§
    private long numberOfBathrooms; //Значение поля должно быть больше 0
    private Transport transport; //Поле может быть null
    private House house; //Поле может быть null


    /**
     *
     * @param id id
     * @param name Название квартиры
     * @param coordinates Координаты
     * @param area Размер квартиры
     * @param numberOfRooms количество комнат
     * @param floor Этаж квартиры
     * @param numberOfBathrooms Количество ванных комнат
     * @param transport количество машин
     * @param house Дом квартиры
     * @throws UserInputException Если параметры не соответствуют ограничениям
     * @throws BadIdException Если id не соответствует ограничениям
     */
    public Flat(Long id, String name, Coordinates coordinates, float area, long numberOfRooms, int floor, long numberOfBathrooms, Transport transport, House house) throws BadIdException, UserInputException {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        generateCreationDate();
        setArea(area);
        setNumberOfRooms(numberOfRooms);
        setFloor(floor);
        setNumberOfBathrooms(numberOfBathrooms);
        setTransport(transport);
        setHouse(house);
    }

    /**
     * Проверит поле id на соответствие ограничениям
     *
     * @param id id
     * @throws BadIdException Если не соответствует ограничениям
     */
    public static void checkId(Long id) throws BadIdException {
        if (id == null)
            throw new BadIdException("id не может быть пустым");
        if (id <= 0)
            throw new BadIdException("id должно быть больше 0");
    }

    /**
     * Проверит поле name на соответствие ограничениям
     *
     * @param name Название квартиры
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkName(String name) throws UserInputException {
        if (name == null || name.isBlank())
            throw new UserInputException("Имя не может быть пустым");
    }

    /**
     * Проверит поле coordinates на соответствие ограничениям
     *
     * @param coordinates Координаты
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkCoordinates(Coordinates coordinates) throws UserInputException {
        if (coordinates == null) {
            throw new UserInputException("Значение координат не может быть пустым");
        }
    }

    /**
     * Проверит поле area на соответствие ограничениям
     *
     * @param area Размер квартиры
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkArea(float area) throws UserInputException {
        if (area <= 0)
            throw new UserInputException("Территория должна быть больше 0");
    }

    /**
     * Проверит поле transport на соответствие ограничениям
     *
     * @param transport Цвет Дракона
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkTransport(Transport transport) throws UserInputException {
        if (transport == null)
            throw new UserInputException("Цвет не может быть пустым");
    }

    public static void checkCreationDate(java.time.ZonedDateTime creationDate) throws UserInputException {
        if (creationDate == null)
            throw new UserInputException("Дата создания не может быть пустой");
    }

    /**
     * Проверит поле numberOfRooms на соответствие ограничениям
     *
     * @param numberOfRooms Количество комнат в квартире
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkNumberOfRooms(long numberOfRooms) throws UserInputException {
        if (numberOfRooms < 0)
            throw new UserInputException("Количество комнат должно быть больше 0");
    }

    /**
     * Проверит поле numberOfBathrooms на соответствие ограничениям
     *
     * @param numberOfBathrooms Количество ванных комнат в квартире
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkNumberOfBathrooms(long numberOfBathrooms) throws UserInputException {
        if (numberOfBathrooms < 0)
            throw new UserInputException("Количество ванных комнат должно быть больше 0");
    }

    /**
     * Проверит поле floor на соответствие ограничениям
     *
     * @param floor Этаж квартиры
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkFloor(int floor) throws UserInputException {
        if (floor < 0)
            throw new UserInputException("Этаж квартиры должен быть больше 0");
    }

    /**
     * Проверит поле head на соответствие ограничениям
     *
     * @param house Дом квартиры
     * @throws UserInputException Если не соответствует ограничениям
     */
    public static void checkHouse(House house) throws UserInputException {
        if (house == null)
            throw new UserInputException("Дом не может быть пустым");
    }

    /**
     * Устанавливает параметр id в поле id, поле проверяется перед установкой
     * @param id id
     * @throws BadIdException Если id не удовлетворяет ограничениям
     */
    public void setId(Long id) throws BadIdException {
        checkId(id);
        this.id = id;
    }

    /**
     * Возвращает значение поля id
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает параметр name в поле name, поле проверяется перед установкой
     * @param name Название квартиры
     * @throws UserInputException Если не соответствует ограничениям
     */
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

    /**
     * Устанавливает значение параметра coordinates в значение поля coordinates, значение проверяется перед установкой на соответствие ограничениям
     * @param coordinates Новые координаты
     * @throws UserInputException Если не соответствует ограничениям
     */
    public void setCoordinates(Coordinates coordinates) throws UserInputException {
        checkCoordinates(coordinates);
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setArea(float area) throws UserInputException {
        checkArea(area);
        this.area = area;
    }

    public float getArea() {
        return this.area;
    }

    public void setTransport(Transport transport) throws UserInputException {
        checkTransport(transport);
        this.transport = transport;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setCreationDate(java.time.ZonedDateTime creationDate) throws UserInputException {
        checkCreationDate(creationDate);
        this.creationDate = creationDate;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void generateCreationDate() {
        this.creationDate = ZonedDateTime.now(ZoneId.systemDefault());
    }

    public void setNumberOfRooms(long numberOfRooms) throws UserInputException {
        checkNumberOfRooms(numberOfRooms);
        this.numberOfRooms = numberOfRooms;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfBathrooms(long numberOfBathrooms) throws UserInputException {
        checkNumberOfBathrooms(numberOfBathrooms);
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public long getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setFloor(int floor) throws UserInputException {
        checkFloor(floor);
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void setHouse(House house) throws UserInputException {
        checkHouse(house);
        this.house = house;
    }

    public House getHouse() {
        return house;
    }

    @Override
    public String toString() {
        String creationDateString = DateTimeFormatter.ofPattern("dd/MM/yyyy - k:m").format(creationDate);
        return String.format("""
                        ID: %d
                        Название квартиры: %s
                        Текущие координаты: %s
                        Дата создания: %s
                        Размер квартиры: %f
                        Количество комнат: %d
                        Этаж: %d
                        Количество ванных комнат: %s
                        Количество машин: %s
                        Название дома: %s
                        Возраст дома: %d
                        Количество лифтов в доме: %d
                        """,
                this.id, this.name, this.coordinates, creationDateString, this.area, this.numberOfRooms, this.floor, this.numberOfBathrooms, this.transport, this.house.getName(), this.house.getYear(), this.house.getNumberOfLifts()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            Flat convObj = (Flat) obj;
            return convObj.getId().equals(getId());
        } else {
            return false;
        }
    }

    /**
     * Сравнивает двух Flat сначала по возрасту, если равны, то по координатам, если опять равны, то по имени, если снова равны, то по id
     *
     * @param o Объект класса Flat, с которым будет идти сравнение
     */
    @Override
    public int compareTo(Flat o) {
        final float VERY_LOW_NUMBER = 0.001f;
        if ((o.getArea() - this.area) < VERY_LOW_NUMBER) {
            if (o.getCoordinates().equals(this.coordinates)) {
                if (o.getName().equals(this.name)) {
                    return (int) (this.id - o.getId());
                } else {
                    return this.name.compareTo(o.getName());
                }
            } else {
                return this.coordinates.compareTo(o.getCoordinates());
            }
        } else {
            return Float.compare(this.area, o.area);
        }
    }
}