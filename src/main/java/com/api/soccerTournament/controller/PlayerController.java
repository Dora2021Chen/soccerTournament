package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/player")
@CrossOrigin
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<Person> readAll() {
        Response response = playerService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Person player) {
        if (player == null) return new Response(Const.statusCodeFailParamNull, "player");
        if (player.name == null) return new Response(Const.statusCodeFailParamNull, "player.name");
        if (player.name.trim().length() == 0) return new Response(Const.statusCodeFailParamEmpty, "player.coach");

        Response response = playerService.write(player);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = playerService.delete(id);
        return response;
    }
}

