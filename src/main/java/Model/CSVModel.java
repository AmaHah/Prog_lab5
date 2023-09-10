package Model;

import Model.DataClasses.*;
import Model.Exceptions.*;
import Presenter.IPresenter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.LineValidator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CSVModel implements IModel {

    private IPresenter presenter;

    public CSVModel() {
        this.presenter = null;
    }

    /**
     *
     * @param presenter Представление, которое можно присоединить к модели
     */
    public CSVModel(IPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Загружает коллекцию из файла CSV
     * @return возвращает Flat[] с элементами из файла
     */
    @Override
    public Flat[] loadData() throws LoadFailedException {
        List<Flat> result = new ArrayList<Flat>();
        try {
            String CSVData = readCSV();
            CSVReader reader = new CSVReader(new StringReader(CSVData));

            String[] lineInArray;

            boolean isHeader = true;
            while ((lineInArray = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    String test = Arrays.toString(lineInArray).trim().replaceAll("[\\[\\]]", "").replace(", ", ",");
                    if (!Arrays.toString(lineInArray).trim().replaceAll("[\\[\\]]", "").replace(", ", ",").
                            equals("id,name,x,y,creationdate,area,numberOfRooms,floor,numberOfBathrooms,transport,housename,year,numberOfLifts")
                    )
                        throw new LoadFailedException("");
                    continue;
                }
                result.add(parseCsvLine(lineInArray));
            }
        } catch (LoadFailedException | ClassCastException | DateTimeParseException | CsvValidationException |ValidationFailedException | BadIdException |
                 UserInputException | IOException | NumberFormatException e) {
            throw new LoadFailedException("Проблема при парсинге CSV файла");
        }
        return result.toArray(new Flat[result.size()]);
    }

    /**
     * Формирует строку для записи коллекции в файл
     * @param data Набор данных из объектов Flat
     * @throws SaveFailedException
     */
    @Override
    public String saveData(Flat[] data) throws SaveFailedException {
        StringBuilder result = new StringBuilder();
        result.append("id,name,x,y,creationdate,area,numberOfRooms,floor,numberOfBathrooms,transport,housename,year,numberOfLifts\n");
        if (data != null) {
            for (Flat element : data)
                result.append(convertToСSV(element) + "\n");

        }
        writeCSV(result.toString());
        return getFilePath();
    }

    /**
     * Записывает Flat в строку в csv формате
     * @param flat элемент коллекции
     * @return строку csv
     */
    private String convertToСSV(Flat flat) {
        StringBuilder result = new StringBuilder();
        result.append(flat.getId()).append(',');
        result.append(flat.getName()).append(',');
        result.append(flat.getCoordinates().getX()).append(',');
        result.append(flat.getCoordinates().getY()).append(',');
        result.append(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss").format(flat.getCreationDate())).append(',');
        result.append(flat.getArea()).append(',');
        result.append(flat.getNumberOfRooms()).append(',');
        result.append(flat.getFloor()).append(',');
        result.append(flat.getNumberOfBathrooms()).append(',');
        result.append(flat.getTransport()).append(',');
        result.append(flat.getHouse().getName()).append(',');
        result.append(flat.getHouse().getYear()).append(',');
        result.append(flat.getHouse().getNumberOfLifts());

        return result.toString();
    }

    /**
     * Парсит строку во Flat
     * @param flatData
     * @return
     * @throws IllegalArgumentException
     * @throws DateTimeParseException
     * @throws ValidationFailedException
     * @throws BadIdException
     * @throws UserInputException
     * @throws NumberFormatException
     */
    private Flat parseCsvLine(String[] flatData) throws IllegalArgumentException, DateTimeParseException, ValidationFailedException, BadIdException, UserInputException, NumberFormatException {
        Long id = Long.parseLong(flatData[0]);
        String name = flatData[1];
        Integer x = Integer.parseInt(flatData[2]);
        Double y = Double.parseDouble(flatData[3]);
        Coordinates coordinates = new Coordinates(x, y);
        LocalDateTime date = LocalDateTime.parse(flatData[4], DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
        ZonedDateTime creationDate = date.atZone(ZoneId.systemDefault());
        float area = Float.parseFloat(flatData[5]);
        long numberOfRooms = Long.parseLong(flatData[6]);
        int floor = Integer.parseInt(flatData[7]);
        long numberOfBathrooms = Long.parseLong(flatData[8]);
        Transport transport = Transport.valueOf(flatData[9]);
        String houseName = flatData[10];
        Integer year = Integer.parseInt(flatData[11]);
        int numberOfLifts = Integer.parseInt(flatData[12]);
        House house = new House(houseName, year, numberOfLifts);

        Flat parsedObj = new Flat(id, name, coordinates, area, numberOfRooms, floor, numberOfBathrooms, transport, house);
        parsedObj.setCreationDate(creationDate);
        return parsedObj;
    }

    /**
     * Читает файл построчно
     * @return возвращает строку
     * @throws LoadFailedException
     */
    private String readCSV() throws LoadFailedException {
        try {
            Scanner sc = new Scanner(getFileForReading());
            StringBuilder result = new StringBuilder();
            while (sc.hasNext()) {
                    result.append(sc.nextLine()).append('\n');
            }
            return result.toString();
        } catch (FileNotFoundException | LoadFailedException e) {
            throw new LoadFailedException(String.format("Невозможно получить доступ к файлу \"%s\" (не найден или недостаточно прав)", getFilePath()));
        } catch (NoSuchElementException e) {
            throw new LoadFailedException("Не удалось прочитать файл");
        }
    }

    /**
     * Записывает коллекцию в CSV файл
     * @param CSVData коллекция в csv формате
     * @throws SaveFailedException
     */
    private void writeCSV(String CSVData) throws SaveFailedException {
        try {
            OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(getFileForWriting()));
            fs.write(CSVData);
            fs.flush();
        } catch (FileNotFoundException e) {
            throw new SaveFailedException (e.getMessage());
        } catch (IOException e) {
            throw new SaveFailedException("Не удалось записать файл");
        }
    }

    /**
     * Получает путь к файлу
     * @return возвращает путь
     */
    private String getFilePath() {
        String ENV_VAR = "LAB5_DATA_PATH";
        String result = System.getenv(ENV_VAR);
        if (result == null) {
            result = Paths.get(System.getProperty("user.dir"), "flat-data.csv").toAbsolutePath().toString();
        }
        return result;
    }

    /**
     * Проверяет возможность чтения файла
     * @return File, для чтения
     * @throws LoadFailedException
     */
    private File getFileForReading() throws LoadFailedException {
        File f = new File(getFilePath());
        if (!f.exists() | !f.isFile())
            throw new LoadFailedException(String.format("Файл \"%s\" не найден", getFilePath()));
        if (!f.canRead())
            throw new LoadFailedException(String.format("Недостаточно прав для чтения файла \"%s\"", getFilePath()));
        return f;
    }

    /**
     * Проверяет возможность записи в файл
     * @return File для записи
     * @throws FileNotFoundException
     */
    private File getFileForWriting() throws FileNotFoundException {
        File f = new File(getFilePath());
        if (!f.exists() | !f.isFile())
            throw new FileNotFoundException(String.format("Файл \"%s\" не найден", getFilePath()));
        if (!f.canWrite())
            throw new FileNotFoundException(String.format("Недостаточно прав для записи файла \"%s\"", getFilePath()));
        return f;
    }

}