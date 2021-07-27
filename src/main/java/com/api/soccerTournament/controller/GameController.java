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

    @GetMapping(path = "/getAll", produces = Const.responseFormat)
    public Response<Game> readAll() {
        Response response = gameService.readAll();
        return response;
    }

    @GetMapping(path = "/getById", produces = Const.responseFormat)
    @Override
    public Response<Game> readById(@RequestParam Integer id) {
        Response response = gameService.readById(id);
        return response;
    }

    @PutMapping(path = "/setGameResult", produces = Const.responseFormat)
    public Response<Game> setGameResult(@RequestParam Integer id, @RequestParam Integer winner) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (winner == null) return new Response(Const.statusCodeFailParamNull, "winner");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");
        if (winner <= 0) return new Response(Const.statusCodeFailParamInvalid, "winner");

        Response response = gameService.setGameResult(id, winner);
        return response;
    }

    @PostMapping(path = "/write", produces = Const.responseFormat)
    public Response write(@RequestBody Game game) {
        if (game == null) return new Response(Const.statusCodeFailParamNull, "game");
        if (game.roundNo == null) return new Response(Const.statusCodeFailParamNull, "game.roundNo");
        if (game.team1 == null) return new Response(Const.statusCodeFailParamNull, "game.team1");
        if (game.team2 == null) return new Response(Const.statusCodeFailParamNull, "game.team2");
        if (game.roundNo <= 0) return new Response(Const.statusCodeFailParamInvalid, "game.roundNo");
        if (game.team1 <= 0) return new Response(Const.statusCodeFailParamInvalid, "game.team1");
        if (game.team2 <= 0) return new Response(Const.statusCodeFailParamInvalid, "game.team2");
        if (game.team1 == game.team2) return new Response(Const.statusCodeFailParamInvalid, "game.team1==game.team2");
        if ((game.winner != null) && ((game.winner != game.team1) & (game.winner != game.team2)))
            return new Response(Const.statusCodeFailParamInvalid, "game.winner");

        Response response = gameService.write(game);
        return response;
    }

    @DeleteMapping(path = "/delete", produces = Const.responseFormat)
    public Response delete(@RequestParam Integer id) {
        if (id == null) return new Response(Const.statusCodeFailParamNull, "id");
        if (id <= 0) return new Response(Const.statusCodeFailParamInvalid, "id");

        Response response = gameService.delete(id);
        return response;
    }
}

