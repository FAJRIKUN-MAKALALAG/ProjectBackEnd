package com.Project_backend.Service;

import com.Project_backend.Entity.Order;
import com.Project_backend.Entity.Payment;
import com.Project_backend.Entity.PaymentStatus;
import com.Project_backend.Repository.OrderRepository;
import com.Project_backend.Repository.PaymentRepository;
import com.Project_backend.Entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public ResponseEntity<Payment> createPayment(Long orderId, Payment paymentRequest) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Order order = orderOptional.get();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus(com.Project_backend.Entity.PaymentStatus.PENDING);
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(new Date());

        return ResponseEntity.ok(paymentRepository.save(payment));
    }

    public ResponseEntity<Payment> getPaymentDetail(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        return payment != null ? ResponseEntity.ok(payment) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Payment> updatePaymentStatus(Long paymentId, com.Project_backend.Entity.PaymentStatus status) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setStatus(status);
            return ResponseEntity.ok(paymentRepository.save(payment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
