package edu.ozu.cs202project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class BorrowService
{
    @Autowired
    JdbcTemplate conn;

    public boolean isBorrowed(Date date_returned, Date date_start)
    {
        List<Map<String, Object>> response = conn.queryForList(
                "select name, surname, title, author_name, date_start, date_returned from book join borrowed_by join student where student.student_id = borrowed_by.student_id and book.book_id = borrowed_by.book_id and date_start>=? and date_returned <= ?",
                new Object[]{date_start,date_returned}
        );

        return response.size() == 1;
    }


}
