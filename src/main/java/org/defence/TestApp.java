package org.defence;

import org.defence.domain.entities.AssertedName;
import org.defence.infrastructure.UnitOfWork;

/**
 * Created by root on 23.07.15.
 */
public class TestApp {
    public static void main(String[] args) {
        UnitOfWork unitOfWork = new UnitOfWork(true);

        AssertedName assertedName = new AssertedName();
        assertedName.setName("Утвержденное наименование");
        assertedName.setNumber("123456");

        unitOfWork.assertedNameRepository.insert(assertedName);
        unitOfWork.save();
    }
}
