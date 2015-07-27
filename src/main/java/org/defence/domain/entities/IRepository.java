package org.defence.domain.entities;

import java.util.List;

/**
 * Created by root on 22.07.15.
 */
public interface IRepository<IEntity> {
    void insert(IEntity entity);
    void update(IEntity entity);
    void remove(IEntity entity);
    IEntity getById(int id);
    List<IEntity> getAll();
}
