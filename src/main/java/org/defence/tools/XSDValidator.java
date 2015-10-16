package org.defence.tools;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by root on 10/13/15.
 */
public class XSDValidator {
	public static void main(String[] args) {
		XSDValidator ob = new XSDValidator();

		ClassLoader classLoader = ob.getClass().getClassLoader();
		String xsdPath = classLoader.getResource("exchange_format/catalog_description.xsd").getFile();
		String xmlPath = classLoader.getResource("exchange_format/catalog_description.xml").getFile();

		if (validateXMLSchema(xsdPath, xmlPath)) {
			System.out.println("XML is valid");
		} else {
			System.out.println("XML is NOT valid");
		}
	}

	public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
		try {
			SchemaFactory factory =
					SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		} catch (SAXException e1) {
			System.out.println("SAX Exception: " + e1.getMessage());
			return false;
		}
		return true;
	}
}
