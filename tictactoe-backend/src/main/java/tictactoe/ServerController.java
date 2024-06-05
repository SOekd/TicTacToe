package tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tictactoe.game.Game;
import tictactoe.game.GameRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ServerController {

//	@Autowired
//	private GameRepository repository;
//
//	@Autowired
//	private SimpMessagingTemplate template;
//
//	@RequestMapping(method = RequestMethod.GET, value = "/ttt/games")
//	public List<AvailableGame> getGames() {
//		List<AvailableGame> available = new ArrayList<AvailableGame>();
//		List<Game> games = repository.findByStartedAndDisconnect(false, false);
//
//		for(Game game : games) {
//			available.add(new AvailableGame(game.id, game.name));
//		}
//
//		return available;
//	}
//
//	@RequestMapping(method = RequestMethod.POST, value = "/ttt/game")
//	public Game createGame(
//			@RequestParam(value = "player") String player,
//			@RequestParam(value = "name", defaultValue="A TicTacToe Game") String name) {
//
//		Game game = new Game(player, name);
//		repository.save(game);
//
//		return game;
//	}
//
//	// Join
//	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player"})
//	public Game joinGame(
//			@RequestParam(value = "id") String id,
//			@RequestParam(value = "player") String player
//			) {
//
//		Game game = repository.findById(id);
//
//		if (!game.started && !game.disconnect) {
//			game.join(player);
//
//			repository.save(game);
//			updateGameState(id, game);
//
//			return game;
//		}
//
//		// return null if third player is trying to join or player left
//		return null;
//	}
//
//	// Disconnect
//	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player", "disconnect"})
//	public Game disconnectGame(
//			@RequestParam(value = "id") String id,
//			@RequestParam(value = "player") String player,
//			@RequestParam(value = "disconnect") boolean disconnect) {
//
//		Game game = repository.findById(id);
//
//		if (disconnect) {
//			game.disconnect(player);
//			repository.save(game);
//
//			updateGameState(id, game);
//
//			return game;
//		}
//
//		return null;
//	}
//
//	// Rematch
//	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player", "rematch"})
//	public Game rematchGame(
//			@RequestParam(value = "id") String id,
//			@RequestParam(value = "player") String player,
//			@RequestParam(value = "rematch") boolean rematch) {
//
//		Game game = repository.findById(id);
//
//		if (rematch) {
//			game.rematch(player);
//
//			repository.save(game);
//			updateGameState(id, game);
//
//			return game;
//		}
//
//		return null;
//	}
//
//	// push new game state to websocket
//	private void updateGameState(String id, Game game) {
//		template.convertAndSend("/ttt/gamestate/" + id, game);
//	}

}
