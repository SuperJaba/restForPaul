package CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResources {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public List<User> retriveAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/user/{id}")
    public User retriveUser(@PathVariable long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException("id-"+ id);
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
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent())
            return ResponseEntity.notFound().build();

        user.setId(id);

        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }
}


