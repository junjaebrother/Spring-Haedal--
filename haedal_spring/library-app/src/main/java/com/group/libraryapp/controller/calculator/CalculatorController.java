package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {
    // 실제 변수로 값을 저장해서 넘겨주는 방식

    //@GetMapping("/add") // GET /add
    /*public int addTwoNumbers(@RequestParam int number1, @RequestParam int number2){ // 인자를 받아올 것을 지정해줌
        return number1 + number2;
    }*/

    // 객체를 통해 전달해주는 방식
    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) { // 쿼리 방식
        return request.getNumber1() + request.getNumber2();
    }

    @PostMapping("/multiply") // POST /multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request) {
        return request.getNumber1() * request.getNumber2();
    }
}
