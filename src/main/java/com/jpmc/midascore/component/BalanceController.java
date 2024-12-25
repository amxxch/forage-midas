package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class BalanceController {
    private final UserRepository userRepository;

    @Autowired
    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Incentive getBalance(@RequestParam("userId") long user_id) {
        Optional<UserRecord> userOpt = userRepository.findById(user_id);

        if (userOpt.isPresent()) {
            UserRecord user = userOpt.get();
            return new Incentive(user.getBalance());
        } else {
            return new Incentive(0f);
        }
    }
}
