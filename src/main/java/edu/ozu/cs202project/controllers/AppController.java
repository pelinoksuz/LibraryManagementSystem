package edu.ozu.cs202project.controllers;

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

import java.util.List;

@Controller
@SessionAttributes({ "username", "level", "itemData" })
public class AppController
{
    @Autowired
    LoginService service;
    @Autowired
    SignUpService signUpService;
    @Autowired
    JdbcTemplate conn;

    @GetMapping("/")
    public String index(ModelMap model)
    {
        return "index";
    }
    @GetMapping("/signup")
    public String signup(ModelMap model)
    {
        return "signup";
    }
    @GetMapping("/login")
    public String loginPage(ModelMap model)
    {
        return "login";
    }

    @PostMapping("/login")
    public String login(ModelMap model, @RequestParam String username, @RequestParam String password)
    {
        //password = Salter.salt(password, "CS202Project");

        if (!service.validate(username, password))
        {
            model.put("errorMessage", "Invalid Credentials");

            return "login";
        }

        model.put("username", username);

        return "login";
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

    @GetMapping("/list")
    public String list(ModelMap model)
    {
        List<String[]> data = conn.query("SELECT * FROM items",
                (row, index) -> {
                    return new String[]{ row.getString("item_name"), row.getString("item_value") };
                });

        model.addAttribute("itemData", data.toArray(new String[0][2]));

        return "list";
    }
}
