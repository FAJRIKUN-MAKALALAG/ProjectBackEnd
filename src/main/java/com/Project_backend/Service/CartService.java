package com.Project_backend.Service;

import com.Project_backend.Entity.Cart;
import com.Project_backend.Entity.Product;
import com.Project_backend.Entity.User;
import com.Project_backend.Repository.CartRepository;
import com.Project_backend.Repository.ProductRepository;
import com.Project_backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ResponseEntity<Cart> addToCart(Cart cart) {
        try {
            // Validasi apakah user dan produk ada
            Optional<Product> productOptional = productRepository.findById(cart.getProduct().getId());
            Optional<User> userOptional = userRepository.findById(cart.getUser().getId());

            if (productOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(null);  // Produk tidak ditemukan
            }
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(null);  // User tidak ditemukan
            }

            cart.setProduct(productOptional.get());
            cart.setUser(userOptional.get());

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
