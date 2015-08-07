package org.defence.domain.entities.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 8/7/15.
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

        Certificate javaCertificate = new Certificate();
        javaCertificate.setName("Java SE 7 Programmer");

        Certificate karateCertificate = new Certificate();
        karateCertificate.setName("International Karatist");

        Certificate engineerCertificate = new Certificate();
        engineerCertificate.setName("Diploma of Engineer");

        Set<Certificate> denisCertificates = new HashSet<Certificate>();
        denisCertificates.add(javaCertificate);
        denisCertificates.add(karateCertificate);
        denisCertificates.add(engineerCertificate);

        Certificate pythonCertificate = new Certificate();
        pythonCertificate.setName("Senior Python Developer");

        Set<Certificate> mihaCertificates = new HashSet<Certificate>();
        mihaCertificates.add(pythonCertificate);
        mihaCertificates.add(engineerCertificate);

        Employee denis = new Employee();
        denis.setFio("Зарубин Денис Михайлович");
        denis.setCertificateSet(denisCertificates);

        Employee miha = new Employee();
        miha.setFio("Маланюк Михаил Викторович");
        miha.setCertificateSet(mihaCertificates);

        Set<Employee> employeesGrantedWithJavaCertificate = new HashSet<Employee>();
        employeesGrantedWithJavaCertificate.add(denis);

        Set<Employee> employeesGrantedWithKarateCertificate = new HashSet<Employee>();
        employeesGrantedWithKarateCertificate.add(denis);

        Set<Employee> employeesGrantedWithPythonCertificate = new HashSet<Employee>();
        employeesGrantedWithPythonCertificate.add(miha);

        Set<Employee> employeesGrantedWithEngineerCertificate = new HashSet<Employee>();
        employeesGrantedWithEngineerCertificate.add(denis);
        employeesGrantedWithEngineerCertificate.add(miha);

        javaCertificate.setEmployeeSet(employeesGrantedWithJavaCertificate);
        karateCertificate.setEmployeeSet(employeesGrantedWithKarateCertificate);
        pythonCertificate.setEmployeeSet(employeesGrantedWithPythonCertificate);
        engineerCertificate.setEmployeeSet(employeesGrantedWithEngineerCertificate);

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(denis);
        session.save(miha);
        session.save(javaCertificate);
        session.save(karateCertificate);
        session.save(pythonCertificate);
        session.save(engineerCertificate);

        transaction.commit();

        transaction = session.beginTransaction();
        List<Certificate> certificates = session.createQuery("from Certificate ").list();
        for (Certificate certificate : certificates) {
            System.out.format("Сертификат \"%s\". Правообладатели:\n", certificate.getName());

            for (Employee employee : certificate.getEmployeeSet()) {
                System.out.println(employee.getFio());
            }
            System.out.println();
        }
        transaction.commit();
    }
}
