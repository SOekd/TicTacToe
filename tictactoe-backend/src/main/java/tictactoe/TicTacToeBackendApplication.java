package tictactoe;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import tictactoe.game.GameRepository;

@SpringBootApplication
@AllArgsConstructor
public class TicTacToeBackendApplication   {

    private final GameRepository gameRepository;

    public static void main(String[] args) {
        SpringApplication.run(TicTacToeBackendApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void deleteGamesOnStartup() {
        gameRepository.deleteAll();
    }
}
