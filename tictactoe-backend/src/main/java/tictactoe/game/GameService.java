package tictactoe.game;

public interface GameService {

    String createToken();

    GameResponse createGame(GameCreateRequest request);



}
