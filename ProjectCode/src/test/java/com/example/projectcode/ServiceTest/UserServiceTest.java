package com.example.projectcode.ServiceTest;

import com.example.projectcode.Models.UserApp;
import com.example.projectcode.Repositories.UserRepository;
import com.example.projectcode.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser() {
        UserApp user = new UserApp("test@example.com", "password123", "John", "Doe", "123 Main St", "toto");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        UserApp createdUser = userService.registerUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        UserApp user = new UserApp("test@example.com", "password123", "John", "Doe", "123 Main St", "toto");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserApp> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testGetAllUsers() {
        List<UserApp> users = List.of(new UserApp("test@example.com", "password123", "John", "Doe", "123 Main St", "toto"));
        when(userRepository.findAll()).thenReturn(users);

        List<UserApp> foundUsers = userService.getAllUsers();
        assertEquals(1, foundUsers.size());
    }
}
