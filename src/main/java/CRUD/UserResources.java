package CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}


