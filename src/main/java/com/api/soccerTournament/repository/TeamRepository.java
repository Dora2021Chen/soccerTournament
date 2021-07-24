package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamRepository implements IRepository {
    @Autowired
    private DbApi dbApi;

    private static final String tableName = "team";
    private static final Class cls = Team.class;

    public Response<Team> readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(Team team) {
        Response response = dbApi.write(Optional.of(team), tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
