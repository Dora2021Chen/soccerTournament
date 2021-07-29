package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.TeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/team")
@CrossOrigin
public class TeamController implements IController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(path = "/getAll", produces = Const.RESPONSE_FORMAT)
    public Response<Team> readAll() {
        Response response = teamService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.RESPONSE_FORMAT)
    @Override
    public Response<Team> readById(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = teamService.readById(id);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.RESPONSE_FORMAT)
    public Response write(@RequestBody Team team) {
        if (team == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "team");
        if (team.name == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "team.name");
        team.name = team.name.trim();
        if (team.name.length() == 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_EMPTY, "team.name");
        if (team.name.length() > Const.MAX_NAME_LEN)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, "team.name");

        Response response = teamService.write(team);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.RESPONSE_FORMAT)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = teamService.delete(id);
        return response;
    }
}

