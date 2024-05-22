package tictactoe.game.packet;

import lombok.Data;

@Data
public class GameConnectMessage {

    private final String gameToken;

}
