package com.api.soccerTournament.controller;

import com.api.soccerTournament.model.Game;
import com.api.soccerTournament.model.response.Const;
import com.api.soccerTournament.model.response.Response;
import com.api.soccerTournament.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccerTournament/game")
@CrossOrigin
public class GameController implements IController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(path = "/getAll", produces = Const.RESPONSE_FORMAT)
    public Response<Game> readAll() {
        Response response = gameService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.RESPONSE_FORMAT)
    @Override
    public Response<Game> readById(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = gameService.readById(id);
        return response;
    }

    @PutMapping(path = "/setGameResult", produces = Const.RESPONSE_FORMAT)
    public Response setGameResult(@RequestParam Integer id, @RequestParam Integer winner) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (winner == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "winner");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");
        if (winner <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "winner");

        Response response = gameService.setGameResult(id, winner);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.RESPONSE_FORMAT)
    public Response write(@RequestBody Game game) {
        if (game == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "game");
        if (game.roundNo == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "game.roundNo");
        if (game.team1 == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "game.team1");
        if (game.team2 == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "game.team2");
        if (game.roundNo <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.roundNo");
        if (game.roundNo >= Const.MAX_ROUND_NO)
            return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.roundNo");
        if (game.team1 <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.team1");
        if (game.team2 <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.team2");
        if (game.team1.equals(game.team2))
            return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.team1==game.team2");
        if (game.winner != null) {
            if (!game.winner.equals(game.team1) && !game.winner.equals(game.team2)) {
                return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "game.winner");
            }
        }

        Response response = gameService.write(game);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.RESPONSE_FORMAT)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.STATUS_CODE_FAIL_PARAM_NULL, "id");
        if (id <= 0) return new Response(Const.STATUS_CODE_FAIL_PARAM_INVALID, "id");

        Response response = gameService.delete(id);
        return response;
    }
}

