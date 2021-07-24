package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Tournament;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/soccerTournament/tournament")
@CrossOrigin
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response readAll() {
        Response response = tournamentService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Tournament tournament){
        Response response = tournamentService.write(tournament);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        Response response = tournamentService.delete(id);
        return response;
    }
}
