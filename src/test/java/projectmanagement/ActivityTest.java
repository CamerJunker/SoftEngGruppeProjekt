package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class ActivityTest {
    @Test
    public void activityStoresStartAndEndDates() {
        Date startDate = new Date(1, 1, 2026);
        Date endDate = new Date(14, 1, 2026);

        Activity activity = new Activity("Programming", 50, startDate, endDate, true);

        assertSame(startDate, activity.getStartDate());
        assertSame(endDate, activity.getEndDate());
    }
}
