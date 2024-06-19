package tictactoe.game;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.MongoId;
import tictactoe.util.RandomUtils;

import java.time.LocalDateTime;
import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    private static final String BOARD_PLAYER_ONE = "X";
    private static final String BOARD_PLAYER_TWO = "O";
    private static final String BOARD_EMPTY = "";

    @MongoId
    private String id;

    private Long moveCounter;

    @CreatedDate
    private LocalDateTime created;

    private String otp;

    private boolean publicGame;

    private GamePlayer playerTwo;

    private GamePlayer playerOne;

    private GamePlayer currentPlayer;

    private String[][] board = new String[3][3];

    public GamePlayer winner;

    private GameState gameState = GameState.WAITING;

    private List<String> sessions;

    private Map<String, List<GameMove>> lastMoves;

    public void setGameState(GameState state) {
        if (state == GameState.RUNNING) {
            resetBoard();
            currentPlayer = RandomUtils.getRandomElementFromList(List.of(playerOne, playerTwo));
        }

        this.gameState = state;
    }


    public void join(GamePlayer gamePlayer) {
        if (gamePlayer.equals(playerOne) || gamePlayer.equals(playerTwo) || gameState != GameState.WAITING) {
            return;
        }

        if (playerOne == null) {
            playerOne = gamePlayer;
        } else {
            playerTwo = gamePlayer;

            setGameState(GameState.RUNNING);
        }
    }

    public void leave() {
        setGameState(GameState.DISCONNECTED);
    }

    public void makeMove(int x, int y, String playerToken) {
        if (gameState != GameState.RUNNING) {
            return;
        }

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

        val currentLetter = getCurrentLetter();

        board[x][y] = currentLetter;

        lastMoves.computeIfAbsent(currentLetter, k -> new ArrayList<>()).add(new GameMove(x, y));
        deleteLastThirdMove(currentLetter);

        checkState();

        if (gameState == GameState.RUNNING) {
            swapPlayer();
        }
    }

    private void deleteLastThirdMove(String currentLetter) {
        if (lastMoves.containsKey(currentLetter) && lastMoves.get(currentLetter).size() == 4) {
            val move = lastMoves.get(currentLetter).remove(0);

            board[move.getX()][move.getY()] = BOARD_EMPTY;
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

    private String getOppositeLetter(String letter) {
        return letter.equals(BOARD_PLAYER_ONE) ? BOARD_PLAYER_TWO : BOARD_PLAYER_ONE;
    }

    private void swapPlayer() {
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

}