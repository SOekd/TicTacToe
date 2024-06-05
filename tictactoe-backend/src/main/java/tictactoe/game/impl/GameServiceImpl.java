package tictactoe.game.impl;

import tictactoe.game.GameCreateRequest;
import tictactoe.game.GameResponse;
import tictactoe.game.GameService;

import java.util.UUID;

public class GameServiceImpl implements GameService {

    @Override
    public String createToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public GameResponse createGame(GameCreateRequest request) {
        return null;
    }

}
