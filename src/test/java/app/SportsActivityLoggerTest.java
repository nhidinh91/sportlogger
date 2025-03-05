package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SportsActivityLoggerTest {
    private SportsActivityLogger logger;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testLogActivity() {
        String input = "Football\n60\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.logActivity();

        String output = outputStream.toString();
        assertTrue(output.contains("Activity logged successfully!"));
        assertEquals(1, logger.getActivities().size());
    }

    @Test
    void testViewActivitiesWhenEmpty() {
        logger = new SportsActivityLogger(new Scanner(System.in), true);
        logger.viewActivities();

        String output = outputStream.toString();
        assertTrue(output.contains("No activities logged yet."));
    }

    @Test
    void testViewActivitiesWhenNotEmpty() {
        logger = new SportsActivityLogger(new Scanner(System.in), true);
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Basketball", 45, LocalDate.now()));

        logger.viewActivities();

        String output = outputStream.toString();
        assertTrue(output.contains("Sport: Basketball, Duration: 45 minutes"));
    }

    @Test
    void testCalculateWeeklyTotal() {
        logger = new SportsActivityLogger(new Scanner(System.in), true);
        LocalDate today = LocalDate.now();
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Running", 30, today));
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Swimming", 40, today.minusDays(2)));

        logger.calculateWeeklyTotal();

        String output = outputStream.toString();
        assertTrue(output.contains("Total time spent on sports this week: 70 minutes"));
    }

    @Test
    void testRunExit() {
        String input = "4\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Exiting..."));
    }

    @Test
    void testRunInvalidInput() {
        String input = "INVALID\n4\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Please enter a number."));
    }

    @Test
    void testRunLogActivity() {
        String input = "1\nFootball\n60\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Activity logged successfully!"));

        input = "1\nFootball\nInvalid";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.run();

        output = outputStream.toString();
        assertTrue(output.contains("Invalid input. Duration must be a number."));
    }

    @Test
    void testRunViewActivities() {
        String input = "2\n4\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Basketball", 45, LocalDate.now()));

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Sport: Basketball, Duration: 45 minutes"));
    }

    @Test
    void testRunCalculateWeeklyTotal() {
        String input = "3\n4\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);
        LocalDate today = LocalDate.now();
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Running", 30, today));
        logger.getActivities().add(new SportsActivityLogger.SportsActivity("Swimming", 40, today.minusDays(2)));

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Total time spent on sports this week: 70 minutes"));
    }

    @Test
    void testRunInvalidOption() {
        String input = "5\n4\n";
        logger = new SportsActivityLogger(new Scanner(new ByteArrayInputStream(input.getBytes())), true);

        logger.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice, try again."));
    }

    @Test
    void testSportsActitvityLoggerConstructor() {
        SportsActivityLogger logger = new SportsActivityLogger();
        assertNotNull(logger);
    }
}

