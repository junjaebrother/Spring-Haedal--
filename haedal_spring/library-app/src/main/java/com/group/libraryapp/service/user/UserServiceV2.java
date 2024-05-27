package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;


    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아래 있는 함수가 시작될 때 start transaction;을 해준다(트랜잭션을 시작!)
    // 함수가 예외 없이 잘 끝났다면 commit을 자동으로
    // 혹시라도 문제가 있다면 rollback
    @Transactional // -> 함수를 한 몸으로 만들어준다!
    public void saveUser(UserCreateRequest request) {
        User u = userRepository.save(new User(request.getName(), request.getAge()));
        // throw new IllegalArgumentException();
        // throw new IOException(); // -> 이건 롤백이 되지 않음
        // u.getId();
    } // save 메소드에 객체를 넣어주면 자동으로 INSERT가 날라간다

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request){
        // select * from user where id = ?
        // Optinal<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        // userRepository.save(user);
        // save를 해주지 않더라도 자동 감지해서 업데이트 해준다. user객체가 Entity인데 이게 바뀌기 때문!
    }

    @Transactional
    public void deleteUser(String name){
         // userRepository.findByName -> 이딴건 없어 따라서 따로 우리가 인터페이스에 직접 구현해주어야 함!
         User user = userRepository.findByName(name)
                        .orElseThrow(IllegalArgumentException::new);
         userRepository.delete(user);
//        if(!userRepository.existsByName(name)){
//            throw new IllegalArgumentException();
//        }
//
//        User user = userRepository.findByName(name);
//        userRepository.delete(user);

    }
}
