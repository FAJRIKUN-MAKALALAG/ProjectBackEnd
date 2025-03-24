package com.Project_backend.Service;

import com.Project_backend.Entity.Cart;
import com.Project_backend.Repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;

    public ResponseEntity<Cart> addToCart(Cart cart) {
        try {
            return ResponseEntity.ok(cartRepository.save(cart));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<List<Cart>> getUserCart(Long userId) {
        return ResponseEntity.ok(cartRepository.findByUserId(userId));
    }

    public ResponseEntity<Void> removeFromCart(Long cartId) {
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Cart> updateCartQuantity(Long cartId, Integer quantity) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.setQuantity(quantity);
            return ResponseEntity.ok(cartRepository.save(cart));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
