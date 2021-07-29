package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.Team;
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
        String url = baseUrlGame + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrlGame + "/getById";
        readById(url);
    }

    int writeOnce(Game game, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = baseUrlGame + "/write";
        return writeOnce(url, Optional.of(game), expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void write() throws Exception {
        Game game = new Game();
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.roundNo = -1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 0;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 100;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 101;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.roundNo = 1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team1 = -1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team1 = 0;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team1 = 100000;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team2 = -1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team2 = 0;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);
        game.team2 = 100000;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.team1 = writeATeam();
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.team2 = writeATeam();
        int gameId = writeOnce(game, Const.STATUS_CODE_SUCCEED, STATUS_INVALID);

        game.winner = -1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.winner = 0;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.winner = 100000;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.winner =game.team1;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.winner =game.team2;
        writeOnce(game, STATUS_INVALID, Const.STATUS_CODE_SUCCEED);

        game.id=gameId;
        int gameIdNew = writeOnce(game, Const.STATUS_CODE_SUCCEED, STATUS_INVALID);

        assertEquals(gameId,gameIdNew);
    }

    @Test
    void delete() {
    }
}