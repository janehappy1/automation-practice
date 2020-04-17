package test;

import org.junit.jupiter.api.Test;

public class DefaultPage extends AbstractTest {

    @Test
    public void openPage() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
