package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// @Repositroy를 안붙여줘도 상속을 받는것 만으로 빈으로 들어옴
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name); // 이 함수이름을 통해서 자동으로 SQL이 조립된다!
                                // By 뒤의 이름으로 WHERE 뒤의 변수 이름이 정해진다
    // boolean existsByName(String name);

    // long countByAge(Integer age);

    List<User> findAllByAgeBetween(int start, int end); // start, end 사이의 나이를 가진 모든 User를 반환해줌

}
