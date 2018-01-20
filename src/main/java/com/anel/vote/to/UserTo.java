package com.anel.vote.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by PeucT on 14.01.2018.
 */
public class UserTo extends BaseTo {
    /*private static final long serialVersionUID = 1L;*/

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @Size(min = 5, max = 32, message = "length must between 5 and 32 characters")
    private String password;


    public UserTo() {
    }

    public UserTo(Integer id, String name, String nickname, String password) {
        super(id);
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
