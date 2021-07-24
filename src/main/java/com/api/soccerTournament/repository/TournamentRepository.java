package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Tournament;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class TournamentRepository implements IRepository {
    @Autowired
    private DbApi dbApi;

    private static final String tableName = "tournament";
    private static final Class cls = Tournament.class;

    public Response readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(Tournament tournament) {
        Optional<Tournament> optionalTournament = Optional.of(tournament);
        Response response = dbApi.write(optionalTournament, tableName, cls);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
