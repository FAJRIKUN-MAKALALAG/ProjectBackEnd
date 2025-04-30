package com.Project_backend.Service;

import com.Project_backend.Entity.Order;
import com.Project_backend.Entity.Payment;
import com.Project_backend.Entity.PaymentStatus;
import com.Project_backend.Entity.OrderStatus;
import com.Project_backend.Repository.OrderRepository;
import com.Project_backend.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    // Membuat pembayaran baru berdasarkan orderId
    public ResponseEntity<Payment> createPayment(Long orderId, Payment paymentRequest) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Order order = orderOptional.get();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING); // Status awal adalah PENDING
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(new Date());

        // Simpan pembayaran di database
        return ResponseEntity.ok(paymentRepository.save(payment));
    }

    // Mengambil detail pembayaran berdasarkan orderId
    public ResponseEntity<Payment> getPaymentDetail(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    // Mengupdate status pembayaran (misalnya menjadi SUCCESS setelah berhasil dibayar)
    public ResponseEntity<Payment> updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status); // Status harus dari enum PaymentStatus
            return ResponseEntity.ok(paymentRepository.save(payment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Proses Checkout untuk user dengan mengambil order yang masih PENDING
    public boolean processCheckout(Long userId) {
        // Mengambil list order yang PENDING dari user
        List<Order> orders = orderRepository.findByUserIdAndStatus(userId, OrderStatus.PENDING);

        if (orders.isEmpty()) {
            return false; // Tidak ada order yang PENDING
        }

        Order order = orders.get(0); // Misalnya ambil order pertama yang ditemukan

        // Proses pembayaran untuk order ini
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod("Credit Card"); // Bisa disesuaikan dengan metode pembayaran yang ada
        payment.setStatus(PaymentStatus.PENDING); // Status awal adalah PENDING
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(new Date());

        // Simpan pembayaran dan update status order
        paymentRepository.save(payment);
        order.setStatus(OrderStatus.PAID);  // Mengubah status order menjadi PAID
        orderRepository.save(order);

        return true; // Proses checkout berhasil
    }
}
