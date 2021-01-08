package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.services.AddBookService;
import edu.ozu.cs202project.services.LoginService;
import edu.ozu.cs202project.services.SignUpService;
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
import java.util.List;

@Controller
@SessionAttributes({ "username", "level", "bookData","lib_username","pub_username","title","overdueData","borrowedData" })
public class AppController
{
    @Autowired
    LoginService service;
    @Autowired
    SignUpService signUpService;
    @Autowired
    AddBookService addBookService;
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
    public String addBook(ModelMap model, @RequestParam String genre, @RequestParam String author_name, @RequestParam String title, @RequestParam String status, @RequestParam int times_borrowed, @RequestParam int penaltyinfo, @RequestParam String requested)
    {
        if (!addBookService.addBook(genre, author_name, title, status, times_borrowed, penaltyinfo, requested))
        {
            return "addBook";
        }
        model.put("title", title);
        return "addBook";
    }
    @GetMapping("/signup")
    public String signup(ModelMap model)
    {
        return "signup";
    }
    @PostMapping("/signup")
    public String signup(@RequestParam String name, @RequestParam String surname, @RequestParam String st_username, @RequestParam String st_password, @RequestParam String address, @RequestParam String email)
    {
        if (signUpService.signupStudent(name, surname, st_username, st_password, address, email))
        {
            return "studentPage";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, WebRequest request, SessionStatus session)
    {
        session.setComplete();
        request.removeAttribute("username", WebRequest.SCOPE_SESSION);

        return "redirect:/login";
    }

    @GetMapping("/informationOfBooks")
    public String informationOfBooks(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM book",
                (row, index) -> {
                    return new String[]{ row.getString("book_id"), row.getString("title"), row.getString("publication_date"), row.getString("penaltyinfo"), row.getString("author_name"), row.getString("genre") };
                });

        model.addAttribute("bookData", data.toArray(new String[0][6]));

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
                    return new String[]{row.getString("student_id"),row.getString("name"),row.getString("surname"),row.getString("book_id"),row.getString("title"), row.getString("penaltyinfo"), row.getString("author_name") };
                });

        model.addAttribute("overdueData", data.toArray(new String[0][6]));

        return "listOverdue";
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


    @GetMapping("/studentSignUp")
    public String studentSignUp(ModelMap model)
    {
        return "studentSignUp";
    }
    @GetMapping("/LibrarianSignUp")
    public String LibrarianSignUp(ModelMap model)
    {
        return "LibrarianSignUp";
    }
    @GetMapping("/PublisherSignUp")
    public String PublisherSignUp(ModelMap model)
    {
        return "PublisherSignUp";
    }
}
