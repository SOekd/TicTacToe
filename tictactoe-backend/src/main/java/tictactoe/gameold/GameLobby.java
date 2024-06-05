package tictactoe.gameold;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameLobby {

    private final int otp;

    private final boolean privateSession;

    private GamePlayer playerOne;

    private GamePlayer playerTwo;

}
