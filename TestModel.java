import model.*;
import java.util.Scanner;

public class TestModel {
    private static ParcelMap parcelMap;
    private static QueueOfCustomers customerQueue;
    private static Worker worker;

    public static void main(String[] args) {
        // Initialize components
        parcelMap = new ParcelMap();
        customerQueue = new QueueOfCustomers();
        worker = new Worker(parcelMap, customerQueue);

        // Add some test data
        addTestData();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Parcel Depot Test System ===");
            System.out.println("1. Process next customer");
            System.out.println("2. Add new customer");
            System.out.println("3. Add new parcel");
            System.out.println("4. View uncollected parcels");
            System.out.println("5. View customer queue");
            System.out.println("6. View log");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    processNextCustomer();
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    addParcel(scanner);
                    break;
                case 4:
                    viewParcels();
                    break;
                case 5:
                    viewCustomers();
                    break;
                case 6:
                    viewLog();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
        scanner.close();
    }

    private static void addTestData() {
        // Add test parcels
        parcelMap.addParcel(new Parcel("C101", 2, 10, 20, 30, 5));
        parcelMap.addParcel(new Parcel("X102", 1, 15, 25, 35, 8));
        
        // Add test customers
        customerQueue.addCustomer("John Smith", "C101");
        customerQueue.addCustomer("Jane Doe", "X102");
    }

    private static void processNextCustomer() {
        ProcessResult result = worker.processNextCustomer();
        if (result == null) {
            System.out.println("No customers in queue!");
            return;
        }

        System.out.println("\nProcessing Result:");
        System.out.println("Customer: " + result.getCustomer().getName());
        System.out.println("Parcel ID: " + result.getParcel().getId());
        System.out.println("Fee: £" + String.format("%.2f", result.getFee()));
        System.out.println("Success: " + result.isSuccessful());
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter parcel ID (C### or X###): ");
        String parcelId = scanner.nextLine();
        
        customerQueue.addCustomer(name, parcelId);
        System.out.println("Customer added successfully!");
    }

    private static void addParcel(Scanner scanner) {
        System.out.print("Enter parcel ID (C### or X###): ");
        String id = scanner.nextLine();
        System.out.print("Days in depot: ");
        int days = scanner.nextInt();
        System.out.print("Length: ");
        double length = scanner.nextDouble();
        System.out.print("Width: ");
        double width = scanner.nextDouble();
        System.out.print("Height: ");
        double height = scanner.nextDouble();
        System.out.print("Weight: ");
        double weight = scanner.nextDouble();

        Parcel parcel = new Parcel(id, days, length, width, height, weight);
        parcelMap.addParcel(parcel);
        System.out.println("Parcel added successfully!");
    }

    private static void viewParcels() {
        System.out.println("\nUncollected Parcels:");
        for (Parcel p : parcelMap.getUncollectedParcels()) {
            System.out.println(p);
        }
    }

    private static void viewCustomers() {
        System.out.println("\nCustomer Queue:");
        for (Customer c : customerQueue.getQueue()) {
            System.out.println(c);
        }
    }

    private static void viewLog() {
        System.out.println("\nSystem Log:");
        System.out.println(Log.getInstance().getLog());
    }
} 