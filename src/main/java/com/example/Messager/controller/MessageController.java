package com.example.Messager.controller;

import com.example.Messager.entity.Message;
import com.example.Messager.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> request){
        try{
            String chatId = request.get("chatId");
            String text = request.get("text");

            Message message = new Message(chatId, "user", text);
            messageRepository.save(message);
            return ResponseEntity.ok().body("{\"status\":\"success\", \"message\":\"Message sent\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"status\":\"error\", \"message\":\"Error: " + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/get-messages")
    public ResponseEntity<?> getMessages(@RequestParam String chatId) {
        try {
            List<Message> messages = messageRepository.findByChatIdOrderByTimestampAsc(chatId);
            List<Map<String, Object>> response = new ArrayList<>();
            for (Message message : messages) {
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("id", message.getId());
                messageMap.put("text", message.getText());
                messageMap.put("edited", message.isEdited());
                messageMap.put("timestamp", message.getTimestamp().toString());
                messageMap.put("chatId", message.getChatId());
                response.add(messageMap);
            }

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    @GetMapping("/get-last-message")
    public ResponseEntity<Map<String, String>> getLastMessage(@RequestParam String chatId){
        try{
            List<Message> messages = messageRepository.findByChatIdOrderByIdAsc(chatId);
            Map<String, String> response = new HashMap<>();

            if(messages.isEmpty()) {
                response.put("status", "success");
                response.put("message", "Нет сообщений");
                response.put("timestamp", "");
            } else {
                Message lastMessage = messages.get(messages.size()-1);
                response.put("status", "success");
                response.put("message", lastMessage.getText());
                if (lastMessage.getTimestamp() != null) {
                    response.put("timestamp", lastMessage.getTimestamp().toString());
                } else {
                    response.put("timestamp", "");
                }
            }
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Ошибка: " + e.getMessage());
            response.put("timestamp", "");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delete-message")
    public ResponseEntity<?> deleteMessage(@RequestParam Long messageId){
        try{
            if(!messageRepository.existsById(messageId))
                return ResponseEntity.badRequest().body("Ошибка");
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok().body("Сообщение удалено");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    @PutMapping("update_message")
    public ResponseEntity<?> update_message(@RequestBody UpdateMessageDTO updateMessageDTO){
        try {
            Optional<Message> optionalMessage = messageRepository.findById(updateMessageDTO.getId());
            if(!optionalMessage.isPresent())
                return ResponseEntity.badRequest().body("Сообщение не найдено!");
            Message message = optionalMessage.get();
            message.setText(updateMessageDTO.getNewText());
            message.setEdited(true);
            messageRepository.save(message);
            return ResponseEntity.ok().body("Сообщение обновлено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
}
