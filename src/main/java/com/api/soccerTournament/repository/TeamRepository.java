package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamRepository extends ParticipantRepository implements IBaseRepository {
    public TeamRepository(DbApi dbApi) {
        super(dbApi);
    }

    private static final String tableName = "team";
    private static final Class cls = Team.class;

    @Override
    public Response readAll() {
        Response response = dbApi.readTable(tableName, cls);
        return response;
    }

    @Override
    public Response readById(Integer id) {
        Response response = readById(id, tableName, cls);
        return response;
    }

    protected Response readByName(String name) {
        Response response = readByName(name, tableName, cls);
        return response;
    }

    public Response write(Team team) {
        Response response = readByName(team.name);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }

        if (response.entities.size() > 0) {
            return new Response(Const.statusCodeFailTeamNameExists);
        }

        response = dbApi.write(Optional.of(team), tableName);
        return response;
    }

    @Override
    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
