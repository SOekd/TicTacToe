package tictactoe.game.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinMessage {

    private String playerName;

    private String token;

}
