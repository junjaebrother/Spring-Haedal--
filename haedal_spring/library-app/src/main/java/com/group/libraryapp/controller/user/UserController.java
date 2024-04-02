package com.group.libraryapp.controller.user;

import ch.qos.logback.core.BasicStatusManager;
import com.group.libraryapp.controller.domain.Fruit;
import com.group.libraryapp.controller.domain.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final List<User> users = new ArrayList<>();
    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        users.add(new User(request.getName(), request.getAge()));
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        List<UserResponse> responses = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            responses.add(new UserResponse(i+1, users.get(i)));
        }
        return responses;
    }
//    @GetMapping("/fruit")
//    public Fruit fruit() { // 겟터를 자동으로 인식해서 JSON형식으로 반환해준다. (RestController 어노테이션 때문)
//        return new Fruit("바나나", 2000);
//    }
}
