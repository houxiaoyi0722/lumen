package com.sang.system.example.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AviatorTest {

    @Test
    public void test() {

        Map<String, Object> value = new HashMap<>();
        value.put("a",null);
        value.put("b",5);
        value.put("veh_model_name","veh_model_name");
        value.put("config_name","config_name");

//        Expression compiledExp = AviatorEvaluator.compile("a*10 + '测试'",true);
        Expression compiledExp2 = AviatorEvaluator.compile("veh_model_name + '_' + config_name",true);
        Expression compiledExp3 = AviatorEvaluator.compile("a != '' ? (veh_model_name + a) : (veh_model_name + b)",true);
        // Execute with injected variables.
//        String result = (String) compiledExp.execute(value);
        String result2 = (String) compiledExp2.execute(value);
        String result3 = (String) compiledExp3.execute(value);

//        System.out.println(result);
        System.out.println(result2);
        System.out.println(result3);
    }


}
