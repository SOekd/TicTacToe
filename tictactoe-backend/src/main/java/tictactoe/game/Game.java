package tictactoe.game;


import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Arrays;


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

    private String[] otp;

    private boolean publicGame;

    private GamePlayer playerTwo;

    private GamePlayer playerOne;

    private GamePlayer currentPlayer;

    private String[][] board = new String[3][3];


    @Nullable
    public GamePlayer winner;

    private GameState gameState = GameState.WAITING;

    public void setGameState(GameState state){
        switch (state) {
            case RUNNING -> {

            }
        }
    }


//    public void join(GamePlayer gamePlayer) {
//        if (this.player2 == null) {
//            this.player2 = player2;
//            this.started = true;
//        }
//    }
//
//    public void websocketJoin(String player, String playerId) {
//        if (player.equals(player1)) {
//            player1Id = playerId;
//        } else if (player.equals(player2)) {
//            player2Id = playerId;
//        }
//    }
//
//    public void disconnect(String player) {
//        if (isValidPlayer(player)) {
//            disconnect = true;
//        }
//    }
//
//    public void disconnectById(String playerId) {
//        if (isValidPlayerId(playerId)) {
//            disconnect = true;
//        }
//    }
//
//    public void rematch(String player) {
//        if (!disconnect && gameOver() && isValidPlayer(player)) {
//            winner = null;
//            draw = false;
//            startingPlayer = startingPlayer.equals(player1) ? player2 : player1;
//            currentPlayer = startingPlayer;
//
//            resetBoard();
//        }
//    }
//
//    public void makeMove(int x, int y, String player) {
//        if ((x < 0 || x >= 3) || (y < 0 || y >= 3)) return;
//            return;
//
//        if (!isValidPlayer(player)) return;
//
//        if (started && !disconnect && !gameOver()
//                && currentPlayer.equals(player) && board[x][y].isBlank()) {
//            board[x][y] = player.equals(player1) ? "X" : "O";
//            if (checkForWinner("X")) winner = player1;
//            if (checkForWinner("O")) winner = player2;
//            checkForDraw();
//            swapCurrentPlayer();
//        }
//    }
//
//    private boolean checkForWinner(String player) {
//        for (int i = 0; i < 3; i++) {
//            if (checkRow(i, player)) return true;
//        }
//
//        for (int i = 0; i < 3; i++) {
//            if (checkColumn(i, player)) return true;
//        }
//
//        return checkDiagonal(player);
//    }
//
//    private boolean checkRow(int i, String player) {
//        return board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player);
//    }
//
//    private boolean checkColumn(int i, String player) {
//        return board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player);
//    }
//
//    private boolean checkDiagonal(String player) {
//        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) {
//            return true;
//        }
//
//        return board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player);
//    }
//
//    private void checkForDraw() {
//        draw = Arrays.stream(board).flatMap(Arrays::stream).noneMatch(String::isBlank);
//    }
//
//    private void resetBoard() {
//        for (String[] row : board) Arrays.fill(row, BOARD_EMPTY);
//    }
//
//    private void swapCurrentPlayer() {
//        if (currentPlayer.equals(player1)) {
//            currentPlayer = player2;
//        } else {
//            currentPlayer = player1;
//        }
//    }
//
//    private boolean isValidPlayer(String player) {
//        return player.equals(player1) || player.equals(player2);
//    }
//
//    private boolean isValidPlayerId(String playerId) {
//        return playerId.equals(player1Id) || playerId.equals(player2Id);
//    }
//
//    private boolean gameOver() {
//        return winner != null || draw;
//    }

}