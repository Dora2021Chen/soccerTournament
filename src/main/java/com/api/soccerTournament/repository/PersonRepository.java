package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class PersonRepository {
    @Autowired
    protected DbApi dbApi;

    protected static final String tableName = "person";
    protected static final Class cls = Person.class;

    protected Response write(Person person, Integer role) {
        Optional<Person> optionalPerson = Optional.of(person);
        person.role = role;

        Response response = dbApi.write(optionalPerson, tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
