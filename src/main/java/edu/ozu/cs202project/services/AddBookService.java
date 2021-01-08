package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class AddBookService {


    @Autowired
    JdbcTemplate conn;


    public boolean addBook(String genre, String author_name, String title, String status, int times_borrowed, int penaltyinfo, String requested)
    {

        List<Map<String, Object>> response = conn.queryForList(
                "INSERT genre, author_name, title, status, times_borrowed,  penaltyinfo, requested FROM book values  ?, ?, ?, ?, ?,?,?",
                new Object[]{genre, author_name, title, status, times_borrowed,  penaltyinfo, requested }
        );

        return response.size() == 1;
    }
}
