package com.Project_backend.Controller;

import com.Project_backend.Entity.Cart;
import com.Project_backend.Entity.Product;
import com.Project_backend.Repository.CartRepository;
import com.Project_backend.Service.CartService;
import com.Project_backend.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartRepository cartRepository;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Cart cart) {
        // Debug logging untuk memeriksa payload
        System.out.println(">>> Received Cart Payload:");
        if (cart.getUser() == null) {
            System.out.println("    user: NULL");
        } else {
            System.out.println("    user.id: " + cart.getUser().getId());
        }
        if (cart.getProduct() == null) {
            System.out.println("    product: NULL");
        } else {
            System.out.println("    product.id: " + cart.getProduct().getId());
        }
        System.out.println("    quantity: " + cart.getQuantity());

        // Menambahkan produk ke keranjang
        return cartService.addToCart(cart);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponse>> getUserCart(@PathVariable Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);

        List<CartResponse> response = carts.stream().map(cart -> {
            Product product = cart.getProduct();
            return new CartResponse(
                    cart.getId(),
                    cart.getQuantity(),
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageBase64()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        // Menghapus produk dari keranjang berdasarkan ID
        return cartService.removeFromCart(cartId);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartQuantity(@PathVariable Long cartId, @RequestParam Integer quantity) {
        // Memperbarui jumlah produk di keranjang
        return cartService.updateCartQuantity(cartId, quantity);
    }
}
