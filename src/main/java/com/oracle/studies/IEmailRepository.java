package com.oracle.studies;

import org.springframework.data.repository.CrudRepository;

public interface IEmailRepository extends CrudRepository<Email, Long> {

    Email findEmailByName(String firstname, String lastname);

    /**
     * Saves all names
     * @return
     */
    void saveAllUsers(Iterable<User> iterable);
}
