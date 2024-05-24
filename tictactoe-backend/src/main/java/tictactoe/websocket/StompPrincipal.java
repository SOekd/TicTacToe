package tictactoe.websocket;

import lombok.Data;

import java.security.Principal;

@Data
public class StompPrincipal implements Principal {

    private final String name;

}
