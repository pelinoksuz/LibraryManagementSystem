package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.services.BorrowService;
import edu.ozu.cs202project.services.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"username", "level", "bookData","lib_username","pub_username",
        "title","overdueData","borrowedData","mostBorrowedData","sumofoverdued","name",
        "userBorrowedData","date_start","date_returned"})
public class AppController
{
    @Autowired
    LoginService service;
   @Autowired
   BorrowService serviceBorrow;
    @Autowired
    JdbcTemplate conn;



    @GetMapping("/studentLogin")
    public String studentLogin(ModelMap model)
    {
        return "studentLogin";
    }
    @PostMapping("/studentLogin")
    public String studentLogin(ModelMap model, @RequestParam String username, @RequestParam String password)
    {
        //password = Salter.salt(password, "CS202Project");

        if (!service.studentvalidate(username, password))
        {
            model.put("errorMessage", "Invalid Credentials");

            return "studentLogin";
        }

        model.put("username", username);

        return "studentLogin";
    }

    @GetMapping("/librarianLogin")
    public String librarianLogin(ModelMap model)
    {
        return "librarianLogin";
    }
    @PostMapping("/librarianLogin")
    public String librarianLogin(ModelMap model,@RequestParam String lib_username, @RequestParam String lib_password)
    {
        //password = Salter.salt(password, "CS202Project");

        if (!service.librarianvalidate(lib_username, lib_password))
        {
            model.put("errorMessage", "Invalid Credentials");

            return "librarianLogin";
        }

        model.put("lib_username", lib_username);

        return "librarianLogin";
    }

    @GetMapping("/PublisherLogin")
    public String PublisherLogin(ModelMap model)
    {
        return "PublisherLogin";
    }
    @PostMapping("/PublisherLogin")
    public String PublisherLogin(ModelMap model,@RequestParam String pub_username, @RequestParam String pub_password)
    {
        //password = Salter.salt(password, "CS202Project");

        if (!service.publishervalidate(pub_username, pub_password))
        {
            model.put("errorMessage", "Invalid Credentials");

            return "PublisherLogin";
        }

        model.put("pub_username", pub_username);

        return "PublisherLogin";
    }

    @GetMapping("/addBook")
    public String addBook(ModelMap model)
    {
        return "addBook";
    }
    @PostMapping("/addBook")
    public String addBook(ModelMap model,@RequestParam String genre, @RequestParam String author_name, @RequestParam String title, @RequestParam String status, @RequestParam Date publication_date, @RequestParam int times_borrowed, @RequestParam int pub_id, @RequestParam int lib_id, @RequestParam String requested) {
        model.put("title",title);
        conn.update(
                "INSERT INTO book(genre,author_name,title, status, publication_date,times_borrowed,requested,pub_id,lib_id) values(?,?,?,?,?,?,?,?,?)", genre,author_name,title, status, publication_date,times_borrowed,requested,pub_id,lib_id);
        return "addBook";

    }
    @GetMapping("/deleteBook")
    public String deleteBook(ModelMap model)
    {
        return "deleteBook";
    }
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam int book_id) {

        conn.update(
                "DELETE FROM book WHERE book.book_id =?",book_id);
        return "deleteBook";

    }
    @GetMapping("/studentSignUp")
    public String studentSignUp(ModelMap model)
    {
        return "studentSignUp";
    }
    @PostMapping("/studentSignUp")
    public String studentSignUp(ModelMap model, @RequestParam String name, @RequestParam String surname,
                                @RequestParam String st_username, @RequestParam String st_password)
    {
        model.put("name",name);
        conn.update(
                "INSERT INTO student(name,surname,st_username, st_password) values(?,?,?,?)", name, surname, st_username, st_password);
        return "studentSignUp";
    }
    @GetMapping("/PublisherSignUp")
    public String PublisherSignUp(ModelMap model)
    {
        return "PublisherSignUp";
    }
    @PostMapping("/PublisherSignUp")
    public String PublisherSignUp(ModelMap model, @RequestParam String name,
                                @RequestParam String pub_username, @RequestParam String pub_password)
    {
        model.put("name",name);
        conn.update(
                "INSERT INTO Publisher(name,pub_username, pub_password) values(?,?,?)", name, pub_username, pub_password);
        return "PublisherSignUp";
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, WebRequest request, SessionStatus session)
    {
        session.setComplete();
        request.removeAttribute("username", WebRequest.SCOPE_SESSION);

        return "redirect:/login";
    }

    @GetMapping("/borrowBook")
    public String borrowBook(ModelMap model)
    {
        return "borrowBook";
    }
    @PostMapping("/borrowBook")
    public String borrowBook(@RequestParam int student_id, @RequestParam int book_id)
    {
        LocalDate localDate = LocalDate.now();
        LocalDate date_start = localDate;
        LocalDate date_returned = localDate.plusDays(30);
        Boolean isBefore = localDate.isBefore(date_returned);
        List<Map<String, Object>> response = conn.queryForList(
                "SELECT book_id FROM book WHERE status ='PRESENT'",
                new Object[]{book_id}
        );
            conn.update(
                    "INSERT INTO borrowed_by(student_id,book_id,date_start,date_returned) values(?,?,?,?)", student_id, book_id, date_start, date_returned);


        return "borrowBook";
    }

    @GetMapping("/usersBorrowedCurrently")
    public String usersBorrowedCurrently(ModelMap model)
    {
        return "usersBorrowedCurrently";
    }
    @PostMapping("/usersBorrowedCurrently")
    public String usersBorrowedCurrently(ModelMap model,@RequestParam Date date_returned, @RequestParam Date date_start)
    {
        model.put("date_start", date_start);
        if (!serviceBorrow.isBorrowed(date_returned, date_start))
        {
            model.put("errorMessage", "There are no books borrowed between these dates.");

            return "usersBorrowedCurrently";
        }

        return "usersBorrowedCurrently";
    }

    @GetMapping("/listUsersBorrowedCurrently")
    public String listUsersBorrowedCurrently(ModelMap model)
    {

        List<String[]> data = conn.query("select name, surname, title, author_name, date_start, date_returned from book join borrowed_by join student where student.student_id = borrowed_by.student_id and book.book_id = borrowed_by.book_id ",
                (row, index) -> {
                    return new String[]{ row.getString("name"), row.getString("surname"),row.getString("title"), row.getString("author_name"),row.getString("date_start"), row.getString("date_returned") };
                });

        model.addAttribute("userBorrowedData", data.toArray(new String[0][5]));
        return "/listUsersBorrowedCurrently";
    }
    @GetMapping("/informationOfBooks")
    public String informationOfBooks(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM book",
                (row, index) -> {
                    return new String[]{ row.getString("book_id"), row.getString("title"), row.getString("publication_date"), row.getString("author_name"), row.getString("genre") };
                });

        model.addAttribute("bookData", data.toArray(new String[0][5]));

        return "informationOfBooks";
    }
    @GetMapping("/listPublisher_BorrowedBooks")
    public String listPublisher_BorrowedBooks(ModelMap model)
    {
        List<String[]> data = conn.query("select  name ,sum(times_borrowed)from book join publisher",
                (row, index) -> {
                    return new String[]{ row.getString("name"), row.getString("sum(times_borrowed)") };
                });

        model.addAttribute("borrowedData", data.toArray(new String[0][1]));

        return "listPublisher_BorrowedBooks";
    }

    @GetMapping("/listOverdue")
    public String listOverdue(ModelMap model)
    {
        List<String[]> data = conn.query("select * from Student join book where student.book_id=book.book_id and book.status= 'Overdue' ",
                (row, index) -> {
                    return new String[]{row.getString("student_id"),row.getString("name"),row.getString("surname"),row.getString("book_id"),row.getString("title"), row.getString("author_name") };
                });

        model.addAttribute("overdueData", data.toArray(new String[0][5]));

        return "listOverdue";
    }
    @GetMapping("/numberofbooksoverduedatthemoment")
    public String numberofbooksoverduedatthemoment(ModelMap model)
    {
        List<String[]> data = conn.query("select count(book.book_id) from Student join book where student.book_id=book.book_id and book.status= 'Overdue'  ",
                (row, index) -> {
                    return new String[]{row.getString("count(book.book_id)") };
                });

        model.addAttribute("sumofoverdued", data.toArray(new String[0][0]));

        return "numberofbooksoverduedatthemoment";
    }
    @GetMapping("/genrewithmostborrowedbooks")
    public String genrewithmostborrowedbooks(ModelMap model)
    {
        List<String[]> data = conn.query("select genre, times_borrowed from book where times_borrowed in (select max(times_borrowed) from book ) ",
                (row, index) -> {
                    return new String[]{row.getString("genre"),row.getString("times_borrowed") };
                });

        model.addAttribute("mostBorrowedData", data.toArray(new String[0][1]));

        return "genrewithmostborrowedbooks";
    }


    @GetMapping("/")
    public String index(ModelMap model)
    {
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(ModelMap model)
    {
        return "login";
    }


}
