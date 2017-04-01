package agg.samples.commands.util;

public class DelayUtil {
    public static void addDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
