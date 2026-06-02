package com.abhijit.ems.util;

import com.abhijit.ems.model.EmployeeStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputReader {
    private final Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }

    public String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field is required.");
        }
    }

    public String readOptionalString(String prompt, String currentValue) {
        System.out.printf("%s [%s]: ", prompt, currentValue == null ? "" : currentValue);
        String value = scanner.nextLine().trim();
        return value.isEmpty() ? currentValue : value;
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    public BigDecimal readOptionalBigDecimal(String prompt, BigDecimal currentValue) {
        while (true) {
            System.out.printf("%s [%s]: ", prompt, currentValue);
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return currentValue;
            }
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            System.out.print(prompt + " (yyyy-mm-dd): ");
            String value = scanner.nextLine().trim();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException exception) {
                System.out.println("Please enter a valid date in yyyy-mm-dd format.");
            }
        }
    }

    public LocalDate readOptionalLocalDate(String prompt, LocalDate currentValue) {
        while (true) {
            System.out.printf("%s [%s]: ", prompt, currentValue);
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return currentValue;
            }
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException exception) {
                System.out.println("Please enter a valid date in yyyy-mm-dd format.");
            }
        }
    }

    public EmployeeStatus readStatus(String prompt) {
        while (true) {
            System.out.print(prompt + " (ACTIVE/INACTIVE): ");
            String value = scanner.nextLine().trim().toUpperCase();
            try {
                return EmployeeStatus.valueOf(value);
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter ACTIVE or INACTIVE.");
            }
        }
    }

    public EmployeeStatus readOptionalStatus(String prompt, EmployeeStatus currentValue) {
        while (true) {
            System.out.printf("%s [%s]: ", prompt, currentValue);
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return currentValue;
            }
            try {
                return EmployeeStatus.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException exception) {
                System.out.println("Please enter ACTIVE or INACTIVE.");
            }
        }
    }

    public boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String value = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(value) || "yes".equals(value)) {
                return true;
            }
            if ("n".equals(value) || "no".equals(value)) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }

    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
