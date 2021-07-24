package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository extends PersonRepository implements IRepository {
    private static Integer role = Const.rolePlayer;
    private static final String roleFilter = "role=" + role;

    public Response readAll() {
        Response response = dbApi.readByFilters(tableName, roleFilter, cls);
        return response;
    }

    public Response write(Person person) {
        Response response = write(person, role);
        return response;
    }
}
