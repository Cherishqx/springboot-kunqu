package com.example.demo.Controller;

import com.example.demo.Model.HelloModel;
import com.example.demo.Model.TicketModel;
import com.example.demo.Service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class TicketController {
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticketinformation/list")
    public String list(Model model) {
        // 从服务层获取所有记录
        List<TicketModel> ticketModels = ticketService.selectAll();
        // 将数据添加到模型中
        model.addAttribute("ticketModels", ticketModels);
        return "/ticketinformation/list"; // 返回视图名称
    }
    @GetMapping("/ticketinformation/selectall")
    public ResponseEntity<List<TicketModel>> selectAll() {
        List<TicketModel> ticketModels = ticketService.selectAll();
        return ResponseEntity.ok(ticketModels);
    }

    // 添加空记录
    @PostMapping("/ticketinformation/addEmptyRecord")
    public String addEmptyRecord(@RequestBody TicketModel ticketModel) {
        ticketModel.setName("");
        ticketModel.setTime("");
        ticketModel.setPlace("");
        ticketModel.setFare("");
        ticketModel.setPicName("");
        boolean updated = ticketService.insert(ticketModel);
        if (updated) {
            return "请输入数据";
        } else {
            return "失败";
        }
    }

    @PutMapping("/ticketinformation/updateRecord/{id}")
    public ResponseEntity<String> updateRecord(@PathVariable Integer id, @RequestBody TicketModel ticketModel) {
        ticketModel.setId(id); // 确保模型包含ID
        boolean updated = ticketService.updateValue(ticketModel);
        if (updated) {
            return ResponseEntity.ok("记录已更新！");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("记录更新失败，ID不存在！");
        }
    }

    @DeleteMapping("/ticketinformation/deleteRecord/{id}")
    @ResponseBody // Marks the method to return a response directly as JSON or plain text
    public String deleteRecord(@PathVariable Integer id) {
        try {
            ticketService.delete(id); // Call the service to delete the record
            return "删除成功";
        } catch (Exception e) {
            return "删除失败";
        }
    }

//    @PostMapping("/ticketinformation/uploadImage")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件不能为空");
//        }
//
//        // Define the upload path (e.g., "uploads/" directory in your project)
//        Path uploadPath = Paths.get("uploads");
//        if (!Files.exists(uploadPath)) {
//            try {
//                Files.createDirectories(uploadPath); // Create the directory if it doesn't exist
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("无法创建上传目录");
//            }
//        }
//
//        // Get the original filename
//        String fileName = file.getOriginalFilename();
//        Path filePath = uploadPath.resolve(fileName);
//
//        try {
//            // Save the file to the server
//            file.transferTo(filePath.toFile());
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
//        }
//
//        return ResponseEntity.ok("文件上传成功");
//    }
    @PostMapping("/ticketinformation/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        String ext = "." + originalFilename.split("\\.")[1];
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + ext;
        //String relativePath = "src/main/resources/static/images/" + fileName;  // 文件保存在相对于项目根目录的路径
        //File destinationFile = new File(relativePath);

        //Use the proper application path for image storage
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String basePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\images\\";
        String path = basePath + fileName;

        try {
            file.transferTo(new File(path)); // Save the file
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }

        return ResponseEntity.ok(fileName);  // Return the file path to frontend
    }


}
