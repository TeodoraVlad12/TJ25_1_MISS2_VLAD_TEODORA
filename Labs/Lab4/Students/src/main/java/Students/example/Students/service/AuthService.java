package Students.example.Students.service;

import Students.example.Students.dto.LoginRequest;
import Students.example.Students.dto.RegisterRequest;
import Students.example.Students.entity.Student;
import Students.example.Students.entity.Instructor;
import Students.example.Students.entity.User;
import Students.example.Students.repository.UserRepository;
import Students.example.Students.repository.StudentRepository;
import Students.example.Students.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user;
        
        if (request.getRole() == User.Role.STUDENT) {
            user = new Student();
            ((Student) user).setCode(request.getCode());
            ((Student) user).setYear(request.getYear());
        } else if (request.getRole() == User.Role.INSTRUCTOR) {
            user = new Instructor();
        } else {
            user = new User();
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(true);

        if (user instanceof Student) {
            studentRepository.save((Student) user);
        } else if (user instanceof Instructor) {
            instructorRepository.save((Instructor) user);
        } else {
            userRepository.save(user);
        }

        String token = jwtService.generateToken(user);
        
        return Map.of(
            "token", token,
            "username", user.getUsername(),
            "role", user.getRole().name(),
            "message", "User registered successfully"
        );
    }

    public Map<String, String> login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        
        return Map.of(
            "token", token,
            "username", user.getUsername(),
            "role", user.getRole().name(),
            "message", "Login successful"
        );
    }
}
