package tictactoe.game.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameCreateRequest {

    private boolean publicGame;

}