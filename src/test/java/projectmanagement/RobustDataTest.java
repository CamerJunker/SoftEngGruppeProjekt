package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class RobustDataTest {
    @Test
    public void projectNamesAreTrimmedAndComparedCaseInsensitively() throws OperationNotAllowed {
        Main app = new Main();

        app.NewProject("  ProjectX  ");

        assertTrue(app.searchProject("projectx"));

        OperationNotAllowed exception = assertThrows(OperationNotAllowed.class, () -> app.NewProject("PROJECTX"));
        assertEquals("Project name already exists", exception.getMessage());
    }

    @Test
    public void blankProjectNamesAreRejected() {
        Main app = new Main();

        OperationNotAllowed exception = assertThrows(OperationNotAllowed.class, () -> app.NewProject("   "));
        assertEquals("Project name is required", exception.getMessage());
        assertFalse(app.searchProject(null));
    }

    @Test
    public void duplicateActivityCreationReturnsExistingActivity() {
        Project project = new Project("Project1");
        Activity firstActivity = project.createActivity("Development", 10, new Date(1, 1, 2026), new Date(2, 1, 2026), true);

        Activity secondActivity = project.createActivity(" development ", 20, new Date(3, 1, 2026), new Date(4, 1, 2026), false);

        assertSame(firstActivity, secondActivity);
        assertEquals(1, project.getActivityList().size());
    }

    @Test
    public void invalidDatesAndReversedActivityDatesAreRejected() {
        assertThrows(DateTimeException.class, () -> new Date(31, 2, 2026));

        Project project = new Project("Project1");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> project.createActivity("Bad dates", 1, new Date(10, 1, 2026), new Date(9, 1, 2026), true));
        assertEquals("Activity start date cannot be after end date", exception.getMessage());
    }

    @Test
    public void nonPositiveAndNonFiniteHoursAreRejected() throws Exception {
        Project project = new Project("Project1");
        User user = new User("HUBA");
        Activity activity = project.createActivity("Development", 10, new Date(1, 1, 2026), new Date(2, 1, 2026), true);

        Exception zeroHours = assertThrows(Exception.class, () -> project.registerTime(activity, 0, user));
        Exception negativeHours = assertThrows(Exception.class, () -> project.registerTime(activity, -1, user));
        Exception infiniteHours = assertThrows(Exception.class, () -> project.registerTime(activity, Float.POSITIVE_INFINITY, user));

        assertEquals("Hours must be greater than zero", zeroHours.getMessage());
        assertEquals("Hours must be greater than zero", negativeHours.getMessage());
        assertEquals("Hours must be greater than zero", infiniteHours.getMessage());
    }

    @Test
    public void duplicateProjectMembersAreIgnored() {
        Project project = new Project("Project1");
        User user = new User("HUBA");

        project.assignUser(user);
        project.assignUser(new User("huba"));

        assertEquals(1, project.getRegisteredMembers().size());
    }
}
