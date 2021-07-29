package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.utility.Utility;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestBase {
    @Autowired
    MockMvc mockMvc;

    protected static final String baseUrlTeam = "/api/soccerTournament/team";
    protected static final String baseUrlGame = "/api/soccerTournament/game";
    protected static final String baseUrlCoach = "/api/soccerTournament/coach";
    protected static final String baseUrlPlayer = "/api/soccerTournament/player";

    Gson gson = new Gson();

    protected static final int INVALID_STATUS = -1;
    protected static final int INVALID_ID = -1;

    private static final MediaType APPLICATION_JSON_UTF8
            = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    public static boolean isStatusCodeValid(int statusCode) {
        return Const.STATUS_MAP.containsKey(statusCode);
    }

    public static String getStr(int length) {
        StringBuilder teamNameBuilder = new StringBuilder();
        teamNameBuilder.append("test");
        while (teamNameBuilder.length() < Const.MAX_NAME_LEN) {
            teamNameBuilder.append("_").append(System.currentTimeMillis());
        }
        teamNameBuilder.delete(length, teamNameBuilder.length());

        return teamNameBuilder.toString();
    }

    void readAll(String url) throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(url));
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        Utility.printStr(responseStr);
        Response response = gson.fromJson(responseStr, Response.class);
        assertNotNull(response);
        assertEquals(response.statusCode, Const.STATUS_CODE_SUCCEED);
        assertNotNull(response.entities);
    }

    int readById(String url, Integer id) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", id.toString());
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(url).params(requestParams));
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        Utility.printStr(responseStr);
        Response response = gson.fromJson(responseStr, new TypeToken<Response<Entity>>() {
        }.getType());
        assertNotNull(response);
        assertEquals(response.statusCode, Const.STATUS_CODE_SUCCEED);
        assertNotNull(response.entities);
        assert (response.entities.size() <= 1);
        if (response.entities.size() == 1) {
            return response.getEntity().id;
        } else {
            return INVALID_ID;
        }
    }

    int writeOnce(String url, Optional<? extends Entity> optionalEntity
            , int expectedResultCode, int unUxpectedResultCode) throws Exception {
        String teamJson = Utility.getGsonStr(optionalEntity.get());
        RequestBuilder requestBuilder;
        requestBuilder = MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON_UTF8).content(teamJson);
        ResultActions actions = mockMvc.perform(requestBuilder);
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        Utility.printStr(responseStr);
        Response response = gson.fromJson(responseStr, new TypeToken<Response<Entity>>() {
        }.getType());
        assertNotNull(response);
        if (isStatusCodeValid(expectedResultCode)) {
            assertEquals(response.statusCode, expectedResultCode);
        }

        if (isStatusCodeValid(unUxpectedResultCode)) {
            assertNotEquals(response.statusCode, unUxpectedResultCode);
        }

        assertNotNull(response.entities);

        if (response.statusCode == Const.STATUS_CODE_SUCCEED) {
            assert (response.entities.size() == 1);
            Entity entity = response.getEntity();
            return entity.id;
        } else {
            assert (response.entities.size() == 0);
            return INVALID_ID;
        }
    }

    int writeATeam() throws Exception {
        Team team = new Team();
        team.name = TestBase.getStr(50);
        String url = baseUrlTeam + "/write";
        int teamId = writeOnce(url, Optional.of(team), Const.STATUS_CODE_SUCCEED, INVALID_STATUS);
        return teamId;
    }
}
