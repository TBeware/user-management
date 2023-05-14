package com.home.exercise.usermanagement.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

public class PersonTest {
    
    @Test
	void testSerialization() {
		Person person = new Person();
		person.setFirstName("TestPerson");
		person.setLastName("TestPerson");
        person.setBirthDate(LocalDate.now());
        person.setEmail("test@test.test");
		person.setId(123);
        Person other = (Person) SerializationUtils.deserialize(SerializationUtils.serialize(person));
		assertThat(other.getFirstName()).isEqualTo(person.getFirstName());
		assertThat(other.getLastName()).isEqualTo(person.getLastName());
		assertThat(other.getId()).isEqualTo(person.getId());
	}
    
}
