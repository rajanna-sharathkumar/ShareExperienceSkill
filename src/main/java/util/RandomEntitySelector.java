package util;

import java.util.Random;

public class RandomEntitySelector {
    public static String getRandomObject(String[] strings) {
        final int index = new Random().nextInt(strings.length);
        return strings[index];
    }
}
