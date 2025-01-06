package com.example.demo.Controller;

import com.example.demo.Model.HelloModel;
import com.example.demo.Model.TicketModel;
import com.example.demo.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketController {
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





}
