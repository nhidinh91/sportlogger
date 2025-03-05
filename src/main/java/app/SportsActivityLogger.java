package app;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SportsActivityLogger {
    public static class SportsActivity {
        String sport;
        int duration; // in minutes
        LocalDate date;

        SportsActivity(String sport, int duration, LocalDate date) {
            this.sport = sport;
            this.duration = duration;
            this.date = date;
        }

        @Override
        public String toString() {
            return "Sport: " + sport + ", Duration: " + duration + " minutes, Date: " + date;
        }
    }

    private final List<SportsActivity> activities = new ArrayList<>();
    private final Scanner scanner;
    private boolean testMode = false;

    public SportsActivityLogger() {
        this.scanner = new Scanner(System.in);
    }

    // Constructor for testing
    public SportsActivityLogger(Scanner scanner, boolean testMode) {
        this.scanner = scanner;
        this.testMode = testMode;
    }

    public List<SportsActivity> getActivities() {
        return activities;
    }

    public void logActivity() {
        System.out.print("Enter sport name: ");
        String sport = scanner.nextLine();

        System.out.print("Enter duration (minutes): ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Duration must be a number.");
            scanner.nextLine();
            return;
        }
        int duration = scanner.nextInt();
        scanner.nextLine();

        LocalDate date = LocalDate.now();
        activities.add(new SportsActivity(sport, duration, date));
        System.out.println("Activity logged successfully!\n");
    }

    public void viewActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities logged yet.");
            return;
        }
        System.out.println("Logged Activities:");
        for (SportsActivity activity : activities) {
            System.out.println(activity);
        }
    }

    public void calculateWeeklyTotal() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        int totalMinutes = activities.stream()
                .filter(activity -> !activity.date.isBefore(startOfWeek))
                .mapToInt(activity -> activity.duration)
                .sum();

        System.out.println("Total time spent on sports this week: " + totalMinutes + " minutes");
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Log Activity");
            System.out.println("2. View Activities");
            System.out.println("3. Calculate Weekly Total");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> logActivity();
                case 2 -> viewActivities();
                case 3 -> calculateWeeklyTotal();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }

            if (testMode) break;  // Exit loop in test mode
        }
    }

    public static void main(String[] args) {
        new SportsActivityLogger().run();
    }
}
