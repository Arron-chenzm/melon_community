package life.melon.community.controller;

import life.melon.community.Mapper.QuestionMapper;
import life.melon.community.Mapper.UserMapper;
import life.melon.community.dto.PageDTO;
import life.melon.community.dto.QuestionDTO;
import life.melon.community.model.Question;
import life.melon.community.model.User;
import life.melon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
//允许类去接受前端的请求
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            String token = new String();
            for (Cookie cookie : cookies) {
                if (cookie.equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            User user = userMapper.findByToken(token);
            if (user != null) {
                request.getSession().setAttribute("user", user);
            }
        }
        PageDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";
    }

}
