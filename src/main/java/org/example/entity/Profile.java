package org.example.entity;

import org.apache.commons.codec.binary.StringUtils;

import java.util.List;

public class Profile {

    private Long chatId;
    private String password;
    private String username;

    public Profile(Long chatId, String password, String username) {
        this.chatId = chatId;

        this.password = password;
        this.username = username;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername(){return username;}
    public void setUsername(String username){this.username = username;}

    @Override
    public String toString() {
        return "Profile{" +
                "chatId=" + chatId +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
