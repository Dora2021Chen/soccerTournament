package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Team;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.utility.Utility;
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
        if (response.statusCode != Const.STATUS_CODE_SUCCEED) {
            Utility.printGsonStr(response);
            return response;
        }

        if (response.entities.size() > 0) {
            return new Response(Const.STATUS_CODE_FAIL_TEAM_NAME_EXISTS);
        }

        response = dbApi.write(Optional.of(team), tableName);
        return response;
    }

    @Override
    public Response delete(Integer id) {
        Response response = readById(id);
        if (response.statusCode != 0) {
            return response;
        }

        if (response.entities.size() == 0) {
            return new Response(Const.STATUS_CODE_FAIL_TEAM_NOT_EXISTS);
        }

        response = dbApi.delete(id, tableName);
        return response;
    }
}
