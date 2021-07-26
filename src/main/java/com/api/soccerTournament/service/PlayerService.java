package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements IService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Response<Person> readAll() {
        Response response = playerRepository.readAll();
        return response;
    }

    @Override
    public Response<? extends Entity> readById(Integer id) {
        Response response = playerRepository.readById(id);
        return response;
    }

    public Response write(Person person) {
        Response response = playerRepository.write(person);
        return response;
    }

    @Override
    public Response delete(Integer id) {
        Response response = playerRepository.delete(id);
        return response;
    }
}
