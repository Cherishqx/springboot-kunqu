package com.example.demo.Mapper;

import com.example.demo.Model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HelloMapper {

    // 插入 并查询id 赋给传入的对象
    @Insert("INSERT INTO hello(title, text) VALUES(#{title}, #{text})")
    //@SelectKey(statement = "SELECT id FROM hello WHERE name = 'hello')", before = false, keyProperty = "id", resultType = int.class)
    int insert(HelloModel model);

//    // 插入 并查询id 赋给传入的对象
//    @Insert("INSERT INTO hello(title, text) VALUES(#{title}, #{text})")
//    @SelectKey(statement = "SELECT last_insert_rowid()", before = false, keyProperty = "id", resultType = int.class)
//    int insert(HelloModel model);

    // 根据 ID 查询
    @Select("SELECT * FROM hello WHERE id=#{id}")
    HelloModel select(int id);

    // 查询全部
    @Select("SELECT * FROM hello")
    List<HelloModel> selectAll();

//    // 更新 value
//    @Update("UPDATE hello SET text=#{text} WHERE id=#{id}")
//    int updateValue(HelloModel model);

    @Update("UPDATE hello SET title=#{title}, text=#{text} WHERE id=#{id}")
    int updateValue(HelloModel model);

    // 根据 ID 删除
    @Delete("DELETE FROM hello WHERE id=#{id}")
    int delete(Integer id);

}