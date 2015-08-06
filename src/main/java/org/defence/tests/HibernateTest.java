package org.defence.tests;

import org.defence.domain.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

        DescriptionFormat descriptionFormat = new DescriptionFormat();
        descriptionFormat.setName("Самолеты");
        descriptionFormat.setCode("1001");

        CharacteristicType heightCharacteristicType = new CharacteristicType();
        heightCharacteristicType.setName("Высота");
        heightCharacteristicType.setCode("1");

        MeasurementType heightMeasurementType = new MeasurementType();
        heightCharacteristicType.setCode("0101");
        heightCharacteristicType.setName("Высоты");

        Measurement heightMeasurement = new Measurement();
        heightMeasurement.setCode("0101.01");
        heightMeasurement.setName("метры");
        heightMeasurement.setShortName("м");
        heightMeasurement.setType(heightMeasurementType);
        heightMeasurement.setCharacteristicSet(null);

        Characteristic maxHeight = new Characteristic();
        maxHeight.setName("Максимальная высота полета");
        maxHeight.setCode("001");
        maxHeight.setMeasurement(heightMeasurement);
        maxHeight.setCharacteristicKitSet(null);

        CharacteristicKit migCharacteristicKit = new CharacteristicKit();
        migCharacteristicKit.setName("Набор характеристик для самолета Миг-29");
        migCharacteristicKit.setCharacteristicSet(null);

    }
}
