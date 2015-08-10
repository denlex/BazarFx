package org.defence.infrastructure;

import org.defence.domain.entities.Measurement;
import org.defence.domain.entities.MeasurementType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by root on 8/10/15.
 */
class MeasurementEntry {
    private int groupId;
    private Measurement measurement;

    public MeasurementEntry(int groupId, Measurement measurement) {
        this.groupId = groupId;
        this.measurement = measurement;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }
}

public class DbHelper {
    private static SessionFactory factory;
    private static final String CHARACTERISTICS = "/home/contragent/Desktop/Characteristics.csv";
    private static final String GROUPS_CHARACTERISTICS = "/home/contragent/Desktop/GroupsCharacteristics.csv";
    private static final String GROUPS_MEASUREMENTS = "/home/contragent/Desktop/GroupsMeasurements.csv";
    private static final String MEASUREMENTS = "/home/contragent/Desktop/Measurements.csv";
    private static List<MeasurementEntry> measurementEntries = new ArrayList<MeasurementEntry>();
    private static Set<Integer> measurementGroupsId = new HashSet<Integer>();
    private static Map<Integer, MeasurementType> measurementTypes = new HashMap<Integer, MeasurementType>();

    public static int importMeasurementsIntoTable() throws Exception {
        int counter = 0;
        BufferedReader reader = new BufferedReader(new FileReader(MEASUREMENTS));
        String line;

        while ((line = reader.readLine()) != null) {
            if (parseMeasurementFormLine(line)) {
                counter++;
            }
        }

        System.out.println("Measurement file was parsed!");

        reader = new BufferedReader(new FileReader(GROUPS_MEASUREMENTS));
        while ((line = reader.readLine()) != null) {
            if (parseMeasurementTypeFromLine(line)) {

            }
        }

        System.out.println("MeasurementType file was parsed!");

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
            factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            Set<Measurement> measurements = new HashSet<Measurement>();

            // loading measurementTypes into MeasurementType table
            for (Map.Entry<Integer, MeasurementType> entry : measurementTypes.entrySet()) {

                for (MeasurementEntry measurementEntry : measurementEntries) {
                    if (entry.getKey() == measurementEntry.getGroupId()) {
                        measurements.add(measurementEntry.getMeasurement());
                    }
                }

                if (measurements.size() > 0) {
                    entry.getValue().setMeasurements(measurements);
                    session.save(entry.getValue());
                    measurements.clear();
                }
            }

            // loading measurements into Measurement table
            for (MeasurementEntry measurementEntry : measurementEntries) {
                session.save(measurementEntry.getMeasurement());
            }

            System.out.println("\nMEASUREMENTS were loaded\n");

            transaction.commit();
        }
    }

    public static void main(String[] args) throws Exception {
        int result = importMeasurementsIntoTable();
        System.out.format("%s measurements were loaded\n", result);
    }
}
