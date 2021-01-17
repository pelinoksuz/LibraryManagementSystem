package edu.ozu.cs202project.user;

public class UserDto {

    private Long id;
    private String userName;
    private String password;    // no getter for password
    private String roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public User generateUser() {

        User user = new User();

        user.setId(this.id);
        user.setUserName(this.userName);
        user.setPassword(this.password);
        user.setRoles(this.roles);

        return user;
    }
}
