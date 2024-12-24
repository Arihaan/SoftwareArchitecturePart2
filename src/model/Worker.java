package model;

public class Worker {
    private static final double BASE_FEE = 5.0;
    private static final double WEIGHT_MULTIPLIER = 0.5;
    private static final double VOLUME_MULTIPLIER = 0.1;
    private static final double DAYS_MULTIPLIER = 0.2;
    private static final double EXPRESS_DISCOUNT = 0.8; // 20% discount for express (X) parcels

    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;

    public Worker(ParcelMap parcelMap, QueueOfCustomers customerQueue) {
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
    }

    private boolean isValidParcelId(String id) {
        // Format: C### or X### (letter C or X followed by 3 digits)
        return id.matches("[CX]\\d{3}");
    }

    public double calculateFee(Parcel parcel) {
        if (!isValidParcelId(parcel.getId())) {
            Log.getInstance().addEntry("Invalid parcel ID format: " + parcel.getId());
            return 0.0;
        }

        double volume = parcel.getLength() * parcel.getWidth() * parcel.getHeight();
        double fee = BASE_FEE;
        
        // Add weight component
        fee += parcel.getWeight() * WEIGHT_MULTIPLIER;
        
        // Add volume component
        fee += volume * VOLUME_MULTIPLIER;
        
        // Add days in depot component
        fee += parcel.getDaysInDepot() * DAYS_MULTIPLIER;
        
        // Apply discount for express (X) parcels
        if (parcel.getId().startsWith("X")) {
            fee *= EXPRESS_DISCOUNT;
            Log.getInstance().addEntry("Express discount applied to parcel: " + parcel.getId());
        }
        
        return Math.round(fee * 100.0) / 100.0; // Round to 2 decimal places
    }

    public ProcessResult processNextCustomer() {
        Customer customer = customerQueue.removeCustomer();
        if (customer == null) {
            return null;
        }

        Parcel parcel = parcelMap.getParcel(customer.getParcelId());
        if (parcel == null) {
            Log.getInstance().addEntry("Error: Parcel " + customer.getParcelId() + 
                " not found for customer " + customer.getName());
            return new ProcessResult(customer, null, 0.0, false);
        }

        if (!isValidParcelId(parcel.getId())) {
            Log.getInstance().addEntry("Error: Invalid parcel ID format: " + parcel.getId());
            return new ProcessResult(customer, parcel, 0.0, false);
        }

        if (parcel.isCollected()) {
            Log.getInstance().addEntry("Error: Parcel " + parcel.getId() + 
                " has already been collected");
            return new ProcessResult(customer, parcel, 0.0, false);
        }

        double fee = calculateFee(parcel);
        parcel.setCollected(true);
        
        Log.getInstance().addEntry(String.format(
            "Processed: Customer=%s, ParcelID=%s, Fee=£%.2f",
            customer.getName(), parcel.getId(), fee));
            
        return new ProcessResult(customer, parcel, fee, true);
    }
} 