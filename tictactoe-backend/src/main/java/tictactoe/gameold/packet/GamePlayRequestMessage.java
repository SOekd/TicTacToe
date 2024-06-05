package tictactoe.gameold.packet;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class GamePlayRequestMessage {

    private final String playerId;

    private final ZonedDateTime until;

    private final int[][] currentState;

}
