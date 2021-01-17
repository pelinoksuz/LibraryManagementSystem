package edu.ozu.cs202project.services;

import edu.ozu.cs202project.exception.LMSException;
import edu.ozu.cs202project.user.User;
import edu.ozu.cs202project.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class SignUpService {

    @Autowired
    JdbcTemplate conn;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signupStudent(String name, String surname, String st_username, String st_password)
    {
        int response = 0;
        String encodedPassword = passwordEncoder.encode(st_password);
        try {
            response = conn.update(
                    "INSERT INTO student (name, surname, st_username, st_password) values (?, ?, ?, ?)",
                    name, surname, st_username, encodedPassword
            );
        }  catch (Exception e) {
            throw new LMSException("400", "Student creation failed. Make sure that username is not already registered.");
        }

        if (response > 0) {
            User user = new User();
            user.setUserName(st_username);
            user.setPassword(encodedPassword);
            user.setRoles("STUDENT");
            userService.create(user);
            return true;
        }
        return false;
    }

    public boolean signupPublisher(String name, String pub_username, String pub_password)
    {
        int response = 0;
        String encodedPassword = passwordEncoder.encode(pub_password);
        try {
            response = conn.update(
                    "INSERT INTO publisher (name, pub_username, pub_password) values (?, ?, ?)",
                    name, pub_username, encodedPassword
            );
        }  catch (Exception e) {
            throw new LMSException("400", "Publisher creation failed. Make sure that username is not already registered.");
        }

        if (response > 0) {
            User user = new User();
            user.setUserName(pub_username);
            user.setPassword(encodedPassword);
            user.setRoles("PUBLISHER");
            userService.create(user);
            return true;
        }
        return false;
    }

    // this function is just to ensure that one librarian exists in lms.
    // user can not create a new librarian
    // this function will be used by app initially if librarian does not exist
    public boolean signupLibrarian(String name, String surname, String lib_username, String lib_password)
    {
        int response = 0;
        String encodedPassword = passwordEncoder.encode(lib_password);
        try {
            List<String[]> data = conn.query("select  * from librarian",
                    (row, index) -> {
                        return new String[]{};
                    });
            if (data.size() > 0) {
                return false;
            }

            response = conn.update(
                    "INSERT INTO librarian (name, surname, lib_username, lib_password) values (?, ?, ?, ?)",
                    name, surname, lib_username, encodedPassword
            );
        }  catch (Exception e) {
            throw new LMSException("400", "Librarian creation failed. Make sure that username is not already registered.");
        }

        if (response > 0) {
            User user = new User();
            user.setUserName(lib_username);
            user.setPassword(encodedPassword);
            user.setRoles("LIBRARIAN");
            userService.create(user);
            return true;
        }
        return false;
    }
}
