package ru.kradin.blog.services.implementations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.RegistrationService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class RegistrationServiceImplTest {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void registrationServiceTest() throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("User");
        userRegistrationDTO.setEmail("some@mail.ru");
        userRegistrationDTO.setPassword("password");

        registrationService.register(userRegistrationDTO);

        User user = userRepository.findByUsername("User").orElseThrow(() -> new UsernameNotFoundException("User"));

        assertEquals(user.getEmail(),userRegistrationDTO.getEmail());
        assertNotEquals(user.getPassword(),userRegistrationDTO.getPassword());
    }
}