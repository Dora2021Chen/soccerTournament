package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class PersonRepository extends ParticipantRepository {
    protected final TeamRepository teamRepository;

    public PersonRepository(DbApi dbApi, TeamRepository teamRepository) {
        super(dbApi);
        this.teamRepository = teamRepository;
    }

    private static final String tableName = "person";
    private static final Class cls = Person.class;

    protected Response readByRole(Byte role) {
        String colName = "role";
        Response response = dbApi.readByColumn(colName, tableName, cls, role);
        return response;
    }

    public Response readById(Integer id) {
        Response response = readById(id, tableName, cls);
        return response;
    }

    protected Response readByIdDocNumber(String idDocNumber) {
        String columnName = "idDocNumber";
        Response response = dbApi.readByColumn(columnName, tableName, cls, idDocNumber);
        return response;
    }

    protected Response write(Person person, Byte role) {
        person.setRole(role);
        Response response = readByIdDocNumber(person.idDocNumber);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }

        if (response.entities.size() > 0) {
            if (person.id == null) {
                return new Response(Const.statusCodeFailTeamNameExists);
            }

            Person personOld = (Person) response.getEntity();
            if (personOld.id != person.id) {
                return new Response(Const.statusCodeFailTeamNameExists);
            }
        }

        response = teamRepository.readById(person.teamId);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }

        if (response.entities.size() == 0) {
            return new Response(Const.statusCodeFailTeamNotExists);
        }

        response = dbApi.write(Optional.of(person), tableName);
        return response;
    }

    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
