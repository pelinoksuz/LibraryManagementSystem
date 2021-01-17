package edu.ozu.cs202project.user;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<Map<String, ?>> getAll() {

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> responseHeader = new HashMap<>();
        Map<String, Object> pagination = new HashMap<>();

        response.put("responseHeader", responseHeader);
        responseHeader.put("pagination", pagination);

        List<User> users;
        List<UserDto> responseBody = new LinkedList<>();

        users = userService.getAll();

        for (User user : users) {
            responseBody.add(user.generateUserDto());
        }

        response.put("responseBody", responseBody);
        pagination.put("totalCount", responseBody.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT') or " +
            "(  hasAnyAuthority('CUSTOMER') and " +
            "   @userService.isGetAuthorized(authentication, #id)" +
            ")"
    )
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id) {

        User userOptional = userService.get(id);
        return ResponseEntity.ok(userOptional.generateUserDto());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserDto> create(@RequestBody UserDto newUserDto) {

        User user = userService.create(newUserDto.generateUser());
        return ResponseEntity.ok(user.generateUserDto());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id,
                                          @RequestBody UserDto updatedUserDto) {

        User userOptional =
                userService.update(id, updatedUserDto.generateUser());
        return ResponseEntity.ok(userOptional.generateUserDto());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserDto> delete(@PathVariable("id") Long id) {

        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
