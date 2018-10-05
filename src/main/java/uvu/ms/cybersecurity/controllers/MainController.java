package uvu.ms.cybersecurity.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uvu.ms.cybersecurity.model.UserData;

@RestController
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/user-info")
    public UserData root(Authentication authentication) {
        logger.debug("Retrieving user info for {}",authentication.getName());
        return new UserData(authentication.getName());
    }
}
