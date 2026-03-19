import service.PaymentConsumer;
import service.PaymentProducer;

public class Main {
    public static void main(String[] args) {

        Thread producer = new Thread(new PaymentProducer());
        Thread consumer1 = new Thread(new PaymentConsumer());
        Thread consumer2 = new Thread(new PaymentConsumer());

        producer.start();
        consumer1.start();
        consumer2.start();
    }
}