package de.tum.in.ase.eist;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.repository.PersonRepository;
import de.tum.in.ase.eist.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;

    @Test
    void testAddPerson() {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        personService.save(person);

        assertEquals(1, personRepository.findAll().size());
    }

    @Test
    void testDeletePerson() {
        var person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setBirthday(LocalDate.now());

        person = personRepository.save(person);

        personService.delete(person);

        assertTrue(personRepository.findAll().isEmpty());
    }

    @Test
    void testAddParent() {
        var child = new Person();
        var parent = new Person();
        child.setFirstName("Max");
        child.setLastName("Mustermann");
        child.setBirthday(LocalDate.now());

        parent.setFirstName("Linda");
        parent.setLastName("Zuckermann");
        parent.setBirthday(LocalDate.of(1969, 5, 11));

        personRepository.save(child);
        personRepository.save(parent);

        personService.addParent(child, parent);

        assertTrue(personRepository.existsById(parent.getId()));
        assertTrue(personRepository.existsById(child.getId()));
        assertTrue(child.getParents().contains(parent));
    }

    @Test
    void testAddThreeParents() {
        var child = new Person();
        var parent1 = new Person();
        var parent2=new Person();
        var parent3= new Person();

        child.setFirstName("Leon");
        child.setLastName("Mustermann");
        child.setBirthday(LocalDate.now());

        parent1.setFirstName("Max");
        parent1.setLastName("Mustermann");
        parent1.setBirthday(LocalDate.of(1970,8,25));

        parent2.setFirstName("Max");
        parent2.setLastName("Mustermann");
        parent2.setBirthday(LocalDate.of(1945,5,6));

        parent3.setFirstName("Max");
        parent3.setLastName("Mustermann");
        parent3.setBirthday(LocalDate.now());
    }
}
