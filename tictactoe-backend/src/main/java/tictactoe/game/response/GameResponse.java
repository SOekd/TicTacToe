package tictactoe.game.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tictactoe.game.Game;
import tictactoe.game.GamePlayer;
import tictactoe.game.GameState;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameResponse {

    public static GameResponse parseResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .created(game.getCreated())
                .otp(game.getOtp())
                .publicGame(game.isPublicGame())
                .playerOne(game.getPlayerOne() == null ? null : game.getPlayerOne().getName())
                .playerTwo(game.getPlayerTwo() == null ? null : game.getPlayerTwo().getName())
                .winner(game.getWinner() == null ? null : game.getWinner().getName())
                .currentPlayer(game.getCurrentPlayer() == null ? null : game.getCurrentPlayer().getName())
                .board(game.getBoard())
                .gameState(game.getGameState())
                .build();
    }

    private String id;

    private LocalDateTime created;

    private String otp;

    private boolean publicGame;

    private String playerOne;

    private String playerTwo;

    private String winner;

    private String currentPlayer;

    private String[][] board = new String[3][3];

    private GameState gameState;

}
