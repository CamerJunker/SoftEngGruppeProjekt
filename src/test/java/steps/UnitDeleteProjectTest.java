package steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Whitebox test: Delete Project Steps")
public class UnitDeleteProjectTest {

    @BeforeEach
    public void setUp(){
        System.out.println("TEST");
    }

    @Test
    @DisplayName("Test 1")
    public void test(){
        System.out.println("TESTING");
        assertTrue(true, "Testing test");
    }
}
