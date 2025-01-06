package com.example.demo.Service;


import com.example.demo.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Mapper.UserMapper;

import java.util.List;

@Service
public class UserService {
    private final UserMapper dao;

    @Autowired
    public UserService(UserMapper dao) {
        this.dao = dao;
    }

    public boolean insert(UserModel model) {
        return dao.insert(model) > 0;
    }

    public UserModel select(int id) {
        return dao.select(id);
    }

    public List<UserModel> selectAll() {
        return dao.selectAll();
    }

    public String selectPassword(String email) {
        return dao.selectPasswordByEmail(email);
    }

//    public boolean updateValue(HelloModel model) {
//        return dao.updateValue(model) > 0;
//    }

    public boolean delete(Integer id) {
        return dao.delete(id) > 0;
    }

    public boolean updateValue(UserModel model) {
        return dao.updateValue(model) > 0;
    }

    public boolean emailExists(String email) {
        UserModel user = dao.selectByEmail(email);
        return user != null;  // Returns true if the email exists
    }
}
