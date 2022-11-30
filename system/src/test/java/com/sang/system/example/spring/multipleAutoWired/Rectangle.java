package com.sang.system.example.spring.multipleAutoWired;


import org.springframework.stereotype.Service;

//实现1
@Service
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
