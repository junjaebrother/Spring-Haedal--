package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primary키를 자동으로 생성해준다
    private Long id = null;

    @Column(nullable = false, length = 20) // name varchar(20)
    private String name;
    private Integer age; // 널이 들어가도 상관없고, 길이 제한도 없고 이름도 같으니 굳이 @Column사용할 필요가 없다

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //연관관계의 주인이 아니라서 MappedBy해줘야함 + User만 삭제해도 대출기록도 삭제해줌 + ophanRemoval때문에 연관관계가 끊어지기만 하면 DB에서도 삭제해줌
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    protected User() {} // JPA를 사용하기 위해서는 기본 생성자가 반드시 필요하다!

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();
    }
}
