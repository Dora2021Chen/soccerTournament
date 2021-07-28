package com.api.soccerTournament.model.response;

import java.util.HashMap;

public interface Const {
    int STATUS_CODE_SUCCEED = 0;
    int STATUS_CODE_FAIL = 1000;
    int STATUS_CODE_FAIL_PARAM_NULL = 1001;
    int STATUS_CODE_FAIL_PARAM_INVALID = 1002;
    int STATUS_CODE_FAIL_PARAM_EMPTY = 1003;
    int STATUS_CODE_FAIL_PARAM_TOO_LONG = 1004;
    int STATUS_CODE_FAIL_TEAM_NAME_EXISTS = 1100;
    int STATUS_CODE_FAIL_PERSON_ID_DOC_NUMBER_EXISTS = 1101;
    int STATUS_CODE_FAIL_GAME_EXISTS = 1102;
    int STATUS_CODE_FAIL_TEAM_NOT_EXISTS = 1200;
    int STATUS_CODE_FAIL_GAME_NOT_EXISTS = 1201;

    HashMap<Integer, String> statusMap = new HashMap<Integer, String>() {{
        put(STATUS_CODE_SUCCEED, "succeed");
        put(STATUS_CODE_FAIL, "internal error");
        put(STATUS_CODE_FAIL_PARAM_NULL, "parameter is null");
        put(STATUS_CODE_FAIL_PARAM_INVALID, "parameter is invalid");
        put(STATUS_CODE_FAIL_PARAM_EMPTY, "parameter is empty");
        put(STATUS_CODE_FAIL_PARAM_TOO_LONG, "parameter is too long");
        put(STATUS_CODE_FAIL_TEAM_NAME_EXISTS, "team name exists");
        put(STATUS_CODE_FAIL_PERSON_ID_DOC_NUMBER_EXISTS, "idDocNumber exists");
        put(STATUS_CODE_FAIL_GAME_EXISTS, "game exists");
        put(STATUS_CODE_FAIL_TEAM_NOT_EXISTS, "team not exists");
        put(STATUS_CODE_FAIL_GAME_NOT_EXISTS, "game not exists");
    }};

    //0, player, 1, coach
    byte ROLE_PLAYER = 0;
    byte ROLE_COACH = 1;

    int MAX_NAME_LEN = 50;
    int MAX_ID_DOC_NUMBER_LEN = 50;

    String RESPONSE_FORMAT = "application/json";
}
