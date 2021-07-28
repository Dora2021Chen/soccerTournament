package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.utility.Utility;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class TestBase {
    @Autowired
    MockMvc mockMvc;

    Gson gson = new Gson();

    private static final MediaType APPLICATION_JSON_UTF8
            = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

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

    void readById(String url) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", "1");
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(url).params(requestParams));
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        Utility.printStr(responseStr);
        Response response = gson.fromJson(responseStr, Response.class);
        assertNotNull(response);
        assertEquals(response.statusCode, Const.STATUS_CODE_SUCCEED);
        assertNotNull(response.entities);
        assert (response.entities.size() <= 1);
    }

    void writeOnce(String url, Optional<? extends Entity> optionalEntity, int expectedResultCode) throws Exception {
        Entity entity = optionalEntity.get();
        String teamJson = Utility.getGsonStr(entity);
        RequestBuilder requestBuilder;
        requestBuilder = MockMvcRequestBuilders.post(url)
                .contentType(APPLICATION_JSON_UTF8).content(teamJson);
        ResultActions actions = mockMvc.perform(requestBuilder);
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        Utility.printStr(responseStr);
        Response response = gson.fromJson(responseStr, Response.class);
        assertNotNull(response);
        assertEquals(response.statusCode, expectedResultCode);
        assertNotNull(response.entities);
        assert (response.entities.size() <= 1);
    }
}
