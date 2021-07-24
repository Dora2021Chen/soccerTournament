package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachService implements IService {
    @Autowired
    private CoachRepository coachRepository;

    public Response<Person> readAll() {
        Response<Person> response = coachRepository.readAll();
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
