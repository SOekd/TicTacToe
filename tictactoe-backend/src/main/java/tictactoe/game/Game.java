package tictactoe.game;


import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;
import tictactoe.util.RandomUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    private static final String BOARD_PLAYER_ONE = "X";
    private static final String BOARD_PLAYER_TWO = "Y";
    private static final String BOARD_EMPTY = "";

    @MongoId
    public String id;

    @CreatedDate
    private LocalDateTime created;

    private String otp;

    private boolean publicGame;

    private GamePlayer playerTwo;

    private GamePlayer playerOne;

    private GamePlayer currentPlayer;

    private String[][] board = new String[3][3];


    @Nullable
    public GamePlayer winner;

    private GameState gameState = GameState.WAITING;

    public void setGameState(GameState state) {
        if (state == GameState.RUNNING) {
            resetBoard();
            currentPlayer = RandomUtils.getRandomElementFromList(List.of(playerOne, playerTwo));
        }

        this.gameState = state;
    }


    public void join(GamePlayer gamePlayer) {
        if (gamePlayer.equals(playerOne) || gamePlayer.equals(playerTwo)) {
            return;
        }

        if (playerOne == null) {
            playerOne = gamePlayer;
        } else {
            playerTwo = gamePlayer;
        }
    }

    public void leave(GamePlayer gamePlayer) {
        if (gamePlayer.equals(playerOne)) {
            playerOne = null;
        } else if (gamePlayer.equals(playerTwo)) {
            playerTwo = null;
        }

        if (gameState == GameState.WAITING)
            return;

        setGameState(GameState.DISCONNECTED);
    }

    public void makeMove(int x, int y, String playerToken) {
        if ((x < 0 || x >= 3) || (y < 0 || y >= 3)) {
            return;
        }

        if (!isCurrentPlayer(playerToken)) {
            return;
        }

        if (gameState != GameState.RUNNING) {
            return;
        }

        if (!board[x][y].isBlank()) {
            return;
        }

        board[x][y] = getCurrentLetter();

        checkState();
        if (gameState == GameState.RUNNING) {
            swapPlayer();
        }
    }

    private String getCurrentLetter() {
        return currentPlayer.equals(playerOne) ? BOARD_PLAYER_ONE : BOARD_PLAYER_TWO;
    }

    private String getLetter(GamePlayer player) {
        return player.equals(playerOne) ? BOARD_PLAYER_ONE : BOARD_PLAYER_TWO;
    }

    private void checkState() {

        if (hasWinner().isPresent()) {
            gameState = GameState.FINISHED;
            winner = hasWinner().get();
            return;
        }

        if (isDraw()) {
            gameState = GameState.DRAW;
        }

    }

    private Optional<GamePlayer> hasWinner() {
        if (isWinner(playerOne)) {
            return Optional.of(playerOne);
        }

        if (isWinner(playerTwo)) {
            return Optional.of(playerTwo);
        }

        return Optional.empty();
    }

    private boolean isWinner(GamePlayer player) {

        if (checkRow(getLetter(player))) {
            return true;
        }

        if (checkColumn(getLetter(player))) {
            return true;
        }

        return checkDiagonal(getLetter(player));
    }

    private boolean isDraw() {
        return Arrays.stream(board).flatMap(Arrays::stream).noneMatch(String::isBlank);
    }

    private boolean checkRow(String letter) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(letter) && board[i][1].equals(letter) && board[i][2].equals(letter)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumn(String letter) {
        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(letter) && board[1][i].equals(letter) && board[2][i].equals(letter)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonal(String letter) {
        if (board[0][0].equals(letter) && board[1][1].equals(letter) && board[2][2].equals(letter))
            return true;


        return board[0][2].equals(letter) && board[1][1].equals(letter) && board[2][0].equals(letter);
    }


    private void resetBoard() {
        for (String[] row : board) {
            Arrays.fill(row, BOARD_EMPTY);
        }
    }


    private boolean isCurrentPlayer(String playerToken) {
        return currentPlayer.getToken().equals(playerToken);
    }

    private void swapPlayer() {
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

}