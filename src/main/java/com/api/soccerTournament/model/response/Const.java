package com.api.soccerTournament.model.response;

import java.util.HashMap;

public interface Const {
    int statusCodeSucceed = 0;
    int statusCodeFail = 1000;
    int statusCodeFailParamNull = 1001;
    int statusCodeFailParamInvalid = 1002;
    int statusCodeFailParamEmpty = 1003;

    HashMap<Integer, String> statusMap = new HashMap<Integer, String>() {{
        put(statusCodeSucceed, "succeed");
        put(statusCodeFail, "fail");
        put(statusCodeFailParamNull, "parameter is null");
        put(statusCodeFailParamInvalid, "parameter is invalid");
        put(statusCodeFailParamEmpty, "parameter is empty");
    }};

    //0, player, 1, coach, 2, referee, ...
    int rolePlayer = 0;
    int roleCoach = 1;
    int roleReferee = 2;

    String responseFormat = "application/json";
}
