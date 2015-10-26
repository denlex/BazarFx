package org.defence.infrastructure;

import org.defence.domain.entities.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public MeasurementType addMeasurementType(MeasurementType measurementType) {
		Session session = factory.openSession();
		Transaction transaction = null;
//		MeasurementType newType = null;

		try {
//			newType = new MeasurementType(measurementType.getCode(), measurementType.getName());
			transaction = session.beginTransaction();
			session.save(measurementType);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
			return measurementType;
		}
	}

	public MeasurementType addMeasurementType(String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;
		MeasurementType newType = null;

		try {
			newType = new MeasurementType(code, name);
			transaction = session.beginTransaction();
			session.save(newType);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
			return newType;
		}
	}

	public CharacteristicType addCharacteristicType(String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;
		CharacteristicType newType = null;

		try {
			newType = new CharacteristicType(code, name);
			transaction = session.beginTransaction();
			session.save(newType);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			session.close();
			return newType;
		}
	}

	public Measurement addMeasurement(Integer typeId, String code, String name, String shortName) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Measurement newMeasurement = null;

		try {
			newMeasurement = new Measurement(code, name, shortName);
			transaction = session.beginTransaction();
			MeasurementType type = (MeasurementType) session.get(MeasurementType.class, typeId);

			type.getMeasurements().add(newMeasurement);
			session.save(type);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return newMeasurement;
		}
	}

	public Characteristic addCharacteristic(Integer typeId, String code, String name, List<Integer>
			measurementIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Characteristic newCharacteristic = null;

		try {
			newCharacteristic = new Characteristic(code, name, null);
			transaction = session.beginTransaction();
			CharacteristicType type = (CharacteristicType) session.get(CharacteristicType.class, typeId);

			// getting measurement list of characteristic
			List<Measurement> measurements;

			// if there is no any measurement belongs to characteristic
			if (measurementIdList != null && measurementIdList.size() != 0) {
				measurements = session.createQuery("from Measurement m where m.id in (:measurementIdList)")
						.setParameterList("measurementIdList",
								measurementIdList).list();
				newCharacteristic.setMeasurements(new ArrayList<>(measurements));
			}

			if (newCharacteristic == null) {
				transaction.rollback();
				return null;
			}

			type.getCharacteristics().add(newCharacteristic);
			session.save(type);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return newCharacteristic;
		}
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
				kit.setCharacteristics(new ArrayList<>(characteristics));
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

	public AssertedName addAssertedName(Integer formatId, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;
		AssertedName newName = new AssertedName(code, name);

		try {
			transaction = session.beginTransaction();

			DescriptionFormat format = (DescriptionFormat) session.get(DescriptionFormat.class, formatId);
			format.getAssertedNames().add(newName);
			session.save(format);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return newName;
		}
	}

	public CharacteristicValue addCharacteristicValue(Characteristic characteristic, String value) {
		Session session = factory.openSession();
		Transaction transaction = null;
		CharacteristicValue characteristicValue = null;

		try {
			characteristicValue = new CharacteristicValue(characteristic, value);

			transaction = session.beginTransaction();
			session.save(characteristicValue);
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
			return characteristicValue;
		}
	}

	public CatalogDescription addCatalogDescriptionWhileImport(Integer assertedNameId, String code, String name, List<CharacteristicValue>
			values) {
		Session session = factory.openSession();
		Transaction transaction = null;
		CatalogDescription description = null;

		try {
			// refresh object references which point on the same object
			for (CharacteristicValue value : values) {
				value.setCharacteristic((Characteristic) session.get(Characteristic.class, value
						.getCharacteristic().getId()));
			}

			description = new CatalogDescription(code, name, values);
			AssertedName assertedName = (AssertedName) session.get(AssertedName.class, assertedNameId);
			assertedName.getCatalogDescriptions().add(description);
			transaction = session.beginTransaction();
			session.save(assertedName);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return description;
		}
	}

	public CatalogDescription addCatalogDescription(Integer assertedNameId, String code, String name, List<CharacteristicValue>
			values) {
		Session session = factory.openSession();
		Transaction transaction = null;
		CatalogDescription description = null;

		try {
			description = new CatalogDescription(code, name, values);
			AssertedName assertedName = (AssertedName) session.get(AssertedName.class, assertedNameId);
			assertedName.getCatalogDescriptions().add(description);
			transaction = session.beginTransaction();
			session.save(assertedName);
			transaction.commit();
		} catch (Exception ex) {
			System.out.println("Error when catalogDescription add process!");
			transaction.rollback();
		} finally {
			session.close();
			return description;
		}
	}

	public Organization addOrganization(String code, String name, String type) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Organization organization = null;

		try {
			organization = new Organization(code, name, type);
			transaction = session.beginTransaction();
			session.save(organization);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return organization;
		}
	}


	public DescriptionFormat addDescriptionFormat(String code, String name, List<Integer> characteristicIdList) {

		Session session = factory.openSession();
		Transaction transaction = null;
		DescriptionFormat newFormat = new DescriptionFormat(code, name);

		try {
			transaction = session.beginTransaction();

			// getting assertedName list of descriptionFormat
			List<Characteristic> characteristics;

			// if there is any characteristic belongs to descriptionFormat
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in " +
						"(:characteristicIdList)").setParameterList("characteristicIdList", characteristicIdList)
						.list();
				newFormat.setCharacteristics(new ArrayList<>(characteristics));
			}

			/*// getting assertedName list of descriptionFormat
			List<AssertedName> assertedNames;

			// if there is no any measurement belongs to characteristic
			if (assertedNameIdList != null && assertedNameIdList.size() != 0) {
				assertedNames = session.createQuery("from AssertedName m where m.id in (:assertedNameIdList)")
						.setParameterList("assertedNameIdList",
								assertedNameIdList).list();
				format.setAssertedNames(new ArrayList<>(assertedNames));
			}*/

			/*Integer newId = (Integer) session.save(format);
			result = (DescriptionFormat) session.get(DescriptionFormat.class, newId);*/
			session.save(newFormat);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return newFormat;
		}
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
				characteristic.setMeasurements(new ArrayList<>(measurements));
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
				kit.setCharacteristics(new ArrayList<>(characteristics));
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

	public AssertedName updateAssertedName(Integer id, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;
		AssertedName assertedName = null;

		try {
			transaction = session.beginTransaction();
			assertedName = (AssertedName) session.get(AssertedName.class, id);
			assertedName.setCode(code);
			assertedName.setName(name);
			session.save(assertedName);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return assertedName;
		}
	}

	public AssertedName updateAssertedName(Integer formatId, Integer id, String code, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;
		AssertedName assertedName = null;

		try {
			transaction = session.beginTransaction();
			DescriptionFormat descriptionFormat = (DescriptionFormat) session.get(DescriptionFormat.class, formatId);
			assertedName = descriptionFormat.getAssertedNames().stream().filter(p -> p.getId() == id)
					.findFirst().get();

			if (assertedName == null) {
				transaction.rollback();
				return assertedName;
			}

			assertedName.setCode(code);
			assertedName.setName(name);

			session.update(assertedName);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return assertedName;
		}
	}

	public CatalogDescription updateCatalogDescription(Integer id, String name, List<CharacteristicValue> values) {
		Session session = factory.openSession();
		Transaction transaction = null;
		CatalogDescription description = null;

		try {
			transaction = session.beginTransaction();
			description = (CatalogDescription) session.get(CatalogDescription.class, id);
			description.setName(name);
			description.setValues(values);
			session.save(description);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return description;
		}
	}

	public Organization updateOrganization(Integer id, String code, String name, String type) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Organization organization = null;

		try {
			transaction = session.beginTransaction();
			organization = (Organization) session.get(Organization.class, id);
			organization.setCode(code);
			organization.setName(name);
			organization.setType(type);
			session.save(organization);
			transaction.commit();
		} catch(Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
			return organization;
		}
	}

	public DescriptionFormat updateDescriptionFormat(Integer id, String code, String name, List<Integer>
			characteristicIdList) {
		Session session = factory.openSession();
		Transaction transaction = null;
		DescriptionFormat format = null;

		try {
			transaction = session.beginTransaction();
			format = (DescriptionFormat) session.get(DescriptionFormat.class, id);
			/*CharacteristicKit characteristicKit = descriptionFormat.getCharacteristicKits().stream().filter(p -> p
					.getId() == id).findFirst().get();

			// getting measurement list of characteristic
			List<Characteristic> characteristics;

			// if there is no any measurement belongs to characteristic
			if (characteristicIdList != null && characteristicIdList.size() != 0) {
				characteristics = session.createQuery("from Characteristic m where m.id in (:characteristicIdList)")
						.setParameterList("characteristicIdList",
								characteristicIdList).list();
				characteristicKit.setCharacteristics(new ArrayList<>(characteristics));
			} else {
				characteristicKit.setCharacteristics(null);
			}

			if (characteristicKit == null) {
				transaction.rollback();
				return false;
			}*/

			List<Characteristic> allCharacteristic = session.createQuery("from Characteristic").list();
			List<Characteristic> characteristics = new ArrayList<>();

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
			return format;
		} finally {
			session.close();
		}

		return format;
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

	public List<Organization> getAllOrganizations() {
		Session session = factory.openSession();
		List<Organization> result = null;

		try {
			result = session.createQuery("from Organization order by name").list();
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

	/*public List<Measurement> getMeasurementsByTypeId(Integer id) {
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
	}*/

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

	public List<Measurement> getMeasurementsByTypeId(Integer typeId) {
		Session session = factory.openSession();
		List<Measurement> result = null;

		try {
			MeasurementType type = (MeasurementType) session.get(MeasurementType.class, typeId);

			if (type != null) {
				/*throw new NullPointerException(String.format("MeasurementType {id = %d} is not found in data base",
						typeId));*/
				result = type.getMeasurements();
			}
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
			List<Measurement> measurements = new ArrayList<>();

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

	public boolean deleteDescriptionFormat(Integer id) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			DescriptionFormat format = (DescriptionFormat) session.get(DescriptionFormat.class, id);
			format.getAssertedNames().clear();
			format.getCharacteristics().clear();
			session.delete(format);
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

	public boolean deleteAssertedName(Integer id) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			AssertedName name = (AssertedName) session.get(AssertedName.class, id);
			session.delete(name);
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

	public boolean deleteCatalogDescription(Integer id) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			CatalogDescription description = (CatalogDescription) session.get(CatalogDescription.class, id);
			session.delete(description);
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

	public List<Characteristic> getCharacteristicsByAssertedNameId(Integer assertedNameId) {
		Session session = factory.openSession();
		List<Characteristic> result = null;

		try {
			AssertedName name = (AssertedName) session.get(AssertedName.class, assertedNameId);
			List<DescriptionFormat> formats = session.createQuery("from DescriptionFormat").list();

			for (DescriptionFormat format : formats) {
				if (format.getAssertedNames().contains(name)) {
					result = format.getCharacteristics();
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<CatalogDescription> getCatalogDescriptionsByAssertedName(Integer assertedNameId) {
		Session session = factory.openSession();
		List<CatalogDescription> result = null;

		try {
			AssertedName name = (AssertedName) session.get(AssertedName.class, assertedNameId);
			result = name.getCatalogDescriptions();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
	}

	public List<CharacteristicValue> getCharacteristicValuesByCatalogDescriptionId(Integer descriptionId) {
		Session session = factory.openSession();
		List<CharacteristicValue> result = new ArrayList<>();

		try {
			CatalogDescription description = (CatalogDescription) session.get(CatalogDescription.class, descriptionId);
			result = description.getValues();

			/*for (CharacteristicValue value : description.getValues()) {
				result.add(value.getCharacteristic());
			}*/
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			return result;
		}
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
			List<Characteristic> characteristics = new ArrayList<>();

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