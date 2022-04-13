package com.neko233.forward.pojo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author SolarisNeko
 * Date on 2022-04-13
 */
@ExtendWith(MockitoExtension.class)
public class UserEmailTest {

    @InjectMocks
    UserEmail userEmail;

    @Mock
    Email email;

    /**
     * BeforeAll 是针对 static method 的
     */
    @BeforeEach
    public void beforeEach() {
        Mockito.when(email.getUsername()).thenReturn("neko");
    }

    @DisplayName("Should be neko")
    @Test
    public void test() {
        Assertions.assertEquals("neko", userEmail.getEmail().getUsername());
    }

}
