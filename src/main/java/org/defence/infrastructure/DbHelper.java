package org.defence.infrastructure;

import org.defence.domain.entities.Characteristic;
import org.defence.domain.entities.CharacteristicType;
import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 8/10/15.
 */
abstract class Entry {
    private int groupId;

    public Entry(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

class MeasurementEntry extends Entry {
    private Measurement measurement;

    public MeasurementEntry(int groupId, Measurement measurement) {
        super(groupId);
        this.measurement = measurement;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}

class CharacteristicEntry extends Entry {
    private Characteristic characteristic;

    public CharacteristicEntry(int groupId, Characteristic characteristic) {
        super(groupId);
        this.characteristic = characteristic;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }
}

public class DbHelper {
    private static DbHelper instance;

    private static SessionFactory factory;
    private static final String CHARACTERISTICS = "/home/contragent/Desktop/Characteristics.csv";
    private static final String GROUPS_CHARACTERISTICS = "/home/contragent/Desktop/GroupsCharacteristics.csv";
    private static final String GROUPS_MEASUREMENTS = "/home/contragent/Desktop/GroupsMeasurements.csv";
    private static final String MEASUREMENTS = "/home/contragent/Desktop/Measurements.csv";
    private static List<MeasurementEntry> measurementEntries = new ArrayList<>();
    private static Map<Integer, MeasurementType> measurementTypes = new HashMap<>();
    private static List<CharacteristicEntry> characteristicEntries = new ArrayList<>();
    private static Map<Integer, CharacteristicType> characteristicTypes = new HashMap<>();

    private DbHelper() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }

        return instance;
    }

    public static void terminateDbConnection() {
        if (factory == null) {
            return;
        }

        if (!factory.isClosed()) {
            factory.close();
            System.out.println("Connection was closed");
        }
    }

    public boolean exportCharacteristic(Characteristic characteristic) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(characteristic);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean addMeasurementType(MeasurementType measurementType) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(measurementType);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    public boolean addMeasurementType(String code, String name) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(new MeasurementType(code, name));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    public MeasurementType getMeasurementTypeById(int id) {
        Session session = factory.openSession();
        MeasurementType result = null;
        try {
            result = (MeasurementType) session.get(MeasurementType.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            return result;
        }
    }

    public boolean removeMeasurementType(int id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        Query query = session.createQuery("from MeasurementType where id = :id");
        query.setParameter("id", id);

        try {
            transaction = session.beginTransaction();
            session.delete(query.list().get(0));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    public boolean updateMeasurementType(Integer id, String code, String name) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            MeasurementType type = (MeasurementType) session.get(MeasurementType.class, id);
            type.setCode(code);
            type.setName(name);
            session.update(type);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    public List<MeasurementType> getAllMeasurementTypes() {
        Session session = factory.openSession();
        List<MeasurementType> result = null;

        try {
            result = session.createQuery("from MeasurementType order by id").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            return result;
        }
    }

    public List<Measurement> importAllMeasurements() {
        Session session = factory.openSession();
        List<Measurement> result = null;

        try {
            result = session.createQuery("from Measurement order by id").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            return result;
        }
    }

    public List<Measurement> getMeasurementsByMeasurementTypeId(Integer id) {
        Session session = factory.openSession();
        List<Measurement> result = null;

        try {
            MeasurementType type = (MeasurementType) session.createQuery("from MeasurementType where id = :id").setParameter("id", id).uniqueResult();

            result = new LinkedList<>();
            result.addAll(type.getMeasurements().stream().sorted().collect(Collectors.toList()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            return result;
        }
    }

    public List<Characteristic> importAllCharacteristics() {
        Session session = factory.openSession();
        List<Characteristic> result = null;

        try {
            result = session.createQuery("from Characteristic ").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
            return result;
        }
    }


    public static int importEntityIntoTable() throws Exception {
        int counter = 0;

        return counter;
    }

    public static int importMeasurementsIntoTable() throws Exception {
        int counter = 0;
        BufferedReader reader = new BufferedReader(new FileReader(MEASUREMENTS));
        String line;

        while ((line = reader.readLine()) != null) {
            parseMeasurementFormLine(line);
        }

        reader = new BufferedReader(new FileReader(GROUPS_MEASUREMENTS));
        while ((line = reader.readLine()) != null) {
            parseMeasurementTypeFromLine(line);
        }

        importMeasurementTypes();

        return counter;
    }

    private static boolean parseMeasurementTypeFromLine(String line) {
        String[] words = line.split(";");

        if (words.length != 2) {
            System.out.println("Error in line: " + line);
            return false;
        }

        String id = words[0];
        String name = words[1];

        try {
            measurementTypes.put(Integer.valueOf(id), new MeasurementType("code", name));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean parseMeasurementFormLine(String line) {
        String[] words = line.split(";");

        if (words.length != 4) {
            System.out.println("Error in line: " + line);
            return false;
        }

        String name = words[1];
        String shortName = words[2];
        String groupNumber = words[3];

        Measurement measurement = new Measurement("", name, shortName);

        try {
            measurementEntries.add(new MeasurementEntry(Integer.valueOf(groupNumber), measurement));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static void importMeasurementTypes() throws ExceptionInInitializerError {
        if (measurementEntries.size() > 0) {
            Set<Measurement> measurements = new HashSet<>();

            // loading measurementTypes into MeasurementType table
            for (final Map.Entry<Integer, MeasurementType> entry : measurementTypes.entrySet()) {
                List<MeasurementEntry> list = measurementEntries.stream().filter(
                        p -> p.getGroupId() == entry.getKey()).collect(Collectors.toList());

                System.out.println(entry.getValue().getName());

                if (list.size() <= 0) {
                    break;
                }

                for (MeasurementEntry measurement : list) {
                    measurements.add(measurement.getMeasurement());
                    System.out.println("\t" + measurement.getMeasurement().getName() + "; " + measurement.getMeasurement().getShortName());
                }

                entry.getValue().setMeasurements(measurements);
                loadMeasurementType(entry);
                measurements.clear();
            }

            System.out.println("\nMEASUREMENTS were loaded\n");
        }
    }

    private static void loadMeasurementType(Map.Entry<Integer, MeasurementType> entry) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entry.getValue());
        transaction.commit();
    }

//----------------------------------------------------------------------------------------------------------------------
    /*public static int importCharacteristicsIntoTable() throws Exception {
        int counter = 0;
        BufferedReader reader = new BufferedReader(new FileReader(CHARACTERISTICS));
        String line;

        while ((line = reader.readLine()) != null) {
            parseCharacteristicFormLine(line);
        }

        reader = new BufferedReader(new FileReader(GROUPS_CHARACTERISTICS));
        while ((line = reader.readLine()) != null) {
            parseCharacteristicTypeFromLine(line);
        }

        importCharacteristicTypes();

        return counter;
    }

    private static boolean parseCharacteristicFormLine(String line) {
        String[] words = line.split(";");

        if (words.length != 2) {
            System.out.println("Error in line: " + line);
            return false;
        }

        String id = words[0];
        String name = words[1];

        try {
            characteristicTypes.put(Integer.valueOf(id), new CharacteristicType("code", name));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean parseCharacteristicTypeFromLine(String line) {
        String[] words = line.split(";");

        if (words.length != 4) {
            System.out.println("Error in line: " + line);
            return false;
        }

        String name = words[1];
        String shortName = words[2];
        String groupNumber = words[3];

        Characteristic characteristic = new Characteristic("", name);

        try {
            characteristicEntries.add(new CharacteristicEntry(Integer.valueOf(groupNumber), characteristic));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return true;
    }*/

    /*private static void importCharacteristicTypes() {
        if (measurementEntries.size() > 0) {
            Set<Characteristic> characteristics = new HashSet<Characteristic>();

            // loading measurementTypes into CharacteristicType table
            for (final Map.Entry<Integer, CharacteristicType> entry : characteristicTypes.entrySet()) {
                List<CharacteristicEntry> list = characteristicEntries.stream().filter(
                        p -> p.getGroupId() == entry.getKey()).collect(Collectors.toList());

                System.out.println(entry.getValue().getName());

                if (list.size() <= 0) {
                    break;
                }

                for (CharacteristicEntry characteristic : list) {
                    characteristics.add(characteristic.getMeasurement());
                    System.out.println("\t" + characteristic.getMeasurement().getName() + "; " + characteristic.getMeasurement().getShortName());
                }

                entry.getValue().setCharacteristics(characteristics);
                loadCharacteristicType(entry);
                characteristics.clear();
            }

            System.out.println("\nMEASUREMENTS were loaded\n");
        }
    }*/

    private static void loadCharacteristicType(Map.Entry<Integer, CharacteristicType> entry) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entry.getValue());
        transaction.commit();
    }

//----------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        if (factory == null) {
            factory = new Configuration().configure().buildSessionFactory();
        }
        int result = importMeasurementsIntoTable();
        System.out.format("%s measurements were loaded\n", result);

        factory.close();
    }
}
