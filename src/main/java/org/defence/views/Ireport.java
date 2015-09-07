package org.defence.views;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.defence.domain.entities.MeasurementType;
import org.defence.infrastructure.DbHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;


/**
 * Created by Denis on 04.09.2015.
 */
public class Ireport {
    public    String sourceFileName;
    public    HashMap parameters = new HashMap();
    private DbHelper dbHelper = DbHelper.getInstance();


    public void fill(String out_file_path) throws JRException {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            JasperDesign jasperDesign = JRXmlLoader.load(sourceFileName);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();

            Map parameters = new HashMap();
            List<MeasurementType> measurementList =  dbHelper.getAllMeasurementTypes();
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(measurementList);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            JRDocxExporter reportExporter = new JRDocxExporter();//JRRtfExporter(); does works fine
            reportExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            reportExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            reportExporter.exportReport();

            OutputStream outputStream = new FileOutputStream(out_file_path);
            out.writeTo(outputStream);
            out.close();

            outputStream.close();

            transaction.commit();
        } catch (JRException ejr) {

            ejr.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



