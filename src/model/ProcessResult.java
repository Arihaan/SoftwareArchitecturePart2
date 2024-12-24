package model;

public class ProcessResult {
    private Customer customer;
    private Parcel parcel;
    private double fee;
    private boolean successful;

    public ProcessResult(Customer customer, Parcel parcel, double fee, boolean successful) {
        this.customer = customer;
        this.parcel = parcel;
        this.fee = fee;
        this.successful = successful;
    }

    public Customer getCustomer() { return customer; }
    public Parcel getParcel() { return parcel; }
    public double getFee() { return fee; }
    public boolean isSuccessful() { return successful; }
} 