package org.defence.domain.repositories;

import org.defence.domain.entities.AssertedName;
import org.defence.domain.entities.IRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by root on 23.07.15.
 */
public class AssertedNameRepository implements IRepository<AssertedName> {
    private Session _session;

    public AssertedNameRepository(Session session)
    {
        _session = session;
    }

    public void insert(AssertedName entity)
    {
        _session.save(entity);
    }

    public void update(AssertedName entity)
    {
        _session.update(entity);
    }

    public void remove(AssertedName entity)
    {
        _session.delete(entity);
    }

    public AssertedName getById(int id)
    {
        return null;
    }

    public List<AssertedName> getAll()
    {
        Query query = _session.createSQLQuery("select distinct * from AssertedName").addEntity(AssertedName.class);

//        return new List<AssertedName>(query.list<AssertedName>());
        return null;
    }
}
