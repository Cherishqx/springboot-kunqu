package com.example.demo.Controller;
import com.example.demo.Model.HelloModel;
import com.example.demo.Model.ReqBody;
import com.example.demo.Service.HelloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.security.ssl.Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

@Controller
public class HelloWorld {
    private final HelloService helloService;

    @Autowired
    public HelloWorld(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/index")
    public String index() {
        // 返回视图名称，Spring MVC会找到对应的index.html文件并渲染
        return "index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        // 从服务层获取所有记录
        List<HelloModel> helloModels = helloService.selectAll();
        // 将数据添加到模型中
        model.addAttribute("helloModels", helloModels);
        return "list"; // 返回视图名称
    }

    @GetMapping("/selectall")
    public ResponseEntity<List<HelloModel>> selectAll() {
        List<HelloModel> helloModels = helloService.selectAll();
        return ResponseEntity.ok(helloModels);
    }

    @PostMapping("/post")
    public String post(@RequestBody ReqBody reqBody) {
        return "输入的姓名是" + reqBody.getName() + ",电子邮件是:" + reqBody.getEmail();
    }

    // 添加空记录
    @PostMapping("/addEmptyRecord")
    public String addEmptyRecord(@RequestBody HelloModel helloModel) {
        helloModel.setText("");
        helloModel.setTitle("");
        boolean updated = helloService.insert(helloModel);
        if (updated) {
            return "请输入数据";
        } else {
            return "失败";
        }
    }

    @PutMapping("/updateRecord/{id}")
    public ResponseEntity<String> updateRecord(@PathVariable Integer id, @RequestBody HelloModel helloModel) {
        helloModel.setId(id); // 确保模型包含ID
        boolean updated = helloService.updateValue(helloModel);
        if (updated) {
            return ResponseEntity.ok("记录已更新！");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("记录更新失败，ID不存在！");
        }
    }


    // New delete method, responding to AJAX DELETE request
    @DeleteMapping("/deleteRecord/{id}")
    @ResponseBody // Marks the method to return a response directly as JSON or plain text
    public String deleteRecord(@PathVariable Integer id) {
        try {
            helloService.delete(id); // Call the service to delete the record
            return "删除成功";
        } catch (Exception e) {
            return "删除失败";
        }
    }

    @GetMapping("/add")
    public String showAddRecordForm(Model model) {
        model.addAttribute("helloModel", new HelloModel()); // Optional, depending on your form structure
        return "add"; // This is the name of the HTML template for adding a record
    }

    // Method to handle form submission for adding a new record
    @PostMapping("/add")
    public String addRecord(@ModelAttribute HelloModel helloModel) {
        // Save the new record (you can save it to the database or any other service)
        helloService.insert(helloModel);
        return "redirect:/list"; // After adding, redirect to the list of records
    }
}