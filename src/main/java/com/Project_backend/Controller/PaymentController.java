package com.Project_backend.Controller;

import com.Project_backend.Entity.Payment;
import com.Project_backend.Entity.PaymentStatus;
import com.Project_backend.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{orderId}")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId, @RequestBody Payment payment) {
        return paymentService.createPayment(orderId, payment);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPaymentDetail(@PathVariable Long orderId) {
        return paymentService.getPaymentDetail(orderId);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }
}
