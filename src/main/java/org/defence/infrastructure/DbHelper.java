package org.defence.infrastructure;

import org.defence.domain.entities.*;
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

	//    private static final String CHARACTERISTICS = "Characteristics.csv";
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

	public boolean addCharacteristicType(String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(new CharacteristicType(code, name));
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	public void addMeasurement(Integer typeId, String code, String name, String shortName) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			MeasurementType type = (MeasurementType) session.get(MeasurementType.class, typeId);
			type.getMeasurements().add(new Measurement(code, name, shortName));
			session.save(type);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean addCharacteristic(Integer typeId, String code, String name, List<Integer> measurementIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			CharacteristicType type = (CharacteristicType) session.get(CharacteristicType.class, typeId);

            /*// getting characteristic measurements
			List<Measurement> measurements
                    = session.createQuery("from Measurement m where m.id in (:measurementIdList)").setParameterList
                    ("measurementIdList",
                    measurementIdList).list();*/

			Characteristic characteristic = new Characteristic(code, name, null);

			// getting measurement list of characteristic
			List<Measurement> measurements;

			// if there is no any measurement belongs to characteristic
			if (measurementIdList != null && measurementIdList.size() != 0) {
				measurements = session.createQuery("from Measurement m where m.id in (:measurementIdList)")
						.setParameterList("measurementIdList",
								measurementIdList).list();
				characteristic.setMeasurements(new HashSet<>(measurements));
			}

			if (characteristic == null) {
				transaction.rollback();
				return false;
			}

			type.getCharacteristics().add(characteristic);
			session.save(type);
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

	public boolean addCharacteristicKit(String name, List<Integer> characteristicIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			CharacteristicKit kit = new CharacteristicKit(name);

			// getting measurement list of characteristic
			List<Characteristic> characteristics;

			// if there is any characteristic belongs to characteristicKit
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in (:characteristicIdList)")
						.setParameterList("characteristicIdList",
								characteristicIdList).list();
				kit.setCharacteristics(new HashSet<>(characteristics));
			}

			session.save(kit);
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

	public boolean addAssertedName(Integer formatId, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			DescriptionFormat format = (DescriptionFormat) session.get(DescriptionFormat.class, formatId);
			format.getAssertedNames().add(new AssertedName(code, name));

			session.save(format);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addDescriptionFormat(String code, String name, List<Integer> characteristicIdList) {

		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			DescriptionFormat format = new DescriptionFormat(code, name);

			// getting assertedName list of descriptionFormat
			List<Characteristic> characteristics;

			// if there is any characteristic belongs to descriptionFormat
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in " +
						"(:characteristicIdList)").setParameterList("characteristicIdList", characteristicIdList).list();
				format.setCharacteristics(new HashSet<>(characteristics));
			}

			/*// getting assertedName list of descriptionFormat
			List<AssertedName> assertedNames;

			// if there is no any measurement belongs to characteristic
			if (assertedNameIdList != null && assertedNameIdList.size() != 0) {
				assertedNames = session.createQuery("from AssertedName m where m.id in (:assertedNameIdList)")
						.setParameterList("assertedNameIdList",
								assertedNameIdList).list();
				format.setAssertedNames(new HashSet<>(assertedNames));
			}*/

			session.save(format);
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

	public boolean updateMeasurement(Integer typeId, Integer id, String code, String name, String shortName) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			MeasurementType type = (MeasurementType) session.get(MeasurementType.class, typeId);
			Measurement measurement = type.getMeasurements().stream().filter(p -> p.getId() == id).findFirst().get();

			if (measurement == null) {
				transaction.rollback();
				return false;
			}

			measurement.setCode(code);
			measurement.setName(name);
			measurement.setShortName(shortName);

			session.update(measurement);
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

	public boolean updateCharacteristic(Integer typeId, Integer id, String code, String name, List<Integer>
			measurementIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			CharacteristicType type = (CharacteristicType) session.get(CharacteristicType.class, typeId);
			Characteristic characteristic = type.getCharacteristics().stream().filter(p -> p.getId() == id).findFirst
					().get();

			// getting measurement list of characteristic
			List<Measurement> measurements;

			// if there is no any measurement belongs to characteristic
			if (measurementIdList != null && measurementIdList.size() != 0) {
				measurements = session.createQuery("from Measurement m where m.id in (:measurementIdList)")
						.setParameterList("measurementIdList",
								measurementIdList).list();
				characteristic.setMeasurements(new HashSet<>(measurements));
			} else {
				characteristic.setMeasurements(null);
			}

			if (characteristic == null) {
				transaction.rollback();
				return false;
			}

			characteristic.setCode(code);
			characteristic.setName(name);

			session.update(characteristic);
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

	public boolean updateCharacteristicKit(Integer id, String name, List<Integer> characteristicIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			CharacteristicKit kit = (CharacteristicKit) session.get(CharacteristicKit.class, id);
			kit.setName(name);

			// getting measurement list of characteristic
			List<Characteristic> characteristics;

			// if there is no any measurement belongs to characteristic
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in (:characteristicIdList)")
						.setParameterList("characteristicIdList",
								characteristicIdList).list();
				kit.setCharacteristics(new HashSet<>(characteristics));
			} else {
				kit.setCharacteristics(null);
			}

			// TODO: Странное условие
			if (kit == null) {
				transaction.rollback();
				return false;
			}

			session.update(kit);
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

	public boolean updateAssertedName(Integer formatId, Integer id, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			DescriptionFormat descriptionFormat = (DescriptionFormat) session.get(DescriptionFormat.class, formatId);
			AssertedName assertedName = descriptionFormat.getAssertedNames().stream().filter(p -> p.getId() == id)
					.findFirst().get();

			if (assertedName == null) {
				transaction.rollback();
				return false;
			}

			assertedName.setCode(code);
			assertedName.setName(name);

			session.update(assertedName);
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

	public boolean updateDescriptionFormat(Integer id, String code, String name, List<Integer> characteristicIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			DescriptionFormat format = (DescriptionFormat) session.get(DescriptionFormat.class, id);
			/*CharacteristicKit characteristicKit = descriptionFormat.getCharacteristicKits().stream().filter(p -> p
					.getId() == id).findFirst().get();

			// getting measurement list of characteristic
			List<Characteristic> characteristics;

			// if there is no any measurement belongs to characteristic
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in (:characteristicIdList)")
						.setParameterList("characteristicIdList",
								characteristicIdList).list();
				characteristicKit.setCharacteristics(new HashSet<>(characteristics));
			} else {
				characteristicKit.setCharacteristics(null);
			}

			if (characteristicKit == null) {
				transaction.rollback();
				return false;
			}*/

			List<Characteristic> allCharacteristic = session.createQuery("from Characteristic").list();
			Set<Characteristic> characteristics = new LinkedHashSet<>();

			for (Characteristic characteristic : allCharacteristic) {
				for (Integer characteristicId : characteristicIdList) {
					if (characteristic.getId() == characteristicId) {
						characteristics.add(characteristic);
						break;
					}
				}
			}

			format.setCode(code);
			format.setName(name);
			format.setCharacteristics(characteristics);

			session.update(format);
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

	public boolean updateCharacteristicType(Integer id, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			CharacteristicType type = (CharacteristicType) session.get(CharacteristicType.class, id);
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

	public List<CharacteristicType> getAllCharacteristicTypes() {
		Session session = factory.openSession();
		List<CharacteristicType> result = null;

		try {
			result = session.createQuery("from CharacteristicType order by id").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<Characteristic> getAllCharacteristics() {
		Session session = factory.openSession();
		List<Characteristic> result = null;

		try {
			result = session.createQuery("from Characteristic order by id").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<CharacteristicKit> getAllCharacteristicKits() {
		Session session = factory.openSession();
		List<CharacteristicKit> result = null;

		try {
			result = session.createQuery("from CharacteristicKit order by id").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<DescriptionFormat> getAllDescriptionFormats() {
		Session session = factory.openSession();
		List<DescriptionFormat> result = null;

		try {
			result = session.createQuery("from DescriptionFormat order by name").list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<Measurement> getMeasurementsByTypeId(Integer id) {
		Session session = factory.openSession();
		List<Measurement> result = null;

		try {
			MeasurementType type = (MeasurementType) session.createQuery("from MeasurementType where id = :id")
					.setParameter("id", id).uniqueResult();

			result = new LinkedList<>();
			result.addAll(type.getMeasurements().stream().sorted().collect(Collectors.toList()));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<Characteristic> getCharacteristicsByTypeId(Integer id) {
		Session session = factory.openSession();
		List<Characteristic> result = null;

		try {
			CharacteristicType type = (CharacteristicType) session.createQuery("from CharacteristicType where id = " +
					":id").setParameter("id", id).uniqueResult();

//            result = new LinkedList<>();
//            result.addAll(type.getCharacteristics().stream().sorted().collect(Collectors.toList()));
			result = type.getCharacteristics().stream().sorted().collect(Collectors.toList());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public Characteristic getCharacteristicById(Integer id) {
		Session session = factory.openSession();
		Characteristic result = null;

		try {
			result = (Characteristic) session.createQuery("from Characteristic where id = :id").setParameter("id", id)
					.uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public CharacteristicKit getCharacteristicKitById(Integer id) {
		Session session = factory.openSession();
		CharacteristicKit result = null;

		try {
			result = (CharacteristicKit) session.createQuery("from CharacteristicKit where id = :id").setParameter
					("id", id).uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public DescriptionFormat getDescriptionFormatById(Integer id) {
		Session session = factory.openSession();
		DescriptionFormat result = null;

		try {
			result = (DescriptionFormat) session.createQuery("from DescriptionFormat where id = :id").setParameter
					("id", id).uniqueResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<Measurement> getAllMeasurements() {
		Session session = factory.openSession();
		List<Measurement> result = null;

		try {
			result = session.createQuery("from Measurement order by name").list();
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
					System.out.println("\t" + measurement.getMeasurement().getName() + "; " + measurement
							.getMeasurement().getShortName());
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

	public boolean deleteMeasurement(Integer id) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Measurement measurement = (Measurement) session.get(Measurement.class, id);
			session.delete(measurement);
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

	public boolean deleteCharacteristic(Integer id) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Characteristic characteristic = (Characteristic) session.get(Characteristic.class, id);
			characteristic.getMeasurements().clear();
			session.delete(characteristic);
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

	//----------------------------------------------------------------------------------------------------------------------
	public static int importCharacteristicsIntoTable() throws Exception {
		int counter = 0;
//        URL test = DbHelper.class.getResource(CHARACTERISTICS);

//        String fileName = DbHelper.class.getResource(CHARACTERISTICS).getFile();

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
	}

	private static void importCharacteristicTypes() {
		if (measurementEntries.size() > 0) {
			Set<Characteristic> characteristics = new HashSet<>();

			// loading measurementTypes into CharacteristicType table
			for (final Map.Entry<Integer, CharacteristicType> entry : characteristicTypes.entrySet()) {
				List<CharacteristicEntry> list = characteristicEntries.stream().filter(
						p -> p.getGroupId() == entry.getKey()).collect(Collectors.toList());

				System.out.println(entry.getValue().getName());

				if (list.size() <= 0) {
					break;
				}

                /*for (CharacteristicEntry characteristic : list) {
					characteristics.add(characteristic.getMeasurement());
                    System.out.println("\t" + characteristic.getMeasurement().getName() + "; " + characteristic
                    .getMeasurement().getShortName());
                }*/

				entry.getValue().setCharacteristics(characteristics);
				loadCharacteristicType(entry);
				characteristics.clear();
			}

			System.out.println("\nMEASUREMENTS were loaded\n");
		}
	}

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
        /*int result = importMeasurementsIntoTable();
        System.out.format("%s measurements were loaded\n", result);*/

		int result = importCharacteristicsIntoTable();

		factory.close();
	}


}
