package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepository extends PersonRepository implements IBaseRepository {
    public PlayerRepository(DbApi dbApi, TeamRepository teamRepository) {
        super(dbApi, teamRepository);
    }

    @Override
    public Response readAll() {
        Response<Person> response = readByRole(Const.ROLE_PLAYER);
        return response;
    }

    @Override
    public Response readById(Integer id) {
        Response response = readById(id, Const.ROLE_PLAYER);
        return response;
    }

    public Response write(Person person) {
        Response response = write(person, Const.ROLE_PLAYER);
        return response;
    }
}
