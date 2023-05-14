package com.home.exercise.usermanagement.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.home.exercise.usermanagement.model.GrantedPermissionKey;
import com.home.exercise.usermanagement.model.GrantedPermissions;
import com.home.exercise.usermanagement.model.Person;
import com.home.exercise.usermanagement.repository.GrantedPermissionRepository;
import com.home.exercise.usermanagement.repository.PermissionRepository;
import com.home.exercise.usermanagement.repository.PersonRepository;
import com.home.exercise.usermanagement.repository.PersonSearchBySpec;

import jakarta.validation.Valid;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepo;

    @Autowired
    GrantedPermissionRepository grantedRepo;

    @Autowired
    PermissionRepository permissionRepo;
    
    @GetMapping("/people")
    public List<Person> listUsers() {
        return personRepo.findAll();
    }

    @GetMapping("/people/{id}")
    public Person getUser(@PathVariable("id") Integer personID) {
        return personRepo.findById(personID).get();
    }

    @GetMapping("/search")
    public List<Person> getUser(@RequestParam("byLastName") String lastName) {
        Specification<Person> spec = PersonSearchBySpec.hasLastName(lastName);
        return personRepo.findAll(spec);
    }

    @PostMapping("/people")
    public Person newPerson(@Valid @RequestBody Person person) {
        return personRepo.save(person);
    }

    @PostMapping("/people/{personid}/grant/{permissionid}")
    public Person grantPermission(@PathVariable("personid") Integer personID, @PathVariable("permissionid") Integer permissionID) {
        GrantedPermissions newPermission = new GrantedPermissions();
        newPermission.setGrantedDate(LocalDate.now());
        GrantedPermissionKey key = new GrantedPermissionKey();
        key.setPersonID(personID);
        key.setPermissionID(permissionID);
        newPermission.setId(key);
        newPermission.setPermission(permissionRepo.findById(permissionID).get());
        newPermission.setPerson(personRepo.findById(personID).get());
        grantedRepo.save(newPermission);

        return personRepo.findById(personID).get();
    }

    @PostMapping("/people/{personid}/revoke/{permissionid}")
    public Person revokePermission(@PathVariable("personid") Integer personID, @PathVariable("permissionid") Integer permissionID) {
        GrantedPermissionKey key = new GrantedPermissionKey();
        key.setPermissionID(permissionID);
        key.setPersonID(personID);
        Optional<GrantedPermissions> grantedPerm = grantedRepo.findById(key);
        if (grantedPerm.isEmpty() == false)
            grantedRepo.deleteById(key);
        return personRepo.findById(personID).get();
    }

    @DeleteMapping("/people/{id}")
    public Person removePerson(@PathVariable("id") Integer personID) {
        Person person = personRepo.findById(personID).get();
        if (person != null) { 
            // We found a person.
            personRepo.delete(person);
        }
        return person;
    }
}
