package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/player")
@CrossOrigin
public class PlayerController implements IController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "/getAll", produces = Const.RESPONSE_FORMAT)
    public Response<Person> readAll() {
        Response response = playerService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.RESPONSE_FORMAT)
    @Override
    public Response<Person> readById(@RequestParam Integer id) {
        Response response = playerService.readById(id);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.RESPONSE_FORMAT)
    public Response write(@RequestBody Person player) {
        if (player == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "player");

        if (player.name == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "player.name");
        player.name = player.name.trim();
        if (player.name.length() == 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_EMPTY, "player.name");
        if (player.name.length() > Const.MAX_NAME_LEN)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, "player.name");

        if (player.idDocNumber == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "player.idDocNumber");
        player.idDocNumber = player.idDocNumber.trim();
        if (player.idDocNumber.length() == 0)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_EMPTY, "player.idDocNumber");
        if (player.idDocNumber.length() > Const.MAX_ID_DOC_NUMBER_LEN)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, "player.idDocNumber");

        if (player.teamId == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "player.teamId");
        if (player.teamId <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "player.teamId");

        Response response = playerService.write(player);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.RESPONSE_FORMAT)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = playerService.delete(id);
        return response;
    }
}

