package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.CoachService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/coach")
@CrossOrigin
public class CoachController implements IController {
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<Person> readAll() {
        Response response = coachService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.responseFormat)
    @Override
    public Response<Person> readById(@RequestParam Integer id) {
        Response response = coachService.readById(id);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Person coach) {
        if (coach == null) return new Response(Const.statusCodeFailParamNull, "coach");
        if (coach.name == null) return new Response(Const.statusCodeFailParamNull, "coach.name");
        if (coach.name.trim().length() == 0) return new Response(Const.statusCodeFailParamEmpty, "team.coach");

        Response response = coachService.write(coach);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = coachService.delete(id);
        return response;
    }
}

