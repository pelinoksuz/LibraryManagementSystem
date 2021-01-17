package edu.ozu.cs202project.controllers;

import edu.ozu.cs202project.services.BorrowService;

import edu.ozu.cs202project.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        "userBorrowedData","date_start","date_returned","student_id","borrowHistData","student_id"})
public class AppController
{

    @Autowired
    SignUpService signUpService;
   @Autowired
   BorrowService serviceBorrow;
    @Autowired
    JdbcTemplate conn;

    // verifies on app startup that librarian exists
    // if does not exists creates it with username and password "admin"
    @EventListener(ApplicationReadyEvent.class)
    public void ensureLibrarianExists() {
        signUpService.signupLibrarian("admin", "admin", "admin",
                "admin", "admin house", "admin@library.com");
    }

    @GetMapping("/addBook")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String addBook(ModelMap model)
    {
        return "addBook";
    }
    @PostMapping("/addBook")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String addBook(ModelMap model,@RequestParam String genre, @RequestParam String author_name, @RequestParam String title, @RequestParam String status, @RequestParam Date publication_date, @RequestParam int times_borrowed, @RequestParam int pub_id, @RequestParam int lib_id, @RequestParam String requested) {
        model.put("title",title);
        conn.update(
                "INSERT INTO book(genre,author_name,title, status, publication_date,times_borrowed,requested,pub_id,lib_id) values(?,?,?,?,?,?,?,?,?)", genre,author_name,title, status, publication_date,times_borrowed,requested,pub_id,lib_id);
        return "addBook";

    }
    @GetMapping("/deleteBook")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String deleteBook(ModelMap model)
    {
        return "deleteBook";
    }
    @PostMapping("/deleteBook")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
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
        return signUpService.signupStudent(
                name, surname, st_username, st_password
        ) ? "redirect:/studentLogin" : "studentSignup";
    }

    @GetMapping("/PublisherSignUp")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String PublisherSignUp(ModelMap model)
    {
        return "PublisherSignUp";
    }
    @PostMapping("/PublisherSignUp")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String PublisherSignUp(ModelMap model, @RequestParam String name,
                                @RequestParam String pub_username, @RequestParam String pub_password)
    {
        return signUpService.signupPublisher(
                name, pub_username, pub_password)
                ? "redirect:/librarianLogin" : "PublisherSignUp";
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
    public String borrowBook(ModelMap model, @RequestParam int student_id, @RequestParam int book_id)
    {
        LocalDate localDate = LocalDate.now();
        LocalDate date_start = localDate;
        LocalDate date_returned = localDate.plusDays(30);
        model.put("student_id",student_id);
        conn.update(
                "UPDATE book SET status='BORROWED' where book_id ='"+book_id+"'");
        conn.update(
                "INSERT INTO borrowed_by(date_returned, date_start, student_id, book_id ) values(?,?,?,?)", date_start,date_returned, student_id, book_id);

        return "borrowBook";
    }
    @GetMapping("/returnBook")
    public String returnBook(ModelMap model)
    {
        return "returnBook";
    }
    @PostMapping("/returnBook")
    public String returnBook(ModelMap model, @RequestParam int book_id)
    {
        model.put("book_id",book_id);
        conn.update(
                "UPDATE book SET status='PRESENT' where book_id ='"+book_id+"'");

        return "returnBook";
    }

    @GetMapping("/borrowHistory")
    public String borrowHistory(ModelMap model)
    {
        return "borrowHistory";
    }
    @PostMapping("/borrowHistory")
    public String borrowHistory(ModelMap model,@RequestParam String student_id)
    {
        model.put("username",student_id);
        List<String[]> data = conn.query("select name, surname, title, author_name, date_start, date_returned from book join borrowed_by join student where student.student_id = borrowed_by.student_id and book.book_id = borrowed_by.book_id and student.student_id='"+student_id+"'",
                (row, index) -> {
                    return new String[]{ row.getString("name"), row.getString("surname"),row.getString("title"), row.getString("author_name"),row.getString("date_start"), row.getString("date_returned") };
                });

        model.addAttribute("borrowHistData", data.toArray(new String[0][5]));
        return "/listborrowHistory";
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

    @GetMapping("/assignBook")
    public String assignBook(ModelMap model)
    {
        return "assignBook";
    }
    @PostMapping("/assignBook")
    public String assignBook(ModelMap model,@RequestParam int student_id, @RequestParam int book_id)
    {
        model.put("book_id",book_id);
        LocalDate localDate = LocalDate.now();
        LocalDate date_start = localDate;
        LocalDate date_returned = localDate.plusDays(30);
        conn.update(
                "UPDATE book SET status='BORROWED' where book_id ='"+book_id+"'");
        conn.update(
                "INSERT INTO borrowed_by(date_returned, date_start, student_id, book_id ) values(?,?,?,?)", date_start,date_returned, student_id, book_id);

        return "assignBook";
    }
    @GetMapping("/unassignBook")
    public String unassignBook(ModelMap model)
    {
        return "assignBook";
    }
    @PostMapping("/unassignBook")
    public String unassignBook(ModelMap model,@RequestParam int student_id, @RequestParam int book_id)
    {

        conn.update(
                "UPDATE book SET status='PRESENT' where book_id ='"+book_id+"'");

        return "unassignBook";
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
        List<String[]> data = conn.query("select * from book where book.status= 'PRESENT'",
                (row, index) -> {
                    return new String[]{ row.getString("book_id"), row.getString("title"), row.getString("publication_date"), row.getString("author_name"), row.getString("genre") };
                });

        model.addAttribute("bookData", data.toArray(new String[0][5]));

        return "informationOfBooks";
    }
    @GetMapping("/searchBook")
    public String searchBook(ModelMap model)
    {
        return "searchBook";
    }
    @PostMapping("/searchBook")
    public String searchBook(ModelMap model,@RequestParam Date date_returned, @RequestParam Date date_start)
    {
        model.put("date_start", date_start);
        if (!serviceBorrow.isBorrowed(date_returned, date_start))
        {
            model.put("errorMessage", "There are no books borrowed between these dates.");

            return "searchBook";
        }

        return "searchBook";
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
    public String index(Authentication authentication, ModelMap model)
    {
        if (authentication != null) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User)
                            authentication.getPrincipal();
            String roles = loggedInUser.getAuthorities().toString();

            if (roles.contains("STUDENT")) {
                return "redirect:/studentLogin";
            }
            if (roles.contains("PUBLISHER")) {
                return "redirect:/PublisherLogin";
            }
            if (roles.contains("LIBRARIAN")) {
                return "redirect:/librarianLogin";
            }
        }
        return "index";
    }

    @GetMapping("/studentLogin")
    @PreAuthorize("hasAnyAuthority('STUDENT')")
    public String studentLogin(Authentication authentication, ModelMap model)
    {
        if (authentication != null) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User)
                            authentication.getPrincipal();
            model.addAttribute("username", loggedInUser.getUsername());
        }
        return "studentLogin";
    }

    @GetMapping("/PublisherLogin")
    @PreAuthorize("hasAnyAuthority('PUBLISHER')")
    public String PublisherLogin(Authentication authentication, ModelMap model)
    {
        if (authentication != null) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User)
                            authentication.getPrincipal();
            model.addAttribute("pub_username", loggedInUser.getUsername());
        }        return "PublisherLogin";
    }

    @GetMapping("/librarianLogin")
    @PreAuthorize("hasAnyAuthority('LIBRARIAN')")
    public String librarianLogin(Authentication authentication, ModelMap model)
    {
        if (authentication != null) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User)
                            authentication.getPrincipal();
            model.addAttribute("lib_username", loggedInUser.getUsername());
        }        return "librarianLogin";
    }

}
