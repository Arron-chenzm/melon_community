package life.melon.community.Mapper;

import life.melon.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {

    @Insert("insert into user(name,accountId,token,gmtcreate,gmtmodified,avatarurl) values(#{name},#{accountId},#{token},#{gmtcreate},#{gmtmodified},#{avatarurl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id")Integer id);
}
