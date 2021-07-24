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
    public Response write(@RequestBody Person person){
        Response response = coachService.write(person);
        return response;
    }

    @PostMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestBody Integer id) {
        Response response = coachService.delete(id);
        return response;
    }
}

