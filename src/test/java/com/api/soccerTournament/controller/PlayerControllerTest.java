package com.api.soccerTournament.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest extends PersonTest {
    @Test
    void readAll() throws Exception {
        String url = baseUrlPlayer + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrlPlayer + "/getById";
        int personId = writeAPlayer();
        int personIdReturnedByRead = readById(url, personId);
        assertEquals(personId, personIdReturnedByRead);

        personId = writeACoach();
        personIdReturnedByRead = readById(url, personId);
        assertEquals(INVALID_ID, personIdReturnedByRead);
    }

    @Test
    void write() throws Exception {
        String url = baseUrlPlayer + "/write";
        write(url);
    }

    @Test
    void delete() {
    }
}