package tictactoe.game.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tictactoe.game.*;
import tictactoe.game.exception.GameNotFoundException;
import tictactoe.game.packet.PlayerJoinMessage;
import tictactoe.game.packet.PlayerMoveMessage;
import tictactoe.game.request.GameCreateRequest;
import tictactoe.game.response.GameResponse;
import tictactoe.game.response.TokenResponse;
import tictactoe.game.response.ValidTokenResponse;
import tictactoe.util.RandomUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private static final String TOKEN_SIGNATURE = "gOkOxWO5A4f5h86W1HEuVZYioms8op61";

    private final GameRepository gameRepository;

    @Override
    public ResponseEntity<TokenResponse> createPlayerToken() {
        return ResponseEntity.ok(new TokenResponse(getNewToken()));
    }

    @Override
    public ResponseEntity<ValidTokenResponse> validatePlayerToken(String token) {

        try {
            JWT.require(getAlgorithm())
                    .withIssuer("tictactoe")
                    .withSubject("player")
                    .build()
                    .verify(token);
            return ResponseEntity.ok(new ValidTokenResponse(token));
        } catch (JWTVerificationException exception) {
            return ResponseEntity.ok(new ValidTokenResponse(getNewToken()));
        }

    }

    @Override
    public ResponseEntity<GameResponse> createGame(GameCreateRequest request) {
        val gameBuilder = Game.builder()
                .gameState(GameState.WAITING)
                .created(LocalDateTime.now())
                .publicGame(request.isPublicGame());

        if (request.isPublicGame()) {
            gameBuilder.otp(RandomUtils.getRandomString(6, 6));
        }

        val game = gameRepository.save(gameBuilder.build());

        System.out.println("Games:");
        for (Game game1 : gameRepository.findAll()) {
            System.out.println("Game: " + game1.getId());
        }

        return ResponseEntity.ok(GameResponse.parseResponse(game));
    }

    @Override
    public ResponseEntity<GameResponse> findGameByOtp(String otp) {
        val game = gameRepository.findByOtp(otp)
                .orElseThrow(() -> new GameNotFoundException("Game not found"));

        return ResponseEntity.ok(GameResponse.parseResponse(game));
    }

    @Override
    public GameResponse makeMove(String gameId, PlayerMoveMessage move) {

        val game = gameRepository.findById(gameId).orElse(null);

        if (game == null)
            return null;

        game.makeMove(move.getX(), move.getY(), move.getPlayerToken());

        return GameResponse.parseResponse(game);
    }

    @Override
    public GameResponse joinGame(String gameId, PlayerJoinMessage join) {
        val game = gameRepository.findById(gameId).orElse(null);

        if (game == null)
            return null;

        game.join(new GamePlayer(join.getPlayerName(), join.getToken()));

        return GameResponse.parseResponse(game);
    }

    private String getNewToken() {
        return JWT.create()
                .withIssuer("tictactoe")
                .withSubject("player")
                .withJWTId(UUID.randomUUID().toString())
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(TOKEN_SIGNATURE);
    }

}
