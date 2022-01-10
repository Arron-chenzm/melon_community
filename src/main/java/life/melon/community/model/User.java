package life.melon.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtcreate;
    private Long gmtmodified;
    private String avatarurl;
}
