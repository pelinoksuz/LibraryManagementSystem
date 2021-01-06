package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginService
{
    @Autowired
    JdbcTemplate conn;

    public boolean studentvalidate(String st_username, String st_password)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM student WHERE st_username = ? AND st_password = ?",
                new Object[]{st_username, st_password}
                );

        return response.size() == 1;
    }
    public boolean librarianvalidate(String lib_username, String lib_password)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM librarian WHERE lib_username = ? AND lib_password = ?",
                new Object[]{lib_username, lib_password}
        );

        return response.size() == 1;
    }
    public boolean publishervalidate(String pub_username, String pub_password)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT * FROM publisher WHERE pub_username = ? AND pub_password = ?",
                new Object[]{pub_username, pub_password}
        );

        return response.size() == 1;
    }
}
