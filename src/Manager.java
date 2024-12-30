import java.io.*;
import java.util.*;
import model.*;
import view.*;
import controller.*;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Manager {
    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;
    private Worker worker;
    private DepotView view;
    private DepotController controller;

    public Manager() {
        parcelMap = new ParcelMap();
        customerQueue = new QueueOfCustomers();
        worker = new Worker(parcelMap, customerQueue);
    }

    public void loadData() {
        try {
            loadParcels("Parcels.csv");
            Log.getInstance().addEntry("Loaded parcels from Parcels.csv");
        } catch (IOException e) {
            Log.getInstance().addEntry("Warning: Could not load Parcels.csv - starting with empty parcel list");
        }

        try {
            loadCustomers("Custs.csv");
            Log.getInstance().addEntry("Loaded customers from Custs.csv");
        } catch (IOException e) {
            Log.getInstance().addEntry("Warning: Could not load Custs.csv - starting with empty customer queue");
        }
    }

    private void loadParcels(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("File not found: " + filename);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        Parcel parcel = new Parcel(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3]),
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5])
                        );
                        parcelMap.addParcel(parcel);
                    } catch (NumberFormatException e) {
                        Log.getInstance().addEntry("Warning: Skipped invalid parcel data: " + line);
                    }
                }
            }
        }
    }

    private void loadCustomers(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("File not found: " + filename);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    customerQueue.addCustomer(parts[0], parts[1]);
                }
            }
        }
    }

    public void start() {
        view = new DepotView();
        controller = new DepotController(parcelMap, customerQueue, worker, view);
        
        // Add window listener to handle application close
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Write log file before exit
                Log.getInstance().writeToFile("depot_log.txt");
                System.exit(0);
            }
        });
        
        view.setVisible(true);
    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.loadData();
        
        // Start GUI 
        SwingUtilities.invokeLater(() -> {
            manager.start();
        });
    }
} 