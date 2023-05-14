package com.home.exercise.usermanagement.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.client.RestTemplate;

import com.home.exercise.usermanagement.model.Person;
import com.home.exercise.usermanagement.repository.PersonRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {
    
    @LocalServerPort
	int port;

    @Autowired
	private RestTemplateBuilder builder;

    @Autowired
    private PersonRepository repo;

    @Test
    void addPerson() {
        Person testPerson = new Person();
        testPerson.setBirthDate(LocalDate.now());
        testPerson.setFirstName("Test");
        testPerson.setLastName("Test");
        testPerson.setEmail("test@test.test");
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Person> builder = RequestEntity.post("/people").body(testPerson, Person.class);
        ResponseEntity<Person> result = template.exchange(builder, Person.class);
        Person addTestPerson = result.getBody();
        assertThat(addTestPerson).isNotNull();
        assertThat(addTestPerson.getId()).isNotZero();
    }

    @Test
    void listPeople() {
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        ResponseEntity<List<Person>> result = template.exchange(RequestEntity.get("/people").build(), new ParameterizedTypeReference<List<Person>>() {});
        List<Person> allPeople = result.getBody();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allPeople).isNotNull();
        if (allPeople != null)
            assertThat(allPeople.size()).isGreaterThanOrEqualTo(1); // The db must contain an Admin.
    }

    @Test
    void deletePerson() {
        List<Person> personList = repo.findAll();
        Person person = personList.get(0);
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Void> builder = RequestEntity.delete("/people/{id}", person.getId().toString()).build();
        ResponseEntity<Person> result = template.exchange(builder, Person.class);
        Person addTestPerson = result.getBody();
        assertThat(addTestPerson).isNotNull();
        assertThat(addTestPerson.getId()).isNotZero();
    }

    @Test
    void getPerson() {
        List<Person> personList = repo.findAll();
        Person person = personList.get(0);
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Void> builder = RequestEntity.get("/people/{id}", person.getId().toString()).build();
        ResponseEntity<Person> result = template.exchange(builder, Person.class);
        Person addTestPerson = result.getBody();
        assertThat(addTestPerson).isNotNull();
        assertThat(addTestPerson.getId()).isNotZero();
    }

    @Test
    void getPersonByName() {
        List<Person> personList = repo.findAll();
        Person person = personList.get(0);
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Void> builder = RequestEntity.get("/search?byLastName={lastname}", person.getLastName()).build();
        ResponseEntity<List<Person>> result = template.exchange(builder, new ParameterizedTypeReference<List<Person>>() {});
        List<Person> listPerson = result.getBody();
        assertThat(listPerson).isNotNull();
        assertThat(listPerson.size()).isNotZero();
    }

    @Test
    void revokePermission() {
        List<Person> personList = repo.findAll();
        Person person = personList.get(0);
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Void> builder = RequestEntity.post("/people/{personid}/revoke/{permissionid}", person.getId().toString(), "2").build();
        ResponseEntity<Person> result = template.exchange(builder, Person.class);
        Person listPerson = result.getBody();
        assertThat(listPerson).isNotNull();
    }

    @Test
    void grantPermission() {
        List<Person> personList = repo.findAll();
        Person person = personList.get(0);
        RestTemplate template = builder.rootUri("http://localhost:" + port).build();
        RequestEntity<Void> builder = RequestEntity.post("/people/{personid}/grant/{permissionid}", person.getId().toString(), "3").build();
        ResponseEntity<Person> result = template.exchange(builder, Person.class);
        Person listPerson = result.getBody();
        assertThat(listPerson).isNotNull();
    }
}
