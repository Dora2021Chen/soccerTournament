package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.PlayerService;
import com.api.soccerTournament.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/soccerTournament/player")
@CrossOrigin
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response readAll() {
        Response response = playerService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Person person){
        Response response = playerService.write(person);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        Response response = playerService.delete(id);
        return response;
    }
}

