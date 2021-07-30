package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Const;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest extends TestBase {
    @Test
    void readAll() throws Exception {
        String url = BASE_URL_GAME + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = BASE_URL_GAME + "/getById";
        int gameId = writeAGame();
        int gameIdReturnedByRead = readById(url, gameId);
        assertEquals(gameId, gameIdReturnedByRead);
    }

    @Test
    void writeOnce() throws Exception {
        int gameId = writeAGame();
        System.out.println("gameId:" + gameId);
    }

    int writeOnce(Game game, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = BASE_URL_GAME + "/write";
        return writeOnce(url, Optional.of(game), expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void write() throws Exception {
        Game game = new Game();
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.roundNo = -1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 0;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 100;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 101;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team1 = -1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team1 = 0;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team1 = 100000;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team2 = -1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team2 = 0;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        game.team2 = 100000;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.team1 = writeATeam();
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.team2 = writeATeam();
        int gameId = writeOnce(game, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);

        game.winner = -1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.winner = 0;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.winner = game.team2 + 1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.winner = game.team1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.winner = game.team2;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        game.id = gameId;
        int gameIdNew = writeOnce(game, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);

        game.winner = game.team2 + 1;
        writeOnce(game, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);

        assertEquals(gameId, gameIdNew);
    }

    void delete(int id, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = BASE_URL_GAME + "/delete";
        delete(url, id, expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void delete() throws Exception {
        delete(-1, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        delete(0, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        int gameId = writeAGame();
        delete(gameId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(gameId, Const.STATUS_CODE_FAIL_GAME_NOT_EXISTS, INVALID_STATUS);
    }
}