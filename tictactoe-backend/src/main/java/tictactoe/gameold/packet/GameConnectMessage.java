package tictactoe.gameold.packet;

import lombok.Data;

@Data
public class GameConnectMessage {

    private final String gameToken;

}
