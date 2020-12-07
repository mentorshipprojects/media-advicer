package org.example.service;

import org.example.entity.Profile;
import org.example.repository.ProfileRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(Profile profile) {
        Profile savedProfile = profileRepository.get(profile.getChatId());
        if (null != savedProfile) {
            return;
        } else {
            profileRepository.save(profile);
        }
    }

    @Override
    public Profile getProfileByChatId(Long chatId) {
        return profileRepository.get(chatId);
    }


}