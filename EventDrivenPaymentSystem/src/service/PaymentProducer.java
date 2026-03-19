package service;

import model.Payment;
import util.PaymentQueue;

public class PaymentProducer {

    public static void produce(Payment p) {
        try {
            PaymentQueue.queue.put(p);
            System.out.println("Produced Payment: " + p.getPaymentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}