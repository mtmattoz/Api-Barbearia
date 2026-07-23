package org.aula.dao;

import jakarta.persistence.EntityManager;
import org.aula.config.JpaConnection;
import org.aula.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UsuarioDao {

    private static final AtomicLong ID_SEQ =
            new AtomicLong(0L);

    public Usuario create(Usuario usuario){

        return JpaConnection.executeInTransaction(entityManager -> {

            entityManager.persist(usuario);

            return usuario;
        });
    }

    public Usuario findById(Long id){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager.find(Usuario.class, id);

        } finally {

            entityManager.close();
        }
    }

    public List<Usuario> findAll(){

        EntityManager entityManager =
                JpaConnection.getEntityManager();

        try {

            return entityManager
                    .createQuery("from Usuario",
                            Usuario.class)
                    .getResultList();

        } finally {

            entityManager.close();
        }
    }

    public Usuario update(Usuario usuario){

        return JpaConnection.executeInTransaction(entityManager ->
                entityManager.merge(usuario));
    }

    public boolean deleteById(Long id){

        return JpaConnection.executeInTransaction(entityManager -> {

            Usuario usuario =
                    entityManager.find(Usuario.class, id);

            if(usuario != null){

                entityManager.remove(usuario);

                return true;
            }

            return false;
        });
    }

    public void deleteAll(){

        JpaConnection.executeInTransaction(entityManager -> {

            entityManager
                    .createQuery("delete from Usuario")
                    .executeUpdate();

            return null;
        });
    }

    public long nextId(){

        return ID_SEQ.incrementAndGet();
    }
}