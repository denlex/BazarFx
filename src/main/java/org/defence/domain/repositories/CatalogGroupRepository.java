package org.defence.domain.repositories;

import org.defence.domain.entities.CatalogGroup;
import org.defence.domain.entities.IRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by root on 23.07.15.
 */
public class CatalogGroupRepository implements IRepository<CatalogGroup> {
    private Session _session;

    public CatalogGroupRepository(Session session)
    {
        _session = session;
    }

    public void insert(CatalogGroup entity)
    {
        _session.save(entity);
    }

    public void update(CatalogGroup entity)
    {
        _session.update(entity);
    }

    public void remove(CatalogGroup entity)
    {
        _session.delete(entity);
    }

    public CatalogGroup getById(int id)
    {
//        throw new System.NotImplementedException();
        return null;
    }

    public List<CatalogGroup> getAll()
    {
        Query query = _session.createSQLQuery("select distinct * from CatalogGroup").addEntity(CatalogGroup.class);

//        return new List<CatalogGroup>(query.list<CatalogGroup>());
        return null;
    }
}
