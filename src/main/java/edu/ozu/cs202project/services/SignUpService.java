package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class SignUpService {

    @Autowired
    JdbcTemplate conn;

    public boolean signupStudent(String namex, String surnamex, String st_usernamex, String st_passwordx,String addressx, String emailx)
    {

        List<Map<String, Object>> response = conn.queryForList(


                "INSERT name, surname, st_username, st_password, address, email FROM student values ?, ?, ?, ?, ?, ?",
                new Object[]{namex, surnamex, st_usernamex, st_passwordx, addressx, emailx}
        );

        return response.size() == 1;
    }
}
