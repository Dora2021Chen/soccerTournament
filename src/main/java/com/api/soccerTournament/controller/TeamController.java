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

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<Team> readAll() {
        Response response = teamService.readAll();
        return response;
    }

    @PostMapping(path = "/getById", produces = Const.responseFormat)
    @Override
    public Response<Team> readById(@RequestBody Integer id) {
        Response response = teamService.readById(id);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Team team) {
        if (team == null) return new Response(Const.statusCodeFailParamNull, "team");
        if (team.name == null) return new Response(Const.statusCodeFailParamNull, "team.name");
        if (team.name.trim().length() == 0) return new Response(Const.statusCodeFailParamEmpty, "team.name");

        Response response = teamService.write(team);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = teamService.delete(id);
        return response;
    }
}

