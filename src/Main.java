import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Date implements Comparable<Date> {
    private int day;
    private int month;
    private int year;

    // Constructor: Initializes a Date object after checking validity
    public Date(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Error: Invalid date!");
        }
    }

    // Method to check if a date is valid
    public boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12 || day < 1) return false; // Month should be 1-12, and day should be at least 1

        // Days in each month (normal year)
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // If it's a leap year, February has 29 days
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }

        return day <= daysInMonth[month - 1]; // Check if the day is valid for the given month
    }

    // Method to determine if a year is a leap year
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Method to return the day of the week (e.g., "Monday")
    public String getDayOfWeek() {
        LocalDate date = LocalDate.of(year, month, day);
        return date.getDayOfWeek().toString();
    }

    // Method to calculate the difference in days between two dates
    public long calculateDifference(Date otherDate) {
        LocalDate thisDate = LocalDate.of(year, month, day);
        LocalDate other = LocalDate.of(otherDate.year, otherDate.month, otherDate.day);
        return ChronoUnit.DAYS.between(thisDate, other);
    }

    // Method to print the date in a readable format (e.g., "15 August 2022")
    public void printDate() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        System.out.println(day + " " + months[month - 1] + " " + year);
    }

    // Method to compare two dates for sorting (by year, then month, then day)
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        List<Date> dateList = new ArrayList<>(); // List to store date objects

        try {
            // Ask the user how many dates they want to enter
            System.out.print("Enter the number of dates to process: ");
            int numDates = scanner.nextInt();

            // Ensure that the user enters at least one date
            if (numDates <= 0) {
                throw new IllegalArgumentException("Error: The number of dates must be greater than zero.");
            }

            // Input multiple dates
            for (int i = 1; i <= numDates; i++) {
                System.out.println("\nEnter date " + i + ":");
                System.out.print("Day: ");
                int day = scanner.nextInt();
                System.out.print("Month: ");
                int month = scanner.nextInt();
                System.out.print("Year: ");
                int year = scanner.nextInt();

                // Create a Date object and add it to the list
                Date date = new Date(day, month, year);
                dateList.add(date);
            }

            // Print entered dates
            System.out.println("\nEntered dates:");
            for (Date d : dateList) {
                d.printDate();
            }

            // Compute differences between the first date and the others
            if (dateList.size() > 1) {
                System.out.println("\nDay of the week for the first date: " + dateList.get(0).getDayOfWeek());

                for (int i = 1; i < dateList.size(); i++) {
                    System.out.println("Difference in days between date 1 and date " + (i + 1) + ": " +
                            dateList.get(0).calculateDifference(dateList.get(i)));
                }
            }

            // Sort the dates in ascending order (earliest first)
            Collections.sort(dateList);
            System.out.println("\nSorted dates:");
            for (Date d : dateList) {
                d.printDate();
            }

        } catch (Exception e) { // Handle errors (e.g., invalid input)
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close(); // Close scanner to prevent resource leak
            System.out.println("Program finished.");
        }
    }
}