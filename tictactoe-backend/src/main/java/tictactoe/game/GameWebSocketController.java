package tictactoe.game;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tictactoe.game.packet.PlayerJoinMessage;
import tictactoe.game.packet.PlayerMoveMessage;
import tictactoe.game.response.GameResponse;

import java.sql.SQLOutput;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {

    private final GameService gameService;

    @MessageMapping("/move/{id}")
    @SendTo("/response/move/{id}")
    public GameResponse onGameMove(@DestinationVariable String id, PlayerMoveMessage move)  {
        return gameService.makeMove(id, move);
    }

    @MessageMapping("/join/{id}")
    @SendTo("/response/join/{id}")
    public GameResponse onGameJoin(@DestinationVariable String id, @Header("simpSessionId") String sessionId, PlayerJoinMessage move)  {
        return gameService.joinGame(sessionId, id, move);
    }

}
