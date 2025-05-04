package org.example.booking_place.service;

import lombok.AllArgsConstructor;
import org.example.booking_place.dto.UserRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.User;
import org.example.booking_place.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User singIn(UserRequest userRequest){
        if(userRepository.findUserByEmail(userRequest.getEmail()) != null){
            throw new HttpStatusException("User already exist with email: " + userRequest.getEmail(), HttpStatus.CONFLICT);
        }

        return userRepository.save(userRequest.toUserModel());
    }

    public User logIn(UserRequest userRequest){
        if(userRepository.findUserByEmail(userRequest.getEmail()) == null){
            throw new HttpStatusException("User is not exist with email: " + userRequest.getEmail(), HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findUserByEmail(userRequest.getEmail());

        if(!Objects.equals(user.getPassword(), userRequest.getPassword())){
            throw new HttpStatusException("Incorrect password for email: " + userRequest.getEmail(), HttpStatus.CONFLICT);
        }

        return user;
    }
}
