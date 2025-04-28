package com.Project_backend.Service;

import com.Project_backend.Entity.User;
import com.Project_backend.Repository.UserRepository;
import com.Project_backend.Util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;  // Kelas utilitas untuk JWT

    // Constructor untuk injeksi dependensi
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // Metode untuk membuat user baru
    public ResponseEntity<Object> create(User user) {
        try {
            // Mengecek apakah email sudah ada
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email sudah terdaftar");
            }

            // Validasi input untuk memastikan tidak ada data kosong
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nama tidak boleh kosong");
            }
            if (user.getAge() == null || user.getAge() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Umur harus diisi dan lebih dari 0");
            }

            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email tidak boleh kosong");
            }

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password tidak boleh kosong");
            }

            // Menambahkan validasi untuk role jika dibutuhkan
            if (user.getRole() == null || (!user.getRole().equals(User.Role.USER) && !user.getRole().equals(User.Role.ADMIN))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role tidak valid");
            }

            // Simpan user baru ke database
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saat membuat user: " + e.getMessage());
        }
    }




    // Metode untuk login dan mengembalikan token JWT
    public ResponseEntity<Object> login(String email, String password) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (user.getPassword().equals(password)) {
                    // Generate JWT jika password cocok
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.status(HttpStatus.OK).body("Bearer " + token);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password salah");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saat login: " + e.getMessage());
        }
    }

    // Mengambil daftar user
    public ResponseEntity<Object> getListData() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saat mengambil data user: " + e.getMessage());
        }
    }

    // Mengambil detail user berdasarkan ID
    public ResponseEntity<Object> getDataDetail(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
        }
    }

    // Menghapus user berdasarkan ID
    public ResponseEntity<Object> delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User berhasil dihapus");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
        }
    }

    // Memperbarui data user berdasarkan ID
    public ResponseEntity<Object> update(Long id, User userUpdate) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userUpdate.getName());
            user.setEmail(userUpdate.getEmail());

            // Update password jika ada
            if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
                user.setPassword(userUpdate.getPassword()); // Tidak enkripsi karena ini contoh tanpa Spring Security
            }

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User berhasil diperbarui");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User tidak ditemukan");
        }
    }
}
