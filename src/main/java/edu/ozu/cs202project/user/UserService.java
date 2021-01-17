package edu.ozu.cs202project.user;

import java.util.List;
import java.util.Optional;

import edu.ozu.cs202project.exception.LMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    JdbcTemplate conn;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findOneByUserName(username);

        User user = conn.queryForObject("select * from user where user_name = ?", new Object[]{username},
                new UserRowMapper());

        if (user == null) {
            throw new UsernameNotFoundException("Invalid user or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
            user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
    }

    public User get(Long id) {
//        Optional<User> user = userRepository.findById(id);

        User user = conn.queryForObject("select * from user where id = ?", new Object[]{id},
                new UserRowMapper());

        if (user == null) {
            throw new LMSException("404", "User not found!");
        }
        return user;
    }

    public List<User> getAll() {
//        return userRepository.findAll();
         return conn.query("select * from user",
                new UserRowMapper());
    }

    public User getByUserName(String username) {
//        Optional<User> user = userRepository.findOneByUserName(username);

        User user = conn.queryForObject("select * from user where user_name = ?", new Object[]{username},
                new UserRowMapper());

        if (user == null) {
            throw new LMSException("404", "User not found!");
        }
        return user;
    }

    public User create(User user) {

        if (user.getUserName() == null) {
            throw new LMSException("400", "User Name not provided!");
        }
        Integer usersByUserName = conn.queryForObject("select count(*) from user where user_name = ?", new Object[]{user.getUserName()}, Integer.class );
        if (usersByUserName > 0) {
            throw new LMSException("409", "User Name already exists!");
        }

        conn.update(
                "INSERT INTO user (user_name, password, roles) values (?, ?, ?)",
                user.getUserName(), user.getPassword(), user.getRoles()
        );

        return user;
    }

    public User update(Long id, User updatedUser) {

        User user = get(id);

        if (updatedUser.getUserName() == null) {
            throw new LMSException("400", "User Name not provided!");
        }
        Integer usersByUserName = conn.queryForObject("select count(*) from user where user_name = ?", new Object[]{user.getUserName()}, Integer.class );
        if (usersByUserName > 0) {
            throw new LMSException("409", "User Name already exists!");
        }

        user.setUserName(updatedUser.getUserName());
        user.setPassword(updatedUser.getPassword());
        user.setRoles(updatedUser.getRoles());

        conn.update(
                "INSERT INTO user (user_name, password, roles) values (?, ?, ?)",
                user.getUserName(), user.getPassword(), user.getRoles()
        );

        return user;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        Object[] args = new Object[] {id};

        conn.update(sql, args);
    }

    public boolean isGetAuthorized(Authentication authentication, Long id) {

        org.springframework.security.core.userdetails.User loggedInUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String userName = loggedInUser.getUsername();

        Long loggedInUserId = getByUserName(userName).getId();

        return loggedInUserId.equals(id);

    }


}
