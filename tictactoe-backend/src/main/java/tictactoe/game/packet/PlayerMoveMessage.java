package tictactoe.game.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerMoveMessage {

    private String playerToken;

    private int x;

    private int y;

}
