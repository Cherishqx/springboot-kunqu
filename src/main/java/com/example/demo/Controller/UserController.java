package com.example.demo.Controller;
import com.example.demo.Model.HelloModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 显示用户列表
    @GetMapping("/allusers/list")
    public String list(Model model) {
        List<UserModel> userModels = userService.selectAll();
        model.addAttribute("userModels", userModels);
        return "allusers/list"; // 返回视图名称
    }

    // 添加空记录
    @PostMapping("/allusers/addEmptyRecord")
    public void addEmptyRecord(@RequestBody UserModel userModel) {
        userService.insert(userModel);
    }
    //

    //添加
    @PostMapping("/allusers/add")
    public ResponseEntity<String> add(@RequestBody UserModel userModel) {
        boolean emailExists = userService.emailExists(userModel.getEmail());

        if (emailExists) {
            // If email exists, return an error response with a suitable message
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
        } else {
            // If email doesn't exist, proceed to add the user
            userService.insert(userModel);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    // 获取密码
    @PostMapping("/allusers/get")
    public ResponseEntity<String> get(@RequestParam String email) {
        String password = userService.selectPassword(email);
        if (password == null) {
            return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST); // If no user found, return 404
        }
        return new ResponseEntity<>(password, HttpStatus.OK); // If user found, return password with 200 OK status
    }


//    // 显示添加用户表单
//    @GetMapping("/allusers/add")
//    public String showAddRecordForm(Model model) {
//        model.addAttribute("userModel", new UserModel()); // 创建一个空的UserModel用于表单
//        return "allusers/add"; // 添加用户的表单页面
//    }
//
//    // 处理添加新用户请求
//    @PostMapping("/allusers/add")
//    public String addRecord(@ModelAttribute UserModel userModel) {
//        userService.insert(userModel);
//        return "redirect:/allusers/list"; // 添加后重定向到用户列表
//    }

    // 更新用户
    @PutMapping("/allusers/updateRecord/{id}")
    public ResponseEntity<String> updateRecord(@PathVariable Integer id, @RequestBody UserModel userModel) {
        userModel.setId(id); // 设置ID
        boolean updated = userService.updateValue(userModel);
        if (updated) {
            return ResponseEntity.ok("用户记录已更新！");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户更新失败，ID不存在！");
        }
    }

    // 删除用户
    @DeleteMapping("/allusers/deleteRecord/{id}")
    @ResponseBody
    public String deleteRecord(@PathVariable Integer id) {
        try {
            userService.delete(id);
            return "用户删除成功";
        } catch (Exception e) {
            return "用户删除失败";
        }
    }
}
