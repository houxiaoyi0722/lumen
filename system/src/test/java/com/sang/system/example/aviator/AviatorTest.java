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
        value.put("a",10);
        value.put("b",5);
        value.put("veh_model_name","aaa");
        value.put("config_name","5555定时");

        Expression compiledExp = AviatorEvaluator.compile("a*10 + '测试'",true);
        Expression compiledExp2 = AviatorEvaluator.compile("veh_model_name + config_name",true);
        // Execute with injected variables.
        String result = (String) compiledExp.execute(value);
        String result2 = (String) compiledExp2.execute(value);

        System.out.println(result);
        System.out.println(result2);
    }


}
