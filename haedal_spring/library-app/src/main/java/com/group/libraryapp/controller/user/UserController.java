package com.group.libraryapp.controller.user;

import ch.qos.logback.core.BasicStatusManager;
import com.group.libraryapp.controller.domain.Fruit;
import com.group.libraryapp.controller.domain.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    // private final List<User> users = new ArrayList<>();
    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> { // rowNum은 어디에 쓰이는것?
            long id = rs.getLong("id"); // 어떻게 rs에 내가 원하는 값들이 들어오게 됐는지
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        String readSql = "SELECT * FROM user WHERE id = ?"; // 밑에 람다식 이해 안됨..
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
        if (isUserNotExist){
            throw new IllegalArgumentException();
        }
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        String readSql = "SELECT * FROM user WHERE name = ?"; // 밑에 람다식 이해 안됨..
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
        if (isUserNotExist){
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }


//    @GetMapping("/fruit")
//    public Fruit fruit() { // 겟터를 자동으로 인식해서 JSON형식으로 반환해준다. (RestController 어노테이션 때문)
//        return new Fruit("바나나", 2000);
//    }
}
