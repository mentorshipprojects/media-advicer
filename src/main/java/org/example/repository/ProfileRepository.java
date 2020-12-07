package org.example.repository;

import org.example.entity.Profile;

/**
 * Interface for CRUD operations.
 */
public interface ProfileRepository {
    // save = update = create
    void save (Profile profile);
    // delete
    void delete (Long chatId);
    // read
    Profile get(Long chatId);
}