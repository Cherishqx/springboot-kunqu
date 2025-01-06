package com.example.demo.Mapper;

import com.example.demo.Model.HelloModel;
import com.example.demo.Model.TicketModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TicketMapper {

    @Insert("INSERT INTO ticketinformation(name, time, place, fare, picName) VALUES(#{name}, #{time}, #{place}, #{fare}, #{picName})")
        //@SelectKey(statement = "SELECT id FROM hello WHERE name = 'hello')", before = false, keyProperty = "id", resultType = int.class)
    int insert(TicketModel model);

    // 根据 ID 查询
    @Select("SELECT * FROM ticketinformation WHERE id=#{id}")
    TicketModel select(int id);

    // 查询全部
    @Select("SELECT * FROM ticketinformation")
    List<TicketModel> selectAll();

//    // 更新 value
//    @Update("UPDATE hello SET text=#{text} WHERE id=#{id}")
//    int updateValue(HelloModel model);

    @Update("UPDATE ticketinformation SET name=#{name}, time=#{time}, place=#{place}, fare=#{fare}, picName=#{picName}  WHERE id=#{id}")
    int updateValue(TicketModel model);

    // 根据 ID 删除
    @Delete("DELETE FROM ticketinformation WHERE id=#{id}")
    int delete(Integer id);
}
