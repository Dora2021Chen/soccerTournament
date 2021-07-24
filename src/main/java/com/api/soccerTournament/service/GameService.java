package com.api.soccerTournament.service;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IService {
    @Autowired
    private GameRepository gameRepository;

    public Response<Game> readAll() {
        Response response = gameRepository.readAll();
        return response;
    }

    public Response write(Game game) {
        Response response = gameRepository.write(game);
        return response;
    }

    public Response delete(Integer id) {
        Response response = gameRepository.delete(id);
        return response;
    }
}
