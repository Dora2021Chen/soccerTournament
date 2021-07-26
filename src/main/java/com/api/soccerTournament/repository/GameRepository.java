package com.api.soccerTournament.repository;

import com.api.soccerTournament.model.Entity;
import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class GameRepository extends BaseRepository implements IBaseRepository {
    protected final TeamRepository teamRepository;

    public GameRepository(DbApi dbApi, TeamRepository teamRepository) {
        super(dbApi);
        this.teamRepository = teamRepository;
    }

    private static final String tableName = "game";
    private static final Class cls = Game.class;

    @Override
    public Response readAll() {
        StringBuilder sqlStrBuilder = new StringBuilder();
        sqlStrBuilder.append("select a.id, roundNo, team1, team2, winner,\n")
                .append("b.name team1Name, c.name team2Name,\n")
                .append("case when winner=team1 then b.name\n")
                .append("\twhen winner=team2 then c.name\n")
                .append("\telse null\n")
                .append("end winnerName\n")
                .append("from game a\n")
                .append("inner join team b on a.team1=b.id\n")
                .append("inner join team c on a.team2=c.id;");

        Response response = dbApi.read(sqlStrBuilder.toString(), cls);
        return response;
    }

    @Override
    public Response readById(Integer id) {
        Response response = readById(id, tableName, cls);
        return response;
    }

    public Response read(Integer roundNo, Integer team1, Integer team2) {
        String filters = "roundNo=? and team1=? and team2=?";
        ArrayList<Object> parameters = new ArrayList<>() {{
            add(roundNo);
            add(Math.min(team1, team2));
            add(Math.max(team1, team2));
        }};

        Response response = dbApi.readByFilters(tableName, cls, filters, parameters);
        return response;
    }

    public Response write(Game game) {
        Response response = teamRepository.readById(game.team1);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }

        if (response.entities.size() == 0) {
            return new Response(Const.statusCodeFailTeamNotExists, game.team1.toString());
        }

        response = teamRepository.readById(game.team2);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }

        if (response.entities.size() == 0) {
            return new Response(Const.statusCodeFailTeamNotExists, game.team2.toString());
        }

        Integer team1 = Math.min(game.team1, game.team2);
        Integer team2 = Math.max(game.team1, game.team2);
        game.team1 = team1;
        game.team2 = team2;

        response = read(game.roundNo, game.team1, game.team2);
        if (response.statusCode != Const.statusCodeSucceed) {
            return response;
        }
        if (response.entities.size() > 0) {
            Entity entity = response.getEntity();
            if ((game.id == null) || (entity.id != game.id)) {
                return new Response(Const.statusCodeFailGameExists);
            }
        }
        response = dbApi.write(Optional.of(game), tableName);
        return response;
    }

    @Override
    public Response delete(Integer id) {
        Response response = dbApi.delete(id, tableName);
        return response;
    }
}
