package tictactoe.websocket;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import tictactoe.game.GameService;

@Component
@AllArgsConstructor
public class WebsocketDisconnection implements ApplicationListener<SessionDisconnectEvent> {

    private final GameService gameService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        gameService.leaveGame(headerAccessor.getSessionId());
    }

}
