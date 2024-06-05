package tictactoe.gameold.packet;

import lombok.Data;

@Data
public class GamePlayResponseMessage {

    private final String playerToken;

    private final String gameToken;

    private final int x;

    private final int y;

}
