package life.melon.community.provider;


import com.alibaba.fastjson.JSON;
import life.melon.community.dto.AccessTokenDTO;
import life.melon.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//仅仅将类初始化到Spring容器的上下文
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String[] split1 = split[0].split("=");
            String ans = split1[1];
            System.out.println(string);
            System.out.println(ans);
            return ans;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("超时");
        }
        return null;

    }
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token"+accessToken)
                //.url(String.format("https://api.github.com/user?access_token="+accessToken))
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(str, GitHubUser.class);
            return gitHubUser;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
