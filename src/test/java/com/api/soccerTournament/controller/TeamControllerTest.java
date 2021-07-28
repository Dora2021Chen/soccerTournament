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
    private String baseUrl = "/api/soccerTournament/team";

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

    void writeOnce(Team team, int expectedResultCode) throws Exception {
        String url = baseUrl + "/write";
        writeOnce(url, Optional.of(team), expectedResultCode);
    }

    @Test
    void write() throws Exception {
        Team team = new Team();
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_NULL);
        team.name = "  ";
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_EMPTY);

        String teamName = "test_" + System.currentTimeMillis();
        team.name = teamName;
        writeOnce(team, Const.STATUS_CODE_SUCCEED);
        writeOnce(team, Const.STATUS_CODE_FAIL_TEAM_NAME_EXISTS);

        team.name = TestBase.getStr(51);
        writeOnce(team, Const.STATUS_CODE_FAIL_PARAM_TOO_LONG);

        team.name = TestBase.getStr(50);
        writeOnce(team, Const.STATUS_CODE_SUCCEED);
    }

    @Test
    void delete() {
    }
}