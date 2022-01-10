package life.melon.community.controller;

import life.melon.community.Mapper.QuestionMapper;
import life.melon.community.Mapper.UserMapper;
import life.melon.community.dto.PageDTO;
import life.melon.community.model.User;
import life.melon.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size) {
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
        } else if ("repies".equals(action)) {
            model.addAttribute("section", "repies");
            model.addAttribute("sectionName", "最新回复");
        }

        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length != 0) {
            String token = new String();
            for (Cookie cookie : cookies) {
                if (cookie.equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            user = userMapper.findByToken(token);
            if (user != null) {
                request.getSession().setAttribute("user", user);
            } else {
//                return "redirect:/";
            }
        }
        PageDTO pageDTO = questionService.list(user.getAccountId(), page, size);
        model.addAttribute("pagination", pageDTO);
            return "profile";
        }

}
