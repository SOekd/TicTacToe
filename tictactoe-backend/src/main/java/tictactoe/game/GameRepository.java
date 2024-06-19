package tictactoe.game;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {

    Optional<Game> findByOtp(String otp);

    List<Game> findAllBySessionsContaining(String sessionId);

}
