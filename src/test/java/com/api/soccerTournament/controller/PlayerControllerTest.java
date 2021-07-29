package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest extends TestBase {
    @Test
    void readAll() throws Exception {
        String url = baseUrlPlayer + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrlPlayer + "/getById";
        readById(url);
    }

    void writeOnce(Person person, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = baseUrlPlayer + "/write";
        writeOnce(url, Optional.of(person), expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void write() {
    }

    @Test
    void delete() {
    }
}