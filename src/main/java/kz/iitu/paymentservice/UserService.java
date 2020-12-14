package kz.iitu.paymentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("There is no user with such id");
        }
        return userRepository.findById(id).get();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User saveUser(User user)  {
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }
}
