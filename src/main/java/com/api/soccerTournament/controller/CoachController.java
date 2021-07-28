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

    @GetMapping(path = "/getAll", produces = Const.RESPONSE_FORMAT)
    public Response<Person> readAll() {
        Response response = coachService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.RESPONSE_FORMAT)
    @Override
    public Response<Person> readById(@RequestParam Integer id) {
        Response response = coachService.readById(id);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.RESPONSE_FORMAT)
    public Response write(@RequestBody Person coach) {
        if (coach == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "coach");

        if (coach.name == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "coach.name");
        coach.name = coach.name.trim();
        if (coach.name.length() == 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_EMPTY, "coach.name");
        if (coach.name.length() > Const.MAX_ID_DOC_NUMBER_LEN)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, "coach.name");

        if (coach.idDocNumber == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "coach.idDocNumber");
        coach.idDocNumber = coach.idDocNumber.trim();
        if (coach.idDocNumber.length() == 0)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_EMPTY, "coach.idDocNumber");
        if (coach.idDocNumber.length() > Const.MAX_NAME_LEN)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_TOO_LONG, "coach.idDocNumber");

        if (coach.teamId == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "coach.teamId");
        if (coach.teamId <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "coach.teamId");

        Response response = coachService.write(coach);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.RESPONSE_FORMAT)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = coachService.delete(id);
        return response;
    }
}

