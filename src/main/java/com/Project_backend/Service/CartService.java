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
            // Validasi user dan produk
            Optional<Product> productOptional = productRepository.findById(cart.getProduct().getId());
            Optional<User> userOptional = userRepository.findById(cart.getUser().getId());

            if (productOptional.isEmpty() || userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            if (cart.getQuantity() == null || cart.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body(null);
            }

            Product product = productOptional.get();
            User user = userOptional.get();

            // Cek apakah produk sudah ada di cart user
            Optional<Cart> existingCart = cartRepository.findByUserIdAndProductId(user.getId(), product.getId());
            if (existingCart.isPresent()) {
                Cart updatedCart = existingCart.get();
                updatedCart.setQuantity(updatedCart.getQuantity() + cart.getQuantity());
                return ResponseEntity.ok(cartRepository.save(updatedCart));
            }

            // Jika belum ada, tambahkan produk baru ke cart
            cart.setProduct(product);
            cart.setUser(user);
            return ResponseEntity.ok(cartRepository.save(cart));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    public ResponseEntity<List<Cart>> getUserCart(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        // Pastikan produk dan pengguna di-load
        for (Cart cart : carts) {
            cart.getProduct().getName(); // Memastikan produk dimuat
            cart.getProduct().getImageBase64(); // Memastikan gambar dimuat
            cart.getProduct().getPrice(); // Memastikan harga dimuat
        }
        return ResponseEntity.ok(carts);
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
        if (quantity == null || quantity <= 0) {
            return ResponseEntity.badRequest().build();
        }

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
