package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Const;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest extends TestBase {
    @Test
    void readAll() throws Exception {
        String url = baseUrlTeam + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = baseUrlTeam + "/getById";
        readById(url);
    }

    int writeOnce(Team team, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = baseUrlTeam + "/write";
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
        writeOnce(team, Const.STATUS_CODE_SUCCEED, STATUS_INVALID);
        writeOnce(team, Const.STATUS_CODE_FAIL_TEAM_NAME_EXISTS, Const.STATUS_CODE_SUCCEED);

        team.name = TestBase.getStr(51);
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, Const.STATUS_CODE_SUCCEED);

        team.name = TestBase.getStr(50);
        writeOnce(team, Const.STATUS_CODE_SUCCEED, STATUS_INVALID);
    }

    @Test
    void delete() {
    }
}