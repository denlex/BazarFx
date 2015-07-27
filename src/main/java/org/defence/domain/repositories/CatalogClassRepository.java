package org.defence.domain.repositories;

import org.defence.domain.entities.CatalogClass;
import org.defence.domain.entities.CatalogGroup;
import org.defence.domain.entities.IRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by root on 23.07.15.
 */
public class CatalogClassRepository implements IRepository<CatalogClass> {
    private Session _session;

    public CatalogClassRepository(Session session)
    {
        _session = session;
    }

    public void insert(CatalogClass entity)
    {
        _session.save(entity);
    }

    public void update(CatalogClass entity)
    {
        _session.update(entity);
    }

    public void remove(CatalogClass entity)
    {
        _session.delete(entity);
    }

    public CatalogClass getById(int id) {
        return null;
    }

    public List<CatalogClass> getAll() {
        return null;
    }

    public List<CatalogClass> GetCatalogClassesByGroup(CatalogGroup group)
    {
        Query query = _session.createSQLQuery("select * from CatalogClass where CatalogGroup.Number = :number");

//        return new List<CatalogClass>(query.list()<CatalogClass>());
        return null;
    }

    public List<CatalogClass> GetAll()
    {
        Query query = _session.createSQLQuery("select distinct * from CatalogClass").addEntity(CatalogClass.class);

//        return new List<CatalogClass>(query.list<CatalogClass>());
        return null;
    }
}

