package superJaba.rest.api.CRUD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private UserRepository userRepository;

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping("/users")
    public List<User> retriveAllUsers() {
        logger.info("All users ->{}", userRepository.findAll());
        return userRepository.findAll();
    }

    @RequestMapping("/user/{id}")
    public User retriveUser(@PathVariable long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException(id);

        return user.get();
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable long id) {

        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setEmail(newUser.getEmail());
                    user.setImage(newUser.getImage());
                    user.setPass(newUser.getPass());
                    user.setCreatedDate(newUser.getCreatedDate());
                    logger.info("Updated USER -> {}", userRepository.save(user));
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    logger.info("SAVE NEW USER/NOT UPDATED -> {}", userRepository.save(newUser));
                    return userRepository.save(newUser);
                });
    }
}


