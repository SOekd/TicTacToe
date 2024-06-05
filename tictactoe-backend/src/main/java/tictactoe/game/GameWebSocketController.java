package tictactoe.game;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tictactoe.game.packet.GameJoinMessage;
import tictactoe.game.packet.PlayerMoveMessage;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {

    private final GameRepository gameRepository;

    @MessageMapping("/move/{id}")
    @SendTo("/game-response/move/{id}")
    public GameResponse onGameMove(@DestinationVariable String id, PlayerMoveMessage move)  {
        return null;
    }

    @MessageMapping("/join/{id}")
    @SendTo("/game-response/join/{id}")
    public GameResponse onGameJoin(@DestinationVariable String id, GameJoinMessage move)  {
        return null;
    }

}
