package frontend.utils;

public class Delay {

    public static final void delay(Integer milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
