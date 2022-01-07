package life.melon.community.controller;

import life.melon.community.dto.AccessTokenDTO;
import life.melon.community.dto.GitHubUser;
import life.melon.community.dto.GiteeAccessTokenDTO;
import life.melon.community.dto.GiteeUser;
import life.melon.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GiteeAuthorizeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @GetMapping("/callback_gitee")


    public String callback(@RequestParam(name = "code") String code){
        //System.out.println(code);
        GiteeAccessTokenDTO dto = new GiteeAccessTokenDTO();
        dto.setGrant_type("authorization_code");
        dto.setCode(code);
        dto.setClient_id("3471f020afadb9c066b8e7dbaa4051f8d24e865c468b8eac7eb85e03b907574a");
        dto.setClient_secret("febf916857c32aabceb883bf484d942a2920f869ce966a9dde844390e642d9cf");
        dto.setRedirect_uri("http://localhost:8887/callback_gitee");
        String accessToken = giteeProvider.getAccessToken(dto);
        GiteeUser user = giteeProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
