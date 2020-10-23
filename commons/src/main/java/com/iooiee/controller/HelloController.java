package com.iooiee.controller;

import com.iooiee.common.PageResult;
import com.iooiee.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: Yanggc
 * DateTime: 10/23 12:14
 */
@RestController
public class HelloController {

    @RequestMapping("getList")
    Result getList(){
        List<Map> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap1 = new HashMap<>();

        resultMap.put("id","123");
        resultMap.put("userName","周杰伦");
        resultMap1.put("userId","6666");

        resultMap1.put("id","1111");
        resultMap1.put("userName","ross");
        resultMap1.put("userId","1111");

        resultList.add(resultMap);
        resultList.add(resultMap1);

//        return Result.failure("adfsafdasdfsa").data(resultList);
        return PageResult.success().data(resultList);
    }
}
