package com.example.demo.Mapper;

import com.example.demo.Model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    // 插入 并查询id 赋给传入的对象
    @Insert("INSERT INTO allusers(email, password) VALUES(#{email}, #{password})")
    //@SelectKey(statement = "SELECT id FROM hello WHERE name = 'hello')", before = false, keyProperty = "id", resultType = int.class)
    int insert(UserModel model);

//    // 插入 并查询id 赋给传入的对象
//    @Insert("INSERT INTO hello(title, text) VALUES(#{title}, #{text})")
//    @SelectKey(statement = "SELECT last_insert_rowid()", before = false, keyProperty = "id", resultType = int.class)
//    int insert(HelloModel model);

    // 根据 ID 查询
    @Select("SELECT * FROM allusers WHERE id=#{id}")
    UserModel select(int id);

    //根据email查询email
    @Select("SELECT * FROM allusers WHERE email = #{email}")
    UserModel selectByEmail(String email);


    // 查询全部
    @Select("SELECT * FROM allusers")
    List<UserModel> selectAll();

    @Select("SELECT password FROM allusers WHERE email=#{email}")
    String selectPasswordByEmail(String email);

//    // 更新 value
//    @Update("UPDATE hello SET text=#{text} WHERE id=#{id}")
//    int updateValue(HelloModel model);

    @Update("UPDATE allusers SET email=#{email}, password=#{password} WHERE id=#{id}")
    int updateValue(UserModel model);

    // 根据 ID 删除
    @Delete("DELETE FROM allusers WHERE id=#{id}")
    int delete(Integer id);

}