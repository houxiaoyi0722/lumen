package com.sang.system.example.spring.multipleAutoWired;

import org.springframework.stereotype.Service;

//实现2
@Service
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}