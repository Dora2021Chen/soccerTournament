package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class PersonTest extends TestBase {

    int writeACoach() throws Exception {
        String url = baseUrlCoach + "/write";
        return writeAPerson(url);
    }

    int writeAPlayer() throws Exception {
        String url = baseUrlPlayer + "/write";
        return writeAPerson(url);
    }

    int writeOnce(String url, Person person, int expectedResultCode, int unUxpectedResultCode) throws Exception {
        return writeOnce(url, Optional.of(person), expectedResultCode, unUxpectedResultCode);
    }

    void write(String url) throws Exception {
        Person person = new Person();
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.name = getStr(Const.MAX_NAME_LEN + 1);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.name = getStr(Const.MAX_NAME_LEN);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.idDocNumber = getStr(Const.MAX_NAME_LEN + 1);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.idDocNumber = getStr(Const.MAX_NAME_LEN);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.teamId = -1;
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.teamId = 0;
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.teamId = writeATeam();
        writeOnce(url, person, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        person.name = getStr(Const.MAX_NAME_LEN + 1);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.name = getStr(Const.MAX_NAME_LEN);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.idDocNumber = getStr(Const.MAX_NAME_LEN + 1);
        writeOnce(url, person, INVALID_STATUS, Const.STATUS_CODE_SUCCEED);
        person.idDocNumber = getStr(Const.MAX_NAME_LEN);
        int personId = writeOnce(url, person, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        person.id = personId;
        person.name = getStr(Const.MAX_NAME_LEN);
        writeOnce(url, person, Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
    }
}
