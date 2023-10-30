package com.example.animalchipization.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Component
public class EntityRepository {
    @Autowired
    private EntityManager entityManager;

    public <T> List<T> list(CriteriaQuery<T> criteria, Integer from, Integer size){
        TypedQuery<T> query = entityManager.createQuery(criteria);
        query.setFirstResult(from);
        query.setMaxResults(size);

        return query.getResultList();
    }

}
