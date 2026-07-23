package org.aula.dao;

import jakarta.persistence.EntityManager;
import org.aula.config.JpaConnection;
import org.aula.model.Servico;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ServicoDao {

    private static final AtomicLong ID_SEQ =
            new AtomicLong(0L);

    public Servico create(Servico servico){

        return JpaConnection.executeInTransaction(entityManager -> {

            entityManager.persist(servico);

            return servico;
        });
    }

    public Servico findById(Long id){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager.find(Servico.class, id);

        } finally {

            entityManager.close();
        }
    }

    public List<Servico> findAll(){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager
                    .createQuery("from Servico",
                            Servico.class)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    public Servico update(Servico servico){

        return JpaConnection.executeInTransaction(entityManager ->
                entityManager.merge(servico));
    }

    public boolean deleteById(Long id){

        return JpaConnection.executeInTransaction(entityManager -> {

            Servico servico =
                    entityManager.find(Servico.class, id);

            if(servico != null){

                entityManager.remove(servico);

                return true;
            }

            return false;
        });
    }

    public void deleteAll(){

        JpaConnection.executeInTransaction(entityManager -> {

            entityManager
                    .createQuery("delete from Servico")
                    .executeUpdate();

            return null;
        });
    }

    public long nextId(){

        return ID_SEQ.incrementAndGet();
    }
}