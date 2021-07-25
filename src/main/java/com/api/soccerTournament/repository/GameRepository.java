package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GameRepository implements IRepository {
    private final DbApi dbApi;

    public GameRepository(DbApi dbApi) {
        this.dbApi = dbApi;
    }

    private static final String tableName = "game";
    private static final Class cls = Game.class;

    public Response<Game> readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(Game game) {
        Response response = dbApi.write(Optional.of(game), tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
