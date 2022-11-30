package com.sang.system.example.spring.multipleAutoWired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ShapeBeanFactory {

    @Autowired//关键在这个，原理：当一个接口有多个实现类的时候，key为实现类名，value为实现类对象
    private Map<String, Shape> shapeMap;

    @Autowired//这个注入了多个实现类对象
    private List<Shape> shapeList;

    public Shape getShape(String shapeType) {
        Shape bean1 = shapeMap.get(shapeType);
        System.out.println(bean1);
        return bean1;
    }
}