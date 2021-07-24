package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/coach")
@CrossOrigin
public class CoachController {
    @Autowired
    private CoachService coachService;

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<Person> readAll() {
        Response<Person> response = coachService.readAll();
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Person coach){
        if (coach == null) return new Response(Const.statusCodeFailParamNull, "coach");
        if (coach.name == null) return new Response(Const.statusCodeFailParamNull, "coach.name");
        if (coach.name.trim().length() == 0) return new Response(Const.statusCodeFailParamEmpty, "team.coach");

        Response response = coachService.write(coach);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = coachService.delete(id);
        return response;
    }
}

