package cn.com.wenjin.miaosha.dao;

import cn.com.wenjin.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: mymiaosha
 * @description:
 * @author: wen jin
 * @create: 2019-07-24 15:48
 */
@Mapper
public interface Userdao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("INSERT INTO user (id,name) VALUES(#{id},#{name})")
    public int  insert(User user);


}