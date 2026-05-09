package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProjectRegisterTimeWhiteBoxTest {

    @Test
    public void registerTimeCreatesMemberWhenUserIsNotAssigned() throws Exception {
        Project project = new Project("Project1");
        User user = new User("huba");
        Activity activity = project.createActivity("Programming", 50, new Date(1, 1, 2026), new Date(14, 1, 2026), true);

        project.registerTime(activity, 2.5f, user);

        assertEquals(1, project.getRegisteredMembers().size());
        assertEquals(2.5f, project.getRegisteredActivityTimeForUser(user, activity));
    }

    @Test
    public void registerTimeUsesExistingMemberWhenUserIsAssigned() throws Exception {
        Project project = new Project("Project1");
        User user = new User("huba");
        Activity activity = project.createActivity("Programming", 50, new Date(1, 1, 2026), new Date(14, 1, 2026), true);

        project.assignUser(user);
        project.registerTime(activity, 3.0f, user);

        assertEquals(1, project.getRegisteredMembers().size());
        assertEquals(3.0f, project.getRegisteredActivityTimeForUser(user, activity));
    }
}
