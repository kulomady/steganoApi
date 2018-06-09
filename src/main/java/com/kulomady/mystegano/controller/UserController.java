package com.kulomady.mystegano.controller;

import com.kulomady.mystegano.exception.ResourceNotFoundException;
import com.kulomady.mystegano.exception.UserAlreadyRegisteredException;
import com.kulomady.mystegano.exception.UserNotAuthorizeException;
import com.kulomady.mystegano.model.User;
import com.kulomady.mystegano.payload.UploadFileResponse;
import com.kulomady.mystegano.repository.UserRepository;
import com.kulomady.mystegano.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FileStorageService fileStorageService;

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // Create a new User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }


    // Get a Single User
    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable(value = "username") String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }


    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/register")
    @ResponseBody

    public User register(@Valid @RequestPart String username,
                                       @Valid @RequestPart String password,
                                       @Valid @RequestPart String email,
                                       @Valid @RequestPart String secreet_message,
                                       @RequestPart(value = "file") MultipartFile file) throws UserAlreadyRegisteredException {

        if (userRepository.findById(username).isPresent()) {
            throw new UserAlreadyRegisteredException("User already registered");
        } else {
            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            User user = new User(username, password, email, secreet_message, fileDownloadUri);
            userRepository.save(user);

           return user;
        }

    }

    // Create a new User
    @PostMapping("/login")
    public User login(@Valid @RequestBody User user) throws UserNotAuthorizeException {
        User userResult =  userRepository.findUser(user.getUsername(),user.getPassword(),user.getSecreetMessage());
        if(userResult == null || userResult.getUsername().isEmpty()){
            logger.error("errror: " );
            throw new UserNotAuthorizeException("Failed Login");
        }else {
            logger.error("sukses: " + userResult.getUsername());
            return userRepository.findById(
                    userResult.getUsername()).isPresent() ? userRepository.findById(userResult.getUsername()).get()
                    : userResult;
        }
    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
