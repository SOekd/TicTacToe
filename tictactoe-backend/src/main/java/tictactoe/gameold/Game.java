package tictactoe.gameold;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
@Builder
public class Game {

    private final String gameToken;

    private final int[][] grid = new int[3][3];

    private final Queue<GameMove> playerOneMoves = new LinkedList<>();

    private final Queue<GameMove> playerTwoMoves = new LinkedList<>();

    private final GamePlayer playerOne;

    private final GamePlayer playerTwo;

}
