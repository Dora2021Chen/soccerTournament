package com.api.soccerTournament.controller;

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
class TeamControllerTest extends TestBase {
    @Test
    void readAll() throws Exception {
        String url = BASE_URL_TEAM + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = BASE_URL_TEAM + "/getById";
        int teamId = writeATeam();
        int teamIdReturnedByRead = readById(url, teamId);
        assertEquals(teamId, teamIdReturnedByRead);
    }

    int writeOnce(Team team, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = BASE_URL_TEAM + "/write";
        return writeOnce(url, Optional.of(team), expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void write() throws Exception {
        Team team = new Team();
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_NULL, Const.STATUS_CODE_SUCCEED);
        team.name = "  ";
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_EMPTY, Const.STATUS_CODE_SUCCEED);

        String teamName = "test_" + System.currentTimeMillis();
        team.name = teamName;
        writeOnce(team, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        writeOnce(team, Const.STATUS_CODE_FAIL_TEAM_NAME_EXISTS, Const.STATUS_CODE_SUCCEED);

        team.name = TestBase.getStr(Const.MAX_NAME_LEN + 1);
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, Const.STATUS_CODE_SUCCEED);

        team.name = TestBase.getStr(Const.MAX_NAME_LEN);
        writeOnce(team, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
    }

    void delete(int id, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = BASE_URL_TEAM + "/delete";
        delete(url, id, expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void delete() throws Exception {
        delete(-1, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        delete(0, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        int teamId = writeATeam();
        delete(teamId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(teamId, Const.STATUS_CODE_FAIL_TEAM_NOT_EXISTS, INVALID_STATUS);

        int team1 = writeATeam();
        int team2 = writeATeam();
        int gameId = writeAGameForATeam(team1, team2);
        delete(team1, Const.STATUS_CODE_FAIL_TEAM_CHILD_EXISTS, INVALID_STATUS);
        delete(team2, Const.STATUS_CODE_FAIL_TEAM_CHILD_EXISTS, INVALID_STATUS);
        deleteAGame(gameId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(team1, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(team2, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);

        teamId = writeATeam();
        int personId = writeAPersonForATeam(BASE_URL_COACH, teamId);
        delete(teamId, Const.STATUS_CODE_FAIL_TEAM_CHILD_EXISTS, INVALID_STATUS);
        deleteAPerson(BASE_URL_COACH, personId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(teamId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
    }
}