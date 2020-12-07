package org.example.entity;

import java.util.List;

public class Profile {

    private Long chatId;
    private List<String> musicCategories;

    public Profile(Long chatId, List<String> musicCategories) {
        this.chatId = chatId;
        this.musicCategories = musicCategories;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<String> getMusicCategories() {
        return musicCategories;
    }

    public void setMusicCategories(List<String> musicCategories) {
        this.musicCategories = musicCategories;
    }
}
