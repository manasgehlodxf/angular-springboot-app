package com.crud.user.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crud.user.entity.User;
import com.crud.user.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<User> getAllUsers() {
		logger.info("Fetching all users...");
		return userService.getAllUsers();
	}
	
	private final String uploadDir = "C:/Users/Divergent/Documents/Learing/user/user/uploads/";

	
	 @GetMapping("/{filename}")
	    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
	        try {
	            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
	            Resource resource = new UrlResource(filePath.toUri());

	            if (resource.exists()) {
	                return ResponseEntity.ok().body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().build();
	        }
	    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        logger.info("Fetching user with ID: {}", id);
//        Optional<User> user = userService.getUserById(id);
//        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

	@PutMapping("/me")
	public ResponseEntity<User> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User user) {
		String username = userDetails.getUsername();
		logger.info("Updating user: {}", username);

		Optional<User> userOptional = userService.getUserByUsername(username);
		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		User existingUser = userOptional.get();

		// Update only the allowed fields

		existingUser.setMobileNumber(user.getMobileNumber());

		User updatedUser = userService.updateUser(existingUser.getId(), existingUser);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		logger.info("Deleting user with ID: {}", id);
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/me/upload-profile-image")
	public ResponseEntity<String> uploadProfileImage(@AuthenticationPrincipal UserDetails userDetails,
			@RequestParam("file") MultipartFile file) {
		String username = userDetails.getUsername();
		logger.info("Uploading profile image for user: {}", username);

		Optional<User> userOptional = userService.getUserByUsername(username);
		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		User user = userOptional.get();
		String imageUrl = userService.uploadProfileImage(user.getId(), file);

		return ResponseEntity.ok(imageUrl);
	}

	@GetMapping("/me")
	public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		logger.info("Fetching profile details for user: {}", username);

		Optional<User> userOptional = userService.getUserByUsername(username);

		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		User user = userOptional.get();

		// Create a response DTO with user details and profile image
		Map<String, Object> response = new HashMap<>();
		response.put("id", user.getId());
		response.put("name", user.getName());
		response.put("email", user.getEmail());
		response.put("mobileNumber", user.getMobileNumber());
		response.put("status", user.getStatus());
		response.put("profileImageUrl", user.getProfileImageUrl());

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/me/profile-image")
	public ResponseEntity<String> deleteProfileImage(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		logger.info("Deleting profile image for user: {}", username);

		Optional<User> userOptional = userService.getUserByUsername(username);

		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		User user = userOptional.get();
		String imageUrl = user.getProfileImageUrl();

		if (imageUrl == null || imageUrl.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No profile image to delete");
		}

		// Delete the image from the file system
		Path imagePath = Paths.get("uploads/" + imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
		try {
			Files.deleteIfExists(imagePath);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete profile image");
		}

		// Remove profile image URL from the user record
		user.setProfileImageUrl(null);
		userService.addUser(user);

		return ResponseEntity.ok("Profile image deleted successfully");
	}

}
