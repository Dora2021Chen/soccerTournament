package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CoachService implements IService {
    @Autowired
    private CoachRepository coachRepository;

    public Response readAll() {
        Response response = coachRepository.readAll();
        return response;
    }

    public Response write(Person person) {
        Response response = coachRepository.write(person);
        return response;
    }

    public Response delete(Integer id) {
        Response response = coachRepository.delete(id);
        return response;
    }
}
