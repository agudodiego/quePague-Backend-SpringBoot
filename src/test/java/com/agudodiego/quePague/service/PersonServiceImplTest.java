package com.agudodiego.quePague.service;

import com.agudodiego.quePague.config.JwtService;
import com.agudodiego.quePague.exceptions.ErrorProcessException;
import com.agudodiego.quePague.model.entity.Payment;
import com.agudodiego.quePague.model.entity.Person;
import com.agudodiego.quePague.model.entity.Role;
import com.agudodiego.quePague.model.request.RegisterPersonRequest;
import com.agudodiego.quePague.model.response.PersonResponse;
import com.agudodiego.quePague.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PersonServiceImplTest {

//    *********** OBJETOS NECESARIOS PARA LA CLASE A TESTEAR ************
    @Mock
    private PersonRepository personRepositoryMock = Mockito.mock(PersonRepository.class);
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //    *********** INSTANCIA DE LA CLASE A TESTEAR (con sus inyecciones) ************
    @Autowired
    private PersonServiceImpl personServiceImpl = new PersonServiceImpl(personRepositoryMock, passwordEncoder, jwtService, authenticationManager);

    //    *********** OBJETOS GLOBALES A USAR DURANTE LOS TESTS ************
    RegisterPersonRequest registerPersonRequest = new RegisterPersonRequest("PepeSanchez", "PepeSanchez88", "pepe@mail.com");
    PersonResponse personResponse01 = new PersonResponse("PepeSanchez", new HashSet<>());
    Person person01 = new Person("PepeSanchez", "PepeSanchez88", "pepe@mail.com", null, Role.USER);
    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("[Person Service] - Registro de persona")
    void registerSuccess() {
        Mockito.when(personServiceImpl.register(registerPersonRequest)).thenReturn("Person added to the database");

        String respuesta = personServiceImpl.register(registerPersonRequest);

        Assertions.assertEquals("Person added to the database", respuesta);
    }

    @Test
    @DisplayName("[Person Service] - Registro fallido de persona")
    void registerFail() {

        // Simular que personExists(existingUsername) devuelve true
        Mockito.when(personServiceImpl.personExists("PepeSanchez")).thenReturn(true);

        // Asegurarse de que al llamar a personService.register(request), se arroje una DataIntegrityViolationException
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            personServiceImpl.register(registerPersonRequest);
        });

        // Verificar que personRepository.save() nunca se llamó, ya que la excepción se arroja antes de llegar allí
        Mockito.verify(personRepositoryMock, Mockito.never()).save(Mockito.any());

        // Verificar que la excepcion es arrojada por el personServiceImpl.register()
        assertThatThrownBy(() -> personServiceImpl.register(registerPersonRequest))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("[Person Service] - Recuperar una persona")
    void getOneByUsernameTest() throws ErrorProcessException {
        Mockito.when(personRepositoryMock.existsByUsername("PepeSanchez")).thenReturn(true);

        Mockito.when(personRepositoryMock.findByUsername("PepeSanchez")).thenReturn(Optional.of(person01));

        PersonResponse respuesta = personServiceImpl.getOneByUsername("PepeSanchez");

        Assertions.assertEquals("PepeSanchez", respuesta.getUsername());
    }


    @Test
    @DisplayName("[Person Service] - Constata que una persona exista por username")
    void personExistsTrue() {
        Mockito.when(personServiceImpl.personExists("diego")).thenReturn(true);

        Boolean respuesta = personServiceImpl.personExists("diego");

        Assertions.assertEquals(true, respuesta);
    }

    @Test
    @DisplayName("[Person Service] - Constata que una persona NO exista por username")
    void personExistsFalse() {
        Boolean respuesta = personServiceImpl.personExists("monica");

        Assertions.assertEquals(false, respuesta);
    }
}