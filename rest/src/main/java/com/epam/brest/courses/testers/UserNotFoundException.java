package com.epam.brest.courses.testers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by xalf on 19.01.16.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "This user is not found in the system")
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String username){
        super("User '" + username + "' not available in the system.");
    }

}
