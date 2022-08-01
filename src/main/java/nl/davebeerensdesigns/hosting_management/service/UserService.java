package nl.davebeerensdesigns.hosting_management.service;

import nl.davebeerensdesigns.hosting_management.dto.UserInputDto;
import nl.davebeerensdesigns.hosting_management.exception.DefaultRoleCantBeRemovedException;
import nl.davebeerensdesigns.hosting_management.exception.EntityExistsException;
import nl.davebeerensdesigns.hosting_management.exception.EntityNotFoundException;
import nl.davebeerensdesigns.hosting_management.exception.OptionDoesNotExistException;
import nl.davebeerensdesigns.hosting_management.model.Authority;
import nl.davebeerensdesigns.hosting_management.model.User;
import nl.davebeerensdesigns.hosting_management.repository.UserRepository;
import nl.davebeerensdesigns.hosting_management.utils.HtmlToTextResolver;
import nl.davebeerensdesigns.hosting_management.utils.RandomStringGenerator;
import nl.davebeerensdesigns.hosting_management.validators.ValidMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }
        return users;
    }

    public Optional<User> getUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if(user.isEmpty()) {
            throw new EntityNotFoundException(User.class, "user id", username);
        }
        return user;
    }

    public boolean userIdExists(String username) {
        return userRepository.existsById(username);
    }

    public boolean userEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User addUser(UserInputDto user) {

        User newUser = new User();

        String username = HtmlToTextResolver.HtmlToText(user.getUsername());
        if (userIdExists(username)) {
            throw new EntityExistsException(User.class, "username", username);
        } else {
            newUser.setUsername(username);
        }

        String email = HtmlToTextResolver.HtmlToText(user.getEmail());
        if(!ValidMetaData.isValidEmail(email)){
            throw new IllegalArgumentException("Please enter a valid email address");
        } else if (userEmailExists(email)) {
            throw new EntityExistsException(User.class, "email", email);
        } else {
            newUser.setEmail(email);
        }

        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        newUser.setApikey(randomString);

        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        newUser.setPassword(password);

        newUser.addAuthority(new Authority(user.getUsername(), Authority.UserRoles.DEFAULT.toString()));

        return userRepository.save(newUser);
    }

    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) throw new EntityNotFoundException(User.class, "username", username);

        userRepository.deleteById(username);
    }

    public User updateUser(String username, UserInputDto newUser) {

        User user = userRepository.findById(username).orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));

        if(newUser.getEmail() != null){
            String email = HtmlToTextResolver.HtmlToText(newUser.getEmail());
            if(!ValidMetaData.isValidEmail(email)){
                throw new IllegalArgumentException("Please enter a valid email address");
            } else if (!email.equals(user.getEmail())){
                if (userEmailExists(email)) {
                    throw new EntityExistsException(User.class, "email", email);
                } else {
                    user.setEmail(email);
                }
            }
        }

        if(newUser.getPassword() != null) {
            String password = new BCryptPasswordEncoder().encode(newUser.getPassword());
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    public Set<Authority> getUserAuthorities(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException(User.class, "username", username)
        );
        return user.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException(User.class, "username", username)
        );

        String foundRole = null;
        String searchedValue = authority;
        ArrayList<String> values = new ArrayList<>();

        for(Authority.UserRoles userRoles : Authority.UserRoles.values()){
            values.add(userRoles.name());
            if((userRoles.toString()).equalsIgnoreCase(searchedValue)){
                foundRole = userRoles.toString();
            }
        }

        if(foundRole == null){
            throw new OptionDoesNotExistException(Authority.class, values.toString(), "authority", authority);
        }

        user.addAuthority(new Authority(username, foundRole));
        Instant updated = Instant.now();
        user.setDateModified(updated);
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {

        User user = userRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException(User.class, "username", username)
        );
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        if(authorityToRemove.getAuthority().equalsIgnoreCase(Authority.UserRoles.DEFAULT.toString())) {
            throw new DefaultRoleCantBeRemovedException(Authority.class, "authority", Authority.UserRoles.DEFAULT.toString());
        } else {
            user.removeAuthority(authorityToRemove);
            Instant updated = Instant.now();
            user.setDateModified(updated);
            userRepository.save(user);
        }
    }

}