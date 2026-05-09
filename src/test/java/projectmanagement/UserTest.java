package projectmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void userInitialsMustBeOneToFourLetters() {
        assertEquals("huba", new User("huba").getName());
        assertEquals("ANNA", new User("ANNA").getName());

        assertThrows(IllegalArgumentException.class, () -> new User(""));
        assertThrows(IllegalArgumentException.class, () -> new User("HUBAA"));
        assertThrows(IllegalArgumentException.class, () -> new User("HU1"));
    }

    @Test
    public void userInitialComparisonIsCaseInsensitive() {
        assertEquals(new User("huba"), new User("HUBA"));
    }

    @Test
    public void loginInitialsAreCaseInsensitive() throws OperationNotAllowed {
        Main app = new Main();

        app.loginUser("huba");

        assertTrue(app.CheckUserLoggedIn());
    }
}
