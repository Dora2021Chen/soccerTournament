package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.TeamMemberService;
import com.api.soccerTournament.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/soccerTournament/teamMember")
@CrossOrigin
public class TeamMemberController {
    @Autowired
    private TeamMemberService teamMemberService;

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response readAll() {
        Response response = teamMemberService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody TeamMember teamMember) {
        Response response = teamMemberService.write(teamMember);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        Response response = teamMemberService.delete(id);
        return response;
    }
}

