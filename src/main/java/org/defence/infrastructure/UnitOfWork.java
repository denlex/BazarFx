package org.defence.infrastructure;

import org.defence.domain.entities.*;
import org.defence.domain.repositories.AssertedNameRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by root on 22.07.15.
 */
public class UnitOfWork {

    private Session currentSession;

    private static boolean isNewDataBase;

    public IRepository<CatalogGroup> catalogGroupRepository;

    public IRepository<CatalogClass> catalogClassRepository;

    public IRepository<CatalogDescription> catalogDescriptionRepository;
    public IRepository<AssertedName> assertedNameRepository;

    public static class HibernateUtil {
        private static final SessionFactory sessionFactory;

        static {
            try {
                //creates the session factory from hibernate.cfg.xml
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (ExceptionInInitializerError ex) {
                System.err.println("Initial SessionFactory creation failed: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }
    }

    public UnitOfWork(boolean createNewDataBase) {
        isNewDataBase = createNewDataBase;
        currentSession = HibernateUtil.getSessionFactory().openSession();
//        catalogGroupRepository = new CatalogGroupRepository(currentSession);
//            catalogClassRepository = new CatalogClassRepository(currentSession);
//        catalogDescriptionRepository = new CatalogDescriptionRepository(currentSession);
        assertedNameRepository = new AssertedNameRepository(currentSession);
    }

    public void save() {
        currentSession.beginTransaction();
        currentSession.getTransaction().commit();
    }

    public void dispose() {
    }
}
