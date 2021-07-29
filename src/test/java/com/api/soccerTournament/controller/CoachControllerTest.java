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
class CoachControllerTest extends PersonTest {
    @Test
    void readAll() throws Exception {
        String url = baseUrlCoach + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrlCoach + "/getById";
        int personId = writeACoach();
        int personIdReturnedByRead = readById(url, personId);
        assertEquals(personId, personIdReturnedByRead);

        personId = writeAPlayer();
        personIdReturnedByRead = readById(url, personId);
        assertEquals(INVALID_ID, personIdReturnedByRead);
    }

    @Test
    void write() throws Exception {
        String url = baseUrlCoach + "/write";
        write(url);
    }

    @Test
    void delete() {
    }
}