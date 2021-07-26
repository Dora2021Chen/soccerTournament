package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository {
    protected final DbApi dbApi;

    public BaseRepository(DbApi dbApi) {
        this.dbApi = dbApi;
    }

    protected Response readById(Integer id, String tableName, Class cls) {
        String columnName = "id";
        Response response = dbApi.readByColumn(columnName, tableName, cls, id);
        return response;
    }
}
