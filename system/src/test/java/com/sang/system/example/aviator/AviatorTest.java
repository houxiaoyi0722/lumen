//package com.sang.system.example.aviator;
//
//import com.googlecode.aviator.AviatorEvaluator;
//import com.googlecode.aviator.Expression;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class AviatorTest {
//
//    @Test
//    public void test() {
//
//        Map<String, Object> value = new HashMap<>();
//        value.put("a","10");
//        value.put("b",5);
//        value.put("veh_model_name","veh_model_name");
//        value.put("config_name","config_name");
//
//        Expression compiledExp = AviatorEvaluator.compile("long(a)*10 + '测试'",true);
//        Expression compiledExp2 = AviatorEvaluator.compile("veh_model_name + '_' + config_name",true);
//        Expression compiledExp3 = AviatorEvaluator.compile("a != '' ? (veh_model_name + a) : (veh_model_name + b)",true);
//        // Execute with injected variables.
//        String result = (String) compiledExp.execute(value);
//        String result2 = (String) compiledExp2.execute(value);
//        String result3 = (String) compiledExp3.execute(value);
//
//        System.out.println(result);
//        System.out.println(result2);
//        System.out.println(result3);
//    }
//
//    @Test
//    public void test2() {
//
//        Map<String, Object> value = new HashMap<>();
//        value.put("a","10");
//        value.put("b",5);
//        value.put("veh_model_name","veh_model_name");
//        value.put("config_name","config_name");
//
//        Expression compiledExp = AviatorEvaluator.compile("string.startsWith(veh_model_name,'veh')? long(a)*10: 'false'",true);
//        // Execute with injected variables.
//        String result = (String) compiledExp.execute(value);
//
//        System.out.println(result);
//    }
//
//    @Test
//    public void test3() {
//
//        Map<String, Object> value = new HashMap<>();
//        value.put("a",null);
//        value.put("b",5);
//        value.put("veh_model_name","veh_model_name");
//        value.put("config_name","config_name");
//
//        Expression compiledExp = AviatorEvaluator.compile("a == nil? str(a) : a",true);
//        // Execute with injected variables.
//        Object result = compiledExp.execute(value);
//
//        System.out.println(result);
//    }
//
//    @Test
//    public void test4() {
//
//        Map<String, Object> value = new HashMap<>();
//        value.put("a",5.156465);
//        value.put("b",5);
//        value.put("veh_model_name","veh_model_name");
//        value.put("config_name","config_name");
//
//        Expression compiledExp = AviatorEvaluator.compile("math.round(decimal(a)*decimal(100))/decimal(100)",true);
//        // Execute with injected variables.
//        Object result = compiledExp.execute(value);
//
//        System.out.println(result);
//    }
//
//
//}
