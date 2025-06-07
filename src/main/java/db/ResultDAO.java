package db;

import entity.Result;
import jakarta.enterprise.context.SessionScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@SessionScoped
public class ResultDAO implements Serializable {

    @PersistenceContext(unitName = "PU")
    private EntityManager entityManager;

    public List<Result> loadAllResults() {
        return entityManager.createQuery("SELECT r FROM Result r", Result.class)
                .getResultList();
    }

    @Transactional
    public void save(Result entity) {
        entityManager.persist(entity);
    }
}