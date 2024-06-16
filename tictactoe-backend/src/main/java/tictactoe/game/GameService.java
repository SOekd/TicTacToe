package tictactoe.game;

import org.springframework.http.ResponseEntity;
import tictactoe.game.packet.PlayerJoinMessage;
import tictactoe.game.packet.PlayerMoveMessage;
import tictactoe.game.request.GameCreateRequest;
import tictactoe.game.response.GameResponse;
import tictactoe.game.response.TokenResponse;
import tictactoe.game.response.ValidTokenResponse;

public interface GameService {

    ResponseEntity<TokenResponse> createPlayerToken();

    ResponseEntity<ValidTokenResponse> validatePlayerToken(String token);

    ResponseEntity<GameResponse> createGame(GameCreateRequest request);

    ResponseEntity<GameResponse> findGameByOtp(String otp);

    GameResponse makeMove(String gameId, PlayerMoveMessage move);

    GameResponse joinGame(String gameId, PlayerJoinMessage join);

}
