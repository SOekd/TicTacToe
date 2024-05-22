package tictactoe.game.packet;

import lombok.Data;

@Data
public class GameDisconnectMessage {

    private final String playerId;

}
