package com.home.exercise.usermanagement.repository;

import org.springframework.data.jpa.domain.Specification;

import com.home.exercise.usermanagement.model.GrantedPermissions;
import com.home.exercise.usermanagement.model.Person;

import jakarta.persistence.criteria.Join;

public class PersonSearchBySpec {

    public static Specification<Person> hasLastName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.<String>get("lastName"), name);
    }
}
