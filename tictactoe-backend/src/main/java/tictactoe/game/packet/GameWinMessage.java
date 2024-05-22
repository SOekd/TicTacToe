package tictactoe.game.packet;

import lombok.Data;

@Data
public class GameWinMessage {

    private final String playerId;

}
