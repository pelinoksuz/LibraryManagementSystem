package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SignUpService {

    @Autowired
    JdbcTemplate conn;

    public boolean signupStudent(String name, String surname, String st_username, String st_password,String address, String email)
    {

        List<Map<String, Object>> response = conn.queryForList(
                "INSERT name, surname, st_username, st_password, address, email FROM student values ?, ?, ?, ?, ?, ?",
                new Object[]{name, surname, st_username, st_password, address, email}
        );

        return response.size() == 1;
    }
}
