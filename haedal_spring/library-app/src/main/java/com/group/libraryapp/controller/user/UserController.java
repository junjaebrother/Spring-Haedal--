package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.fruit.AppleService;
import com.group.libraryapp.service.fruit.FruitService;
import com.group.libraryapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // UserController 클래스를 진입 지점으로 만들어 주는 것 뿐만 아니라 Sptring bin에 등록시켜준다!
public class UserController {

    private final UserService userService;
    private final FruitService fruitService;

    public UserController(UserService userService, @Qualifier("appleService") FruitService fruitService){
        this.userService = userService;
        this.fruitService = fruitService;
    }
    // private final List<User> users = new ArrayList<>();
    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }


//    @GetMapping("/fruit")
//    public Fruit fruit() { // 겟터를 자동으로 인식해서 JSON형식으로 반환해준다. (RestController 어노테이션 때문)
//        return new Fruit("바나나", 2000);
//    }
}
