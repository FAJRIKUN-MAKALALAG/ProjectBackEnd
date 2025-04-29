package com.Project_backend.Controller;

import com.Project_backend.Entity.Cart;
import com.Project_backend.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

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

        return cartService.addToCart(cart);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        return cartService.removeFromCart(cartId);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartQuantity(@PathVariable Long cartId, @RequestParam Integer quantity) {
        return cartService.updateCartQuantity(cartId, quantity);
    }
}
