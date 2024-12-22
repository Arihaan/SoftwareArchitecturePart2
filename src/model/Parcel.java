package model;

public class Parcel {
    private String id;
    private int daysInDepot;
    private double length;
    private double width;
    private double height;
    private double weight;
    private boolean collected;

    // Constructor
    public Parcel(String id, int daysInDepot, double length, double width, double height, double weight) {
        this.id = id;
        this.daysInDepot = daysInDepot;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.collected = false;
    }

    // Getters and setters
    public String getId() { return id; }
    public int getDaysInDepot() { return daysInDepot; }
    public double getLength() { return length; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public boolean isCollected() { return collected; }
    public void setCollected(boolean collected) { this.collected = collected; }

    @Override
    public String toString() {
        return String.format("Parcel[ID=%s, Days=%d, Dimensions=%.1fx%.1fx%.1f, Weight=%.1f, Collected=%s]",
                id, daysInDepot, length, width, height, weight, collected);
    }
} 