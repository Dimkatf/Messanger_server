package com.example.Messager.controller;

import com.example.Messager.entity.User;
import com.example.Messager.repository.UserRepository;
import com.example.Messager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/check-phone")
    public String checkPhone(@RequestParam String phone){
        boolean exists = userRepository.existsByPhone(phone);
        return exists ? "EXISTS" : "NOT_EXISTS";
    }

    @PostMapping("/register")
    public String registeUser(@RequestBody User user){
        if(userRepository.existsByPhone(user.getPhone())){
            return "PHONE_EXISTS";
        }
        userRepository.save(user);
        return "Пользователь успешно создан!";
    }

    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam("phone") String phone,
                                         @RequestParam("photo") MultipartFile photo) {
        try {
            System.out.println("🖥️ === UPLOAD PHOTO START ===");
            System.out.println("🖥️ Phone: " + phone);
            System.out.println("🖥️ File size: " + photo.getSize());

            User user = userRepository.findByPhone(phone);
            if (user == null) {
                return ResponseEntity.badRequest().body("{\"status\":\"error\", \"message\":\"User not found\"}");
            }

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File uploadsDir = new File(uploadDir);

            System.out.println("🖥️ Upload directory: " + uploadDir);

            if (!uploadsDir.exists()) {
                boolean created = uploadsDir.mkdirs();
                System.out.println("🖥️ Directory created: " + created);
            }

            String filename = phone + "_" + System.currentTimeMillis() + ".jpg";
            File file = new File(uploadsDir, filename);

            System.out.println("🖥️ Saving to: " + file.getAbsolutePath());

            photo.transferTo(file);

            System.out.println("🖥️ File saved: " + file.exists());
            System.out.println("🖥️ File size on disk: " + file.length());

            user.setPhotoFilename(filename);
            userRepository.save(user);

            System.out.println("🖥️ === UPLOAD PHOTO SUCCESS ===");
            return ResponseEntity.ok().body("{\"status\":\"success\", \"message\":\"Photo uploaded successfully\"}");

        } catch (Exception e) {
            System.out.println("🖥️ === UPLOAD PHOTO ERROR ===");
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"status\":\"error\", \"message\":\"File save error: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/user-photo")
    public ResponseEntity<byte[]> getUserPhoto(@RequestParam String phone) {
        try {
            User user = userRepository.findByPhone(phone);
            if (user == null || user.getPhotoFilename() == null) {
                return ResponseEntity.notFound().build();
            }

            File file = new File("uploads/" + user.getPhotoFilename());
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            byte[] photoBytes = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                    .body(photoBytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update-name")
    public ResponseEntity<String> updateUsername(@RequestBody UpdateNameRequest request){
        try{
            userService.updateUserName(request.getPhone(), request.getNewName());
            return ResponseEntity.ok("{\"status\":\"success\", \"message\":\"Успешно\"}");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("{\"status\":\"error\", \"message\":\"Ошибка: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        try {
            if (!userRepository.existsById(userId)) {
                return ResponseEntity.badRequest().body("Пользователь не найден");
            }
            userRepository.deleteById(userId);
            return ResponseEntity.ok().body("Пользователь успешно удален");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginData) {
        try {
            String phone = loginData.get("phone");
            String password = loginData.get("password");

            System.out.println("Login attempt - Phone: " + phone + ", Password: " + password);

            User user = userRepository.findByPhone(phone);
            System.out.println("User found: " + (user != null));

            if (user == null || !user.getPassword().equals(password)) {
                System.out.println("Login failed");
                return ResponseEntity.ok("LOGIN_FAILED");
            }

            System.out.println("Login successful - User: " + user.getName());

            String photoInfo = user.getPhotoFilename() != null ? "|HAS_PHOTO" : "|NO_PHOTO";
            return ResponseEntity.ok("LOGIN_SUCCESS|" + user.getId() + "|" + user.getName() + "|" + user.getPhone() + photoInfo);

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            return ResponseEntity.ok("LOGIN_FAILED");
        }
    }



    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public static class UpdateNameRequest {
        private String phone;
        private String newName;

        public UpdateNameRequest() {}

        public UpdateNameRequest(String phone, String newName) {
            this.phone = phone;
            this.newName = newName;
        }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getNewName() { return newName; }
        public void setNewName(String newName) { this.newName = newName; }
    }

    public static class LoginRequest {
        private String phone;
        private String password;

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
