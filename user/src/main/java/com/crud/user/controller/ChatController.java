package com.crud.user.controller;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	private ChatClient chatClient;

	public ChatController(OllamaChatModel chatModel) {
		this.chatClient = ChatClient.create(chatModel);
	}

	@GetMapping("/{message}")
	public ResponseEntity<Map<String, String>> getPrompt(@PathVariable String message) {
	    // Validate that the message is not null or empty
	    if (message == null || message.trim().isEmpty()) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Message cannot be null or empty");
	        return ResponseEntity.badRequest().body(errorResponse);
	    }

	    try {
	        // Call AI chat client
	        String response = chatClient.prompt(message).call().content();
	        
	        // Construct JSON response
	        Map<String, String> jsonResponse = new HashMap<>();
	        jsonResponse.put("answer", response);

	        return ResponseEntity.ok(jsonResponse);
	    } catch (Exception e) {
	        // Return a proper JSON error message
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Error processing the AI response: " + e.getMessage());
	        return ResponseEntity.status(500).body(errorResponse);
	    }
	}
}
	

//@GetMapping("/{message}")
//public ResponseEntity<Map<String, String>> getChatResponse(@PathVariable String message) {
//    String response = "Hello! How can I assist you?";
//    Map<String, String> jsonResponse = new HashMap<>();
//    jsonResponse.put("answer", response);
//    return ResponseEntity.ok(jsonResponse);
//}



