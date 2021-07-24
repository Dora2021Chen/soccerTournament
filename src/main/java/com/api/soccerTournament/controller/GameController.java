package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/game")
@CrossOrigin
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response readAll() {
        Response response = gameService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Game game){
        Response response = gameService.write(game);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        Response response = gameService.delete(id);
        return response;
    }
}

