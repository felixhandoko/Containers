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

import static org.junit.jupiter.api.Assertions.*;

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

        child = personRepository.save(child);
        parent = personRepository.save(parent);

        assertTrue(personRepository.existsById(parent.getId()));
        assertTrue(personRepository.existsById(child.getId()));

        personService.addParent(child, parent);


        child = personRepository.findById(child.getId()).orElse(null);
        parent = personRepository.findById(parent.getId()).orElse(null);
        assertNotNull(child);
        assertNotNull(parent);
        assertTrue(child.getParents().contains(parent));
        assertTrue(parent.getChildren().contains(child));
    }

    @Test
    void testAddThreeParents() {
        var child = new Person();
        var parent1 = new Person();
        var parent2 = new Person();
        var parent3 = new Person();

        child.setFirstName("Leon");
        child.setLastName("Mustermann");
        child.setBirthday(LocalDate.now());

        parent1.setFirstName("Max");
        parent1.setLastName("Mustermann");
        parent1.setBirthday(LocalDate.of(1970, 8, 25));

        parent2.setFirstName("Maximillian");
        parent2.setLastName("Schmidt");
        parent2.setBirthday(LocalDate.of(1945, 5, 6));

        parent3.setFirstName("Ellis");
        parent3.setLastName("Zuckermann");
        parent3.setBirthday(LocalDate.of(1986, 4, 12));

        personRepository.save(child);
        personRepository.save(parent1);
        personRepository.save(parent2);
        personRepository.save(parent3);


    }
}
