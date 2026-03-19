package service;

import javax.swing.SwingUtilities;
import model.Payment;
import ui.Dashboard;
import util.PaymentQueue;

public class PaymentConsumer implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                Payment p = PaymentQueue.queue.take();

                // simulate processing delay
                Thread.sleep(1000);

                // SUCCESS / FAILED logic
                double random = Math.random();
                if (random < 0.8) {
                    p.setStatus("SUCCESS");
                } else {
                    p.setStatus("FAILED");
                }

                System.out.println("Processed Payment: " + p.getPaymentId());

                // update UI
                Integer rowIndex = Dashboard.rowMap.get(p.getPaymentId());

                if (rowIndex != null) {
                    SwingUtilities.invokeLater(() -> {
                        Dashboard.model.setValueAt(p.getStatus(), rowIndex, 2);
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}