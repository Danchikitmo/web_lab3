import org.junit.jupiter.api.Test;
import util.AreaChecker;

import static org.junit.jupiter.api.Assertions.*;

public class AreaCheckerTest {

    @Test
    public void testCheckInside() {
        double r = 3.0;

        assertTrue(AreaChecker.checkInside(-1, 1, r));
        assertTrue(AreaChecker.checkInside(0, 3, r));
        assertFalse(AreaChecker.checkInside(-3, 3, r));

        assertTrue(AreaChecker.checkInside(1, 2, r));
        assertTrue(AreaChecker.checkInside(r / 2, r, r));
        assertFalse(AreaChecker.checkInside(r / 2 + 0.1, r, r));
        assertFalse(AreaChecker.checkInside(1, r + 0.1, r));

        assertFalse(AreaChecker.checkInside(-1, -1, r));
        assertFalse(AreaChecker.checkInside(-3, -1.5, r));
        assertFalse(AreaChecker.checkInside(-1, -2, r));
        assertFalse(AreaChecker.checkInside(-3, -3, r));

        assertFalse(AreaChecker.checkInside(1, -1, r));
        assertFalse(AreaChecker.checkInside(2, -2, r));
    }
}
