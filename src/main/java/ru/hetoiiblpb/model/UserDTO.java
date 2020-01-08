package ru.hetoiiblpb.model;

import java.util.Objects;

public class UserDTO {
    private String login;

    private Long id;

//    private String login;

    private String password;

    private String username;

    private String mail;

    private String roles;

    public static UserDTO fromUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setMail(user.getMail());
        userDTO.setRoles(user.getRoles().toString());
        return userDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getId(), userDTO.getId()) &&
                Objects.equals(getLogin(), userDTO.getLogin()) &&
                Objects.equals(getPassword(), userDTO.getPassword()) &&
                Objects.equals(getUsername(), userDTO.getUsername()) &&
                Objects.equals(getMail(), userDTO.getMail()) &&
                Objects.equals(getRoles(), userDTO.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getUsername(), getMail(), getRoles());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDTO{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", mail='").append(mail).append('\'');
        sb.append(", roles='").append(roles).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
