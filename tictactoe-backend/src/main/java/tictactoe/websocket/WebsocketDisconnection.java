package tictactoe.websocket;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import tictactoe.game.GameRepository;
import tictactoe.game.GameService;

@Component
@AllArgsConstructor
public class WebsocketDisconnection implements ApplicationListener<SessionDisconnectEvent> {

    private final GameRepository gameRepository;

    private final GameService gameService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        val games = gameRepository.findAllBySessionsContainingIgnoreCase(headerAccessor.getSessionId());

        games.forEach(game -> {
            game.getSessions().remove(headerAccessor.getSessionId());
            gameRepository.save(game);
        });
    }

}
