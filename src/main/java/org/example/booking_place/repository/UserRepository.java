package org.example.booking_place.repository;

import org.example.booking_place.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    boolean existsUserById(Integer id);

    void deleteUserById(Integer id);
}
