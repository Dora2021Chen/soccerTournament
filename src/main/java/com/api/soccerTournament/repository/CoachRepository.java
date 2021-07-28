package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public class CoachRepository extends PersonRepository implements IBaseRepository {
    public CoachRepository(DbApi dbApi, TeamRepository teamRepository) {
        super(dbApi, teamRepository);
    }

    @Override
    public Response readAll() {
        Response response = readByRole(Const.ROLE_COACH);
        return response;
    }

    public Response write(Person person) {
        Response response = write(person, Const.ROLE_COACH);
        return response;
    }
}
