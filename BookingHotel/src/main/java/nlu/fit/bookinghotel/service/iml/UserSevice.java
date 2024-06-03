package nlu.fit.bookinghotel.service.iml;

import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.UserDTO;
import nlu.fit.bookinghotel.components.JwtTokenUtils;
import nlu.fit.bookinghotel.entity.Role;
import nlu.fit.bookinghotel.entity.User;
import nlu.fit.bookinghotel.exceptions.DataNotFoundException;
import nlu.fit.bookinghotel.exceptions.PermissionDenyException;
import nlu.fit.bookinghotel.repositories.RoleRepository;
import nlu.fit.bookinghotel.repositories.UserRepository;
import nlu.fit.bookinghotel.service.IUserSevice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSevice implements IUserSevice {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {

        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new DataIntegrityViolationException("Number Phone exists");
        }
        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(""));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }
        User newUser= User.builder()
                .fullName(userDTO.getFullName())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .isAtive(true)
                .build();
        newUser.setRole(role);
        // Kiểm tra nếu có accountId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public List<User> getAllUserl() {
        return null;
    }

    @Override
    public User updateUser(long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public String  login(String phoneNumber, String password,   Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("WRONG_PHONE_PASSWORD");
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("WRONG_PHONE_PASSWORD");
            }
        }
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException("ROLE_DOES_NOT_EXISTS");
        }
        if(!optionalUser.get().isAtive()) {
            throw new DataNotFoundException("USER_IS_LOCKED");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
