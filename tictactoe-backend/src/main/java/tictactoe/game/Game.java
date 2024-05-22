package tictactoe.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {

    private final String gameToken;

    private final int[][] grid = new int[3][3];

    private final GamePlayer playerOne;

    private final GamePlayer playerTwo;

    public void play() {

    }

}
