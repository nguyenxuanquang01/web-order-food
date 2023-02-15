package com.dac.topic3;

import com.dac.topic3.entity.User;
import com.dac.topic3.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    UserRepository repo;

    @Test
    public void testCreateUser(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "111111";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User("user2", encodedPassword,"inactive","ROLE_CUSTOMER");
        User savedUser = repo.save(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);

    }
}
