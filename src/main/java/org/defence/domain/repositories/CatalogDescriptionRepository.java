package org.defence.domain.repositories;

import org.defence.domain.entities.CatalogDescription;
import org.defence.domain.entities.IRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by root on 23.07.15.
 */
public class CatalogDescriptionRepository implements IRepository<CatalogDescription> {
    private Session _session;

    public CatalogDescriptionRepository(Session session)
    {
        _session = session;
    }

    public void insert(CatalogDescription entity)
    {
        _session.save(entity);
    }

    public void update(CatalogDescription entity)
    {
        _session.update(entity);
    }

    public void remove(CatalogDescription entity)
    {
        _session.delete(entity);
    }

    public CatalogDescription getById(int id)
    {
        return null;
    }

    public List<CatalogDescription> getAll()
    {
        Query query = _session.createSQLQuery("select distinct * from CatalogDescription").addEntity(CatalogDescription.class);

//        return new List<CatalogDescription>(query.list<CatalogDescription>());
        return null;
    }
}
