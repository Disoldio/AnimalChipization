package com.example.animalchipization.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CriteriaManager {
    @Autowired
    private EntityManager entityManager;
    public <T> CriteriaQuery<T> buildCriteria(Class<T> tClass, Map<String, Object> paramsMap){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> root = query.from(tClass);
        List<Predicate> list = new ArrayList<>();
        paramsMap.forEach((key, value) -> {
            if (value != null){
                Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(key)), "%" + value.toString().toUpperCase() + "%");
                list.add(predicate);
            }
        });
        Predicate globalPredicate = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        query.where(globalPredicate);
        query.orderBy(criteriaBuilder.asc(root.get("id")));

        return query;
    }
}
