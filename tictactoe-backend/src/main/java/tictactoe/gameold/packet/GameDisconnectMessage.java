package tictactoe.gameold.packet;

import lombok.Data;

@Data
public class GameDisconnectMessage {

    private final String playerId;

}
