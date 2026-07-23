package org.aula.dao;

import jakarta.persistence.EntityManager;
import org.aula.config.JpaConnection;
import org.aula.model.Barbeiro;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BarbeiroDao {

    private static final AtomicLong ID_SEQ =
            new AtomicLong(0L);

    public Barbeiro create(Barbeiro barbeiro){

        return JpaConnection.executeInTransaction(entityManager -> {

            entityManager.persist(barbeiro);

            return barbeiro;
        });
    }

    public Barbeiro findById(Long id){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager.find(Barbeiro.class, id);

        } finally {

            entityManager.close();
        }
    }

    public List<Barbeiro> findAll(){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager
                    .createQuery("from Barbeiro",
                            Barbeiro.class)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    public Barbeiro update(Barbeiro barbeiro){

        return JpaConnection.executeInTransaction(entityManager ->
                entityManager.merge(barbeiro));
    }

    public boolean deleteById(Long id){

        return JpaConnection.executeInTransaction(entityManager -> {

            Barbeiro barbeiro =
                    entityManager.find(Barbeiro.class, id);

            if(barbeiro != null){

                entityManager.remove(barbeiro);

                return true;
            }

            return false;
        });
    }

    public void deleteAll(){

        JpaConnection.executeInTransaction(entityManager -> {

            entityManager
                    .createQuery("delete from Barbeiro")
                    .executeUpdate();

            return null;
        });
    }

    public long nextId(){

        return ID_SEQ.incrementAndGet();
    }
}