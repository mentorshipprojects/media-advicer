package org.example.service;

import org.example.entity.Profile;

/**
 * Profile Service.
 */
public interface ProfileService {
    void createProfile(Profile profile);
    Profile getProfileByChatId(Long chatId);
}
