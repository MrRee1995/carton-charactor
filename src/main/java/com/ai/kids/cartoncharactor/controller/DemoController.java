package com.ai.kids.cartoncharactor.controller;

import com.ai.kids.cartoncharactor.entity.User;
import com.ai.kids.cartoncharactor.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class DemoController {

    @Autowired
    RedisService redisService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello/{myName}")
    String index(@PathVariable String myName) {

        redisService.set("loginName", myName, 1000L);
        return "Hello "+myName+"!!!";
    }

    @RequestMapping("/test/jdbc")
    public String userList(ModelMap map) {
        String sql = "SELECT * FROM user";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setAge(rs.getString("age"));
                return user;
            }
        });
        map.addAttribute("users", userList);
        return "user";
    }
}
