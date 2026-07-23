package org.aula.dao;

import jakarta.persistence.EntityManager;
import org.aula.config.JpaConnection;
import org.aula.model.Agendamento;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AgendamentoDao {

    private static final AtomicLong ID_SEQ =
            new AtomicLong(0L);

    public Agendamento create(Agendamento agendamento){

        return JpaConnection.executeInTransaction(entityManager -> {

            entityManager.persist(agendamento);

            return agendamento;
        });
    }

    public Agendamento findById(Long id){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager.find(
                    Agendamento.class,
                    id
            );

        } finally {

            entityManager.close();
        }
    }

    public List<Agendamento> findAll(){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager
                    .createQuery(
                            "from Agendamento",
                            Agendamento.class
                    )
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    public Agendamento update(Agendamento agendamento){

        return JpaConnection.executeInTransaction(entityManager ->
                entityManager.merge(agendamento));
    }

    public boolean deleteById(Long id){

        return JpaConnection.executeInTransaction(entityManager -> {

            Agendamento agendamento =
                    entityManager.find(
                            Agendamento.class,
                            id
                    );

            if(agendamento != null){

                entityManager.remove(agendamento);

                return true;
            }

            return false;
        });
    }

    public void deleteAll(){

        JpaConnection.executeInTransaction(entityManager -> {

            entityManager
                    .createQuery("delete from Agendamento")
                    .executeUpdate();

            return null;
        });
    }

    public boolean existeAgendamento(
            Long barbeiroId,
            LocalDate data,
            LocalTime hora
    ){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            Long quantidade = entityManager
                    .createQuery("""
                            select count(a)
                            from Agendamento a
                            where a.barbeiro.id = :barbeiroId
                            and a.data = :data
                            and a.hora = :hora
                            """, Long.class)
                    .setParameter(
                            "barbeiroId",
                            barbeiroId
                    )
                    .setParameter(
                            "data",
                            data
                    )
                    .setParameter(
                            "hora",
                            hora
                    )
                    .getSingleResult();

            return quantidade > 0;

        } finally {

            entityManager.close();
        }
    }

    public long nextId(){

        return ID_SEQ.incrementAndGet();
    }
}