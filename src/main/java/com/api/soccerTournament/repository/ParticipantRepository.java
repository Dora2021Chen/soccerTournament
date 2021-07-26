package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantRepository extends BaseRepository {
    public ParticipantRepository(DbApi dbApi) {
        super(dbApi);
    }

    protected Response readByName(String name, String tableName, Class cls) {
        String columnName = "name";
        Response response = dbApi.readByColumn(columnName, tableName, cls, name);
        return response;
    }
}
