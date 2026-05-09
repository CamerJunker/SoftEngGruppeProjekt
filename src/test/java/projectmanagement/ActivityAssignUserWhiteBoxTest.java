package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ActivityAssignUserWhiteBoxTest {

    @Test
    public void assignUserAddsNewUserWhenListIsEmpty() throws Exception {
        Activity activity = new Activity("Programming", 50, new Date(1, 1, 2026), new Date(14, 1, 2026), true);
        Member member = new Member(new User("huba"));

        activity.assignUser(member);

        assertEquals(1, activity.getAssignedUsers().size());
        assertEquals(member, activity.getAssignedUsers().get(0));
    }

    @Test
    public void assignUserRejectsUserAlreadyAssigned() throws Exception {
        Activity activity = new Activity("Programming", 50, new Date(1, 1, 2026), new Date(14, 1, 2026), true);
        Member member = new Member(new User("huba"));

        activity.assignUser(member);

        Exception exception = assertThrows(Exception.class, () -> activity.assignUser(member));
        assertEquals("Employee already assigned", exception.getMessage());
    }

    @Test
    public void assignUserAddsDifferentUserWhenListHasAnotherUser() throws Exception {
        Activity activity = new Activity("Programming", 50, new Date(1, 1, 2026), new Date(14, 1, 2026), true);
        Member firstMember = new Member(new User("anna"));
        Member secondMember = new Member(new User("huba"));

        activity.assignUser(firstMember);
        activity.assignUser(secondMember);

        assertEquals(2, activity.getAssignedUsers().size());
        assertEquals(secondMember, activity.getAssignedUsers().get(1));
    }
}
