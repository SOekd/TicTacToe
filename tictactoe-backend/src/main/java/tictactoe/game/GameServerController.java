package tictactoe.game;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tictactoe.game.request.GameCreateRequest;
import tictactoe.game.response.GameResponse;
import tictactoe.game.response.TokenResponse;
import tictactoe.game.response.ValidTokenResponse;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameServerController {

    private final GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<GameResponse> createGame(@RequestBody GameCreateRequest gameCreateRequest) {
        return gameService.createGame(gameCreateRequest);
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token() {
        return gameService.createPlayerToken();
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<ValidTokenResponse> token(@PathVariable String token) {
        return gameService.validatePlayerToken(token);
    }


}
