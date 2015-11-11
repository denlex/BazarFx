package org.defence.tests;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by root on 11.11.15.
 */
public class XmlValidation {
	public static boolean validate(String xsdFile, String xmlFile) {
		try {
			// 1. Поиск и создание экземпляра фабрики для языка XML Schema
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

			// 2. Компиляция схемы
			// Схема загружается в объект типа java.io.File, но вы также можете использовать
			// классы java.net.URL и javax.xml.transform.Source
			File schemaLocation = new File(xsdFile);
			Schema schema = factory.newSchema(schemaLocation);

			// 3. Создание валидатора для схемы
			Validator validator = schema.newValidator();

			// 4. Разбор проверяемого документа
			Source source = new StreamSource(xmlFile);

			validator.validate(source);
		} catch (IOException ex) {
			System.out.println("IOException: " + ex.getMessage());
			return false;
		} catch (SAXException ex) {
			System.out.println("SAXException: " + ex.getMessage());
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		ClassLoader classLoader = new XmlValidation().getClass().getClassLoader();
		String xsdPath = classLoader.getResource("exchange_format/shiporder.xsd").getFile();
//		String xmlPath = classLoader.getResource("exchange_format/shiporder.xml").getFile();

//		String xsdPath = "/home/nd/test/shiporder.xsd";
		String xmlPath = "/home/nd/test/shiporder.xml";

		System.out.println(xsdPath);
//		System.out.println(validate(xsdPath, xmlPath));
	}
}
