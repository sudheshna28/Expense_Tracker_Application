import java.io.*;
import java.util.*;

class Expense {
    String category;
    double amount;
    String date;

    public Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | ₹" + amount;
    }
}

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== EXPENSE TRACKER =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addExpense(sc);
                case 2 -> viewExpenses();
                case 3 -> generateReport();
                case 4 -> saveExpenses();
                default -> System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 4);
    }

    private static void addExpense(Scanner sc) {
        System.out.print("Enter category (Food/Travel/Other): ");
        String category = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        Expense expense = new Expense(category, amount, date);
        expenses.add(expense);
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("\n--- All Expenses ---");
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    private static void generateReport() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        double total = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense e : expenses) {
            total += e.amount;
            categoryTotals.put(e.category,
                    categoryTotals.getOrDefault(e.category, 0.0) + e.amount);
        }

        System.out.println("\n--- Expense Report ---");
        System.out.println("Total Spent: ₹" + total);
        for (String cat : categoryTotals.keySet()) {
            System.out.println(cat + ": ₹" + categoryTotals.get(cat));
        }
    }

    private static void loadExpenses() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(parts[0], Double.parseDouble(parts[1]), parts[2]));
            }
        } catch (IOException e) {
            System.out.println("No previous expenses found. Starting fresh.");
        }
    }

    private static void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                bw.write(e.category + "," + e.amount + "," + e.date);
                bw.newLine();
            }
            System.out.println("Expenses saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }
}
