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
    private String baseUrl = "/api/soccerTournament/player";

    @Test
    void readAll() throws Exception {
        String url = baseUrl + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrl + "/getById";
        readById(url);
    }

    void writeOnce(Person person, int expectedResultCode) throws Exception {
        String url = baseUrl + "/write";
        writeOnce(url, Optional.of(person), expectedResultCode);
    }

    @Test
    void write() {
    }

    @Test
    void delete() {
    }
}