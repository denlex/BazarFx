package org.defence.tests;

import org.defence.domain.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 06.08.15.
 */
public class HibernateTest {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        Characteristic maxSpeed = new Characteristic("01", "Макс. скорость полета");
        Characteristic maxHeight = new Characteristic("02", "Макс. высота полета");
        Characteristic maxDistance = new Characteristic("03", "Макс. дальность полета");
        Characteristic moatWidth = new Characteristic("04", "Ширана преодолеваемого рва");
        Characteristic speedOnRoad = new Characteristic("05", "Скорость по трассе");

        Set<Characteristic> migCharacteristics = new HashSet<Characteristic>();
        migCharacteristics.add(maxDistance);
        migCharacteristics.add(maxHeight);
        migCharacteristics.add(maxSpeed);

        CharacteristicKit destroyersOfVVS = new CharacteristicKit("Истребители ВВС", migCharacteristics);

        Set<CharacteristicKit> characteristicKits = new HashSet<CharacteristicKit>();
        characteristicKits.add(destroyersOfVVS);

        Set<CharacteristicValue> migCharacteristicValues = new HashSet<CharacteristicValue>();
        migCharacteristicValues.add(new CharacteristicValue(maxDistance, "1 430"));
        migCharacteristicValues.add(new CharacteristicValue(maxHeight, "18 000"));
        migCharacteristicValues.add(new CharacteristicValue(maxSpeed, "2 450"));

        CatalogDescription mig29 = new CatalogDescription("Миг-29", migCharacteristicValues);


        Set<CatalogDescription> catalogDescriptions = new HashSet<CatalogDescription>();
        catalogDescriptions.add(mig29);

		AssertedName assertedName = new AssertedName("code", "Миги");
		assertedName.setCatalogDescriptions(catalogDescriptions);

		Set<AssertedName> assertedNames = new HashSet<>();
		assertedNames.add(assertedName);

        DescriptionFormat destroyer = new DescriptionFormat("001", "Истребители");
//        destroyer.setCharacteristics(characteristicKits);
        destroyer.setAssertedNames(assertedNames);

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(destroyer);
        transaction.commit();
        session.close();
    }
}
