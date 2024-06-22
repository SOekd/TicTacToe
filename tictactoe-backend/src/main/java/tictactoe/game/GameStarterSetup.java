package tictactoe.game;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GameStarterSetup {

    private final GameRepository gameRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void deleteGamesOnStartup() {
        gameRepository.deleteAll();
    }
}
