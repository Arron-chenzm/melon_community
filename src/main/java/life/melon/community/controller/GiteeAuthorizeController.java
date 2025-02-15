package life.melon.community.controller;

import com.sun.deploy.net.HttpResponse;
import life.melon.community.Mapper.UserMapper;
import life.melon.community.dto.AccessTokenDTO;
import life.melon.community.dto.GitHubUser;
import life.melon.community.dto.GiteeAccessTokenDTO;
import life.melon.community.dto.GiteeUser;
import life.melon.community.model.User;
import life.melon.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class GiteeAuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/callback_gitee")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response){
        //System.out.println(code);
        GiteeAccessTokenDTO dto = new GiteeAccessTokenDTO();
        dto.setGrant_type("authorization_code");
        dto.setCode(code);
        dto.setClient_id("3471f020afadb9c066b8e7dbaa4051f8d24e865c468b8eac7eb85e03b907574a");
        dto.setClient_secret("febf916857c32aabceb883bf484d942a2920f869ce966a9dde844390e642d9cf");
        dto.setRedirect_uri("http://localhost:8887/callback_gitee");
        String accessToken = giteeProvider.getAccessToken(dto);
        GiteeUser user = giteeProvider.getUser(accessToken);
        if (user!=null){
            request.getSession().setAttribute("user", user);
            User user1 = new User();
            //user1.setToken(accessToken);
            String token = UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setAccountId(String.valueOf(user.getId()));
            user1.setName(user.getName());
            user1.setGmtcreate(System.currentTimeMillis());
            user1.setGmtmodified(user1.getGmtcreate());
            user1.setAvatarurl(user.getAvatarurl());
            userMapper.insert(user1);
            response.addCookie(new Cookie("token", token));
            //登录成功，写cookie和session
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";
        }
    }


}
