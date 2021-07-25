package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TeamMemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    private String baseUrl = "/api/soccerTournament/teamMember";

    Gson gson = new Gson();

    @Test
    void readAll() throws Exception {
        String url = baseUrl + "/getAll";

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(url));
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        String responseStr = actions.andReturn().getResponse().getContentAsString();
        assertNotNull(responseStr);
        System.out.println(responseStr);
        Response response = gson.fromJson(responseStr, Response.class);
        System.out.println(response.statusCode);
        assertNotNull(response);
        assertEquals(response.statusCode, Const.statusCodeSucceed);
    }

    @Test
    void write() {
    }

    @Test
    void delete() {
    }
}