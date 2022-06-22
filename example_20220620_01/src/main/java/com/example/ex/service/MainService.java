package com.example.ex.service;

import org.springframework.stereotype.Service;

@Service
public class MainService {
    public String req1() {
        System.out.println("MainService.req1");
        return "방문 인증";
    }
}
