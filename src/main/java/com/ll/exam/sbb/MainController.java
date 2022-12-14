package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MainController {
    private int lastId = 0;

    @RequestMapping("/sbb")
    // 아래 함수의 리턴값을 그대로 브라우저에 표시
    // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답의 바디에 담는다.
    @ResponseBody
    public String index() {
        // 서버에서 출력
        System.out.println("Hello");
        // 먼 미래에 브라우저에서 보여짐
        return "안녕하세요.";
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이" />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, POST 방식으로 오셨군요.</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요, Get 방식으로 오셨군요.</h1>
                """.formatted(age);
    }
//
//    @GetMapping("/plus")
//    @ResponseBody
//    public String showPlus(int a, int b) {
//        return """
//                %d
//                """.formatted(a + b);
//    }

    @GetMapping("/minus")
    @ResponseBody
    public String showMinus(int a, int b) {
        return """
                %d
                """.formatted(a - b);
    }

    @GetMapping("/increase")
    @ResponseBody
    public String showIncrease() {
        int id = lastId++;
        return "%d".formatted(id);
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(int dan, int limit) {

        for(int i=1; i<= limit; i++){
            return """
                
                %d * %d = %d
                                
                """.formatted(dan, i, dan * i);
        }

        return "0";
    }

    @GetMapping("/plus")
    @ResponseBody
    public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        resp.getWriter().append(a+b+"");
        }

    //# 문제 6
    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value , HttpServletRequest req) {

        // 세션 선언
        HttpSession session  = req.getSession();

            session.setAttribute(name,value);

        return "세션변수 %s의 값이 %s로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession (@PathVariable String name ,HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값이 %s 입니다.".formatted(name,value);
    }
}