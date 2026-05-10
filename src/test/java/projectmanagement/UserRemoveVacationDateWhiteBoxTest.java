package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UserRemoveVacationDateWhiteBoxTest {

    @Test
    public void removeVacationDateRemovesDateWhenItIsRegistered() throws Exception {
        User user = new User("huba");
        Date vacationDate = new Date(1, 7, 2026);

        user.addVacationDate(vacationDate);
        user.removeVacationDate(vacationDate);

        assertEquals(0, user.getVacationDates().size());
    }

    @Test
    public void removeVacationDateRejectsDateThatIsNotRegistered() throws Exception {
        User user = new User("huba");
        Date vacationDate = new Date(1, 7, 2026);

        Exception exception = assertThrows(Exception.class, () -> user.removeVacationDate(vacationDate));

        assertEquals("Vacation day not registered", exception.getMessage());
    }
}
