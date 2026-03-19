package util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import model.Payment;

public class PaymentQueue {
    public static BlockingQueue<Payment> queue = new LinkedBlockingQueue<>();
}