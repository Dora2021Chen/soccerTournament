package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
class PersonRepository {
    @Autowired
    protected DbApi dbApi;

    private static final String tableName = "person";
    private static final Class cls = Person.class;

    protected Response<Person> readByRole(Integer role) {
        String roleFilter = "role=?";
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(role);

        Response response = dbApi.readByFilters(tableName, roleFilter, parameters, cls);
        return response;
    }

    protected Response write(Person person, Integer role) {
        person.role = role;
        Response response = dbApi.write(Optional.of(person), tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
