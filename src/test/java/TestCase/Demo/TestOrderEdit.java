package TestCase.Demo;

import org.testng.annotations.Test;

public class TestOrderEdit {
    @Test(groups={"OrderEdit", "save"})
    public void testEditOrder() {
        System.out.println("testEditOrder");
    }

    @Test(groups={"OrderEdit", "save"})
    public void testEditEmptyOrder() {
        System.out.println("testEditEmptyOrder");
    }

    @Test(groups="OrderEdit")
    public void testUpdateEditOrder() {
        System.out.println("testUpdateEditOrder");
    }

    @Test(groups="OrderEdit")
    public void testFindEditOrder() {
        System.out.println("testFindEditOrder");
    }
}
