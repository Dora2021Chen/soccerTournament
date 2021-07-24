package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements IService {
    @Autowired
    private PlayerRepository playerRepository;

    public Response readAll() {
        Response response = playerRepository.readAll();
        return response;
    }

    public Response write(Person person) {
        Response response = playerRepository.write(person);
        return response;
    }

    public Response delete(Integer id) {
        Response response = playerRepository.delete(id);
        return response;
    }
}
