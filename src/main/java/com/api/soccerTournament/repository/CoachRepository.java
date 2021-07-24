package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Service;

@Service
public class CoachRepository extends PersonRepository implements IRepository {
    private static Integer role = Const.roleCoach;
    private static final String roleFilter = "role=" + Const.roleCoach;

    public Response<Person> readAll() {
        Response<Person> response = dbApi.readByFilters(tableName, roleFilter, cls);
        return response;
    }

    public Response write(Person person) {
        Response response = write(person, role);
        return response;
    }
}
