package model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;
    private int nextSeqNo;

    public QueueOfCustomers() {
        customerQueue = new LinkedList<>();
        nextSeqNo = 1;
    }

    public void addCustomer(String name, String parcelId) {
        Customer customer = new Customer(name, parcelId, nextSeqNo++);
        customerQueue.offer(customer);
        Log.getInstance().addEntry("Customer added to queue: " + customer.getName());
    }

    public Customer removeCustomer() {
        Customer customer = customerQueue.poll();
        if (customer != null) {
            Log.getInstance().addEntry("Customer removed from queue: " + customer.getName());
        }
        return customer;
    }

    public int getQueueSize() {
        return customerQueue.size();
    }

    public Queue<Customer> getQueue() {
        return new LinkedList<>(customerQueue);
    }
} 