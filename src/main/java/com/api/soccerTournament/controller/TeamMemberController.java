package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.TeamMemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/teamMember")
@CrossOrigin
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<TeamMember> readAll() {
        Response response = teamMemberService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody TeamMember teamMember) {
        if (teamMember == null) return new Response(Const.statusCodeFailParamNull, "teamMember");
        if (teamMember.teamId == null) return new Response(Const.statusCodeFailParamNull, "teamMember.teamId");
        if (teamMember.personId == null) return new Response(Const.statusCodeFailParamNull, "teamMember.personId");
        if (teamMember.teamId <= 0) return new Response(Const.statusCodeFailParamInvalid, "teamMember.teamId");
        if (teamMember.personId <= 0) return new Response(Const.statusCodeFailParamInvalid, "teamMember.personId");

        Response response = teamMemberService.write(teamMember);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = teamMemberService.delete(id);
        return response;
    }
}

