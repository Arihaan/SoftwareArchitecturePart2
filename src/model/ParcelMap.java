package model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ParcelMap {
    private Map<String, Parcel> parcels;

    public ParcelMap() {
        parcels = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getId(), parcel);
        Log.getInstance().addEntry("Added parcel: " + parcel.getId());
    }

    public Parcel getParcel(String id) {
        return parcels.get(id);
    }

    public List<Parcel> getUncollectedParcels() {
        List<Parcel> uncollected = new ArrayList<>();
        for (Parcel p : parcels.values()) {
            if (!p.isCollected()) {
                uncollected.add(p);
            }
        }
        return uncollected;
    }

    public List<Parcel> getCollectedParcels() {
        List<Parcel> collected = new ArrayList<>();
        for (Parcel p : parcels.values()) {
            if (p.isCollected()) {
                collected.add(p);
            }
        }
        return collected;
    }
} 