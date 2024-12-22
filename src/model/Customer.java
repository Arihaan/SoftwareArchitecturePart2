package model;

public class Customer {
    private int seqNo;
    private String name;
    private String parcelId;

    // Constructor
    public Customer(String name, String parcelId, int seqNo) {
        this.seqNo = seqNo;
        this.name = name;
        this.parcelId = parcelId;
    }

    // Getters
    public int getSeqNo() { return seqNo; }
    public String getName() { return name; }
    public String getParcelId() { return parcelId; }

    @Override
    public String toString() {
        return String.format("Customer[Seq=%d, Name=%s, ParcelID=%s]", seqNo, name, parcelId);
    }
} 