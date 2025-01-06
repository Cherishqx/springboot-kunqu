package com.example.demo.Service;

import com.example.demo.Mapper.TicketMapper;
import com.example.demo.Model.TicketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketMapper dao;

    @Autowired
    public TicketService(TicketMapper dao) {
        this.dao = dao;
    }

    public boolean insert(TicketModel model) {
        return dao.insert(model) > 0;
    }

    public TicketModel select(int id) {
        return dao.select(id);
    }

    public List<TicketModel> selectAll() {
        return dao.selectAll();
    }

//    public boolean updateValue(HelloModel model) {
//        return dao.updateValue(model) > 0;
//    }

    public boolean delete(Integer id) {
        return dao.delete(id) > 0;
    }

    public boolean updateValue(TicketModel model) {
        return dao.updateValue(model) > 0;
    }
}
