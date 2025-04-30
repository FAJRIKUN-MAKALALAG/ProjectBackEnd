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

    // Endpoint untuk memulai pembayaran dengan orderId
    @PostMapping("/create/{orderId}")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId, @RequestBody Payment paymentRequest) {
        return paymentService.createPayment(orderId, paymentRequest);
    }
    // Endpoint untuk mendapatkan detail pembayaran berdasarkan orderId
    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPaymentDetail(@PathVariable Long orderId) {
        return paymentService.getPaymentDetail(orderId);
    }

    // Endpoint untuk mengupdate status pembayaran (misalnya, dari PENDING menjadi SUCCESS)
    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return paymentService.updatePaymentStatus(paymentId, status);
    }

    // Endpoint untuk melakukan proses checkout dan pembayaran
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<String> checkout(@PathVariable Long userId) {
        boolean isSuccess = paymentService.processCheckout(userId);
        if (isSuccess) {
            return ResponseEntity.ok("Checkout berhasil! Pembayaran sedang diproses.");
        } else {
            return ResponseEntity.status(400).body("Checkout gagal. Silakan coba lagi.");
        }
    }
}
