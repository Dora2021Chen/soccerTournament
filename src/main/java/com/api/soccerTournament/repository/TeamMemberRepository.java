package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.TeamMember;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TeamMemberRepository implements IRepository {
    private final DbApi dbApi;

    public TeamMemberRepository(DbApi dbApi) {
        this.dbApi = dbApi;
    }

    private static final String tableName = "team_member";
    private static final Class cls = TeamMember.class;

    public Response<TeamMember> readAll() {
        Response response = dbApi.readAll(tableName, cls);
        return response;
    }

    public Response write(TeamMember teamMember) {
        Response response = dbApi.write(Optional.of(teamMember), tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
