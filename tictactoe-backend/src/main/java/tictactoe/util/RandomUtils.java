package tictactoe.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.List;

@UtilityClass
public class RandomUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    public String getRandomString(int minimumLength, int maximumLength) {
        if (minimumLength < 0 || maximumLength < 0 || minimumLength > maximumLength) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = RANDOM.nextInt(maximumLength - minimumLength + 1) + minimumLength;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) (RANDOM.nextInt(26) + 'A'));
        }
        return stringBuilder.toString();
    }

    public <T> T getRandomElementFromList(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List is empty");
        }
        return list.get(RANDOM.nextInt(list.size()));
    }

}
