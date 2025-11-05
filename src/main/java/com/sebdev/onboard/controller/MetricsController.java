package com.sebdev.onboard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final JdbcTemplate jdbc;

    public MetricsController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/top-hosts")
    public List<Map<String, Object>> dailyRevenue() {
        String sql = "SELECT * FROM vw_top_50_hosts";
        return jdbc.queryForList(sql);
    }

    @GetMapping("/best-buddies")
    public List<Map<String, Object>> topProducts() {
        String sql = "SELECT * FROM mv_best_buddies";
        return jdbc.queryForList(sql);
    }
}

