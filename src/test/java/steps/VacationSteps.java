package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import projectmanagement.Date;
import projectmanagement.User;

public class VacationSteps {
    private User user;
    private String errorMessage = "";

    @Given("a vacation employee with initials {string}")
    public void aVacationEmployeeWithInitials(String initials) {
        this.user = new User(initials);
        assertEquals(initials, this.user.getName());
    }

    @Given("the employee has vacation on {int}-{int}-{int}")
    public void theEmployeeHasVacationOn(Integer year, Integer month, Integer day) {
        addVacationDate(createDate(year, month, day));
        assertTrue(hasVacationDate(year, month, day));
    }

    @When("the employee registers vacation on {int}-{int}-{int}")
    public void theEmployeeRegistersVacationOn(Integer year, Integer month, Integer day) {
        try {
            addVacationDate(createDate(year, month, day));
        } catch (RuntimeException exception) {
            this.errorMessage = exception.getMessage();
        }
    }

    @When("the employee removes vacation on {int}-{int}-{int}")
    public void theEmployeeRemovesVacationOn(Integer year, Integer month, Integer day) {
        try {
            removeVacationDate(createDate(year, month, day));
        } catch (RuntimeException exception) {
            this.errorMessage = exception.getMessage();
        }
    }

    @Then("the employee should have vacation on {int}-{int}-{int}")
    public void theEmployeeShouldHaveVacationOn(Integer year, Integer month, Integer day) {
        assertTrue(hasVacationDate(year, month, day));
    }

    @Then("the employee should not have vacation on {int}-{int}-{int}")
    public void theEmployeeShouldNotHaveVacationOn(Integer year, Integer month, Integer day) {
        assertFalse(hasVacationDate(year, month, day));
    }

    @Then("the vacation error message {string} is given")
    public void theVacationErrorMessageIsGiven(String expectedMessage) {
        assertEquals(expectedMessage, this.errorMessage);
    }

    private Date createDate(int year, int month, int day) {
        LocalDate.of(year, month, day);

        Date date = new Date();
        date.year = year;
        date.month = month;
        date.day = day;
        return date;
    }

    private void addVacationDate(Date date) {
        invokeVacationMethod("addVacationDate", date);
    }

    private void removeVacationDate(Date date) {
        invokeVacationMethod("removeVacationDate", date);
    }

    private void invokeVacationMethod(String methodName, Date date) {
        try {
            Method method = User.class.getMethod(methodName, Date.class);
            method.invoke(this.user, date);
        } catch (NoSuchMethodException exception) {
            fail("Expected User." + methodName + "(Date) to be implemented");
        } catch (IllegalAccessException exception) {
            fail("Expected User." + methodName + "(Date) to be public");
        } catch (InvocationTargetException exception) {
            throw new RuntimeException(exception.getCause().getMessage());
        }
    }

    private boolean hasVacationDate(int year, int month, int day) {
        for (Date vacationDate : getVacationDates()) {
            if (vacationDate.year == year && vacationDate.month == month && vacationDate.day == day) {
                return true;
            }
        }
        return false;
    }

    private List<Date> getVacationDates() {
        ArrayList<Date> vacationDates = this.user.getVacationDates();
        if (vacationDates == null) {
            fail("Expected User.getVacationDates() to return an initialized list");
        }
        return vacationDates;
    }
}
