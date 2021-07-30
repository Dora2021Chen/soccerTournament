package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.response.Const;
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
        String url = BASE_URL_PLAYER + "/getAll";
        readAll(url);
    }

    @Test
    void readById() throws Exception {
        String url = BASE_URL_PLAYER + "/getById";
        int personId = writeAPlayer();
        int personIdReturnedByRead = readById(url, personId);
        assertEquals(personId, personIdReturnedByRead);

        personId = writeACoach();
        personIdReturnedByRead = readById(url, personId);
        assertEquals(INVALID_ID, personIdReturnedByRead);
    }

    @Test
    void write() throws Exception {
        String url = BASE_URL_PLAYER + "/write";
        write(url);
    }

    void delete(int id, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String url = BASE_URL_PLAYER + "/delete";
        delete(url, id, expectedResultCode, unUxpectedResultCode);
    }

    @Test
    void delete() throws Exception {
        delete(-1, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        delete(0, Const.STATUS_CODE_FAIL_PARAM_INVALID, INVALID_STATUS);
        int personId = writeAPlayer();
        delete(personId, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        delete(personId, Const.STATUS_CODE_FAIL_PLAYER_NOT_EXISTS, INVALID_STATUS);

        personId = writeACoach();
        delete(personId, Const.STATUS_CODE_FAIL_PLAYER_NOT_EXISTS, INVALID_STATUS);
    }
}