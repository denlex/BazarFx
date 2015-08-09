package org.defence.tests;

import org.defence.domain.entities.*;
import org.hibernate.SessionFactory;
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

        Characteristic maxSpeed = new Characteristic();
        maxSpeed.setCode("01");
        maxSpeed.setName("Макс. скорость полета");

        Characteristic maxHeight = new Characteristic();
        maxHeight.setCode("02");
        maxHeight.setName("Макс. высота полета");

        Characteristic maxDistance = new Characteristic();
        maxDistance.setCode("03");
        maxDistance.setName("Макс. дальность полета");

        Characteristic moatWidth = new Characteristic();
        moatWidth.setCode("04");
        moatWidth.setName("Ширана преодолеваемого рва");

        Characteristic speedOnRoad = new Characteristic();
        speedOnRoad.setCode("05");
        speedOnRoad.setName("Скорость по трассе");

        Set<Characteristic> migCharacteristics = new HashSet<Characteristic>();
        migCharacteristics.add(maxDistance);
        migCharacteristics.add(maxHeight);
        migCharacteristics.add(maxSpeed);

        CharacteristicKit destroyersOfVVS = new CharacteristicKit();
        destroyersOfVVS.setName("Истребители ВВС");
        destroyersOfVVS.setCharacteristics(migCharacteristics);

        Set<CharacteristicKit> characteristicKits = new HashSet<CharacteristicKit>();
        characteristicKits.add(destroyersOfVVS);

        CatalogDescription mig29 = new CatalogDescription();
        mig29.setName("Миг-29");

        Set<CatalogDescription> catalogDescriptions = new HashSet<CatalogDescription>();
        catalogDescriptions.add(mig29);

        DescriptionFormat destroyer = new DescriptionFormat();
        destroyer.setName("Истребители");
        destroyer.setCode("001");
        destroyer.setCharacteristicKits(characteristicKits);
        destroyer.setCatalogDescriptions(catalogDescriptions);

    }
}
