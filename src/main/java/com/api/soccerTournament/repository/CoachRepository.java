package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Service;

@Service
public class CoachRepository extends PersonRepository implements IRepository {
    public Response<Person> readAll() {
        Response<Person> response = readByRole(Const.roleCoach);
        return response;
    }

    public Response write(Person person) {
        Response response = write(person, Const.roleCoach);
        return response;
    }
}
