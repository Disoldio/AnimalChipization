package com.example.animalchipization.util;

import com.example.animalchipization.domain.Animal;
import com.example.animalchipization.domain.Gender;
import com.example.animalchipization.domain.LifeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
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
        paramsMap.forEach((key, value) -> {     //SELECT * FROM animal WHERE name.toUpperCase() LIKE %кот%
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

    public CriteriaQuery<Animal> buildAnimalCriteria(Integer chipperId, Long chippingLocationId, Gender gender, LifeStatus lifeStatus, LocalDateTime start, LocalDateTime end){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Animal> query = criteriaBuilder.createQuery(Animal.class);
        Root<Animal> root = query.from(Animal.class);
        List<Predicate> list = new ArrayList<>();
        if(chipperId != null){
            Predicate predicate = criteriaBuilder.equal(root.get("chipper"), chipperId);
            list.add(predicate);
        }
        if(chippingLocationId != null){
            Predicate predicate = criteriaBuilder.equal(root.get("chippingLocation"), chippingLocationId);
            list.add(predicate);
        }
        if(gender != null){
            Predicate predicate = criteriaBuilder.equal(root.get("gender"), gender);
            list.add(predicate);
        }
        if(lifeStatus != null){
            Predicate predicate = criteriaBuilder.equal(root.get("lifeStatus"), lifeStatus);
            list.add(predicate);
        }
        if(start != null){
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("chippingDateTime"), start);
            list.add(predicate);
        }
        if(end != null){
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("chippingDateTime"), end);
            list.add(predicate);
        }
        Predicate globalPredicate = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        query.where(globalPredicate);
        query.orderBy(criteriaBuilder.asc(root.get("id")));

        return query;
    }
}
