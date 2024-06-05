package tictactoe.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponse {

    public static GameResponse parse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .created(game.getCreated())
                .otp(game.getOtp())
                .publicGame(game.isPublicGame())
                .playerOne(game.getPlayerOne().getName())
                .playerTwo(game.getPlayerTwo().getName())
                .currentPlayer(game.getCurrentPlayer().getName())
                .board(game.getBoard())
                .gameState(game.getGameState())
                .build();
    }

    @MongoId
    private String id;

    @CreatedDate
    private LocalDateTime created;

    private String[] otp;

    private boolean publicGame;

    private String playerOne;

    private String playerTwo;

    private String currentPlayer;

    private String[][] board = new String[3][3];

    private GameState gameState;

}
