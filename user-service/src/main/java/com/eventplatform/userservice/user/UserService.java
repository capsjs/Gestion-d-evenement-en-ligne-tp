package com.eventplatform.userservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();

        for (User user : users) {
            result.add(toResponse(user));
        }

        return result;
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse);
    }

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        User user = fromRequest(request);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(request.getFirstName());
                    existing.setLastName(request.getLastName());
                    existing.setEmail(request.getEmail());
                    existing.setPassword(request.getPassword());
                    existing.setRole(request.getRole());
                    User saved = userRepository.save(existing);
                    return toResponse(saved);
                })
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserResponse> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .map(this::toResponse);
    }

    public UserResponse updateUserRole(Long id, String newRole) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(newRole);
                    User saved = userRepository.save(user);
                    return toResponse(saved);
                })
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }


    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    private User fromRequest(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        return user;
    }
}
