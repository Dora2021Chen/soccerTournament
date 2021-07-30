package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Person;
import com.api.soccerTournament.model.PersonInternal;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
class PersonRepository extends ParticipantRepository {
    protected final TeamRepository teamRepository;

    public PersonRepository(DbApi dbApi, TeamRepository teamRepository) {
        super(dbApi);
        this.teamRepository = teamRepository;
    }

    private static final String tableName = "person";
    private static final Class clsOutput = Person.class;
    private static final Class clsOutput1 = PersonInternal.class;

    protected Response readByRole(Byte role) {
        String colName = "role";
        Response response = dbApi.readByColumn(colName, tableName, clsOutput, role);
        return response;
    }

    protected Response readById(Integer id, Byte role) {
        Response response = readById(id, tableName, clsOutput1);
        if (response.statusCode != Const.STATUS_CODE_SUCCEED) {
            return response;
        }

        if (response.entities.size() == 0) {
            return response;
        }

        PersonInternal personInternal = (PersonInternal) response.getEntity();

        if (personInternal.role.equals(role)) {
            return response;
        }

        response.entities.clear();

        return response;
    }

    public Response readByIdDocNumber(String idDocNumber) {
        String columnName = "idDocNumber";
        Response response = dbApi.readByColumn(columnName, tableName, clsOutput, idDocNumber);
        return response;
    }

    protected Response write(Person person, Byte role) {
        PersonInternal personInternal = new PersonInternal(person, role);
        Response response = readByIdDocNumber(person.idDocNumber);
        if (response.statusCode != Const.STATUS_CODE_SUCCEED) {
            return response;
        }

        if (response.entities.size() > 0) {
            if (person.id == null) {
                return new Response(Const.STATUS_CODE_FAIL_PERSON_ID_DOC_NUMBER_EXISTS);
            }

            Person personOld = (Person) response.getEntity();
            if (!personOld.id.equals(person.id)) {
                return new Response(Const.STATUS_CODE_FAIL_PERSON_ID_DOC_NUMBER_EXISTS);
            }
        }

        response = teamRepository.readById(person.teamId);
        if (response.statusCode != Const.STATUS_CODE_SUCCEED) {
            return response;
        }

        if (response.entities.size() == 0) {
            return new Response(Const.STATUS_CODE_FAIL_TEAM_NOT_EXISTS);
        }

        response = dbApi.write(Optional.of(personInternal), tableName);
        return response;
    }

    protected Response delete(Integer id, Byte role) {
        String sql = "delete from person where id=? and role=?";
        ArrayList<Object> parameters = new ArrayList<Object>() {{
            add(id);
            add(role);
        }};

        Response response = dbApi.executeNonQuery(sql, parameters);
        return response;
    }
}
