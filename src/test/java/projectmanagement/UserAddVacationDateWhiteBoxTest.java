package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UserAddVacationDateWhiteBoxTest {

    @Test
    public void addVacationDateAddsDateWhenItIsNotRegistered() throws Exception {
        User user = new User("huba");
        Date vacationDate = new Date(1, 7, 2026);

        user.addVacationDate(vacationDate);

        assertEquals(1, user.getVacationDates().size());
        assertEquals(vacationDate, user.getVacationDates().get(0));
    }

    @Test
    public void addVacationDateRejectsDateAlreadyRegistered() throws Exception {
        User user = new User("huba");
        Date vacationDate = new Date(1, 7, 2026);

        user.addVacationDate(vacationDate);

        Exception exception = assertThrows(Exception.class, () -> user.addVacationDate(vacationDate));
        assertEquals("Vacation day already registered", exception.getMessage());
    }
}
