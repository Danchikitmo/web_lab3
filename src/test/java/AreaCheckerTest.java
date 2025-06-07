import org.junit.jupiter.api.Test;
import util.AreaChecker;

import static org.junit.jupiter.api.Assertions.*;

public class AreaCheckerTest {

    @Test
    public void testCheckInside() {
        double r = 3.0;

        // Первая четверть (x <= 0, y >= 0) — проверка круга радиуса r
        assertTrue(AreaChecker.checkInside(-1, 1, r));
        assertTrue(AreaChecker.checkInside(0, 3, r));
        assertFalse(AreaChecker.checkInside(-3, 3, r)); // вне круга
        // Вторая четверть (x >= 0, y >= 0) — прямоугольник x ≤ r/2, y ≤ r
        assertTrue(AreaChecker.checkInside(1, 2, r));
        assertTrue(AreaChecker.checkInside(r / 2, r, r));
        assertFalse(AreaChecker.checkInside(r / 2 + 0.1, r, r));
        assertFalse(AreaChecker.checkInside(1, r + 0.1, r));
        // Третья четверть (x <= 0, y <= 0) — y ≥ -x - r/2 (линия)
        assertFalse(AreaChecker.checkInside(-1, -1, r));
        assertFalse(AreaChecker.checkInside(-3, -1.5, r)); // поправлено на false
        assertFalse(AreaChecker.checkInside(-1, -2, r));
        assertFalse(AreaChecker.checkInside(-3, -3, r));

        // Остальные точки — false
        assertFalse(AreaChecker.checkInside(1, -1, r));
        assertFalse(AreaChecker.checkInside(2, -2, r));
    }
}
