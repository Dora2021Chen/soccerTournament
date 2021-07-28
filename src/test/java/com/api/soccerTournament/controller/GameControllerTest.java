package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest extends TestBase {
    private String baseUrl = "/api/soccerTournament/game";

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

    void writeOnce(Game game, int expectedResultCode) throws Exception {
        String url = baseUrl + "/write";
        writeOnce(url, Optional.of(game), expectedResultCode);
    }

    @Test
    void write() {
    }

    @Test
    void delete() {
    }
}