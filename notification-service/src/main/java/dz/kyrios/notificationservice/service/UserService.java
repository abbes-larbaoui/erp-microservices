package dz.kyrios.notificationservice.service;

import dz.kyrios.notificationservice.config.exception.NotFoundException;
import dz.kyrios.notificationservice.entity.User;
import dz.kyrios.notificationservice.event.notification.NotificationPayload;
import dz.kyrios.notificationservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User getOne(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("User not found with uuid: " + uuid));
        return user;
    }

    public User getByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }
}
