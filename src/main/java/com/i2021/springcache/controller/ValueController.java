package com.i2021.springcache.controller;


import com.i2021.springcache.config.TestListConfig;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("value")
@RestController
public class ValueController {

//    @Value("${test.list}")
//    private List<String> testList;

    /**
     * : 号，冒号后的值表示当 key 不存在时候使用的默认值，使用默认值时数组的 length = 0
     */
    @Value("${test.array1:}")
    private String[] testArray1;

    /**
     * 借助 EL 表达式的 split() 函数进行切分即可
     */
    @Value("#{'${test.list1:}'.empty ? null : '${test.list1:}'.split(',')}")
    private List<String> testList1;

    @Value("#{'${test.set:}'.empty ? null : '${test.set:}'.split(',') }")
    private Set<Integer> testSet;

    /**
     * ! 特别注意的是 @Value 注解不能和 @AllArgsConstructor 注解同时使用，否则会报错
     * 利用 EL 表达式注入 map1
     * 没找到利用 EL 表达式默认不支持不配置 key/value 的写法
     */
    @Value("#{${test.map1}}")
    private Map<String, String> map1;

    /**
     * 自定义MapDecoder， 解析默认的map值。
     *  缺点： 不美观。
     */
    @Value("#{T(com.i2021.springcache.codec.MapDecoder).decodeMap('${test.map2:}')}")
    private Map<String, Integer> map2;

    @Autowired
    private TestListConfig testListConfig;

    @GetMapping("get")
    public List<String> getValueList() {
        return testListConfig.getList();
    }

    @GetMapping("list1")
    public List<String> getValueList1() {
        return testList1;
    }

    @GetMapping("set1")
    public Set<Integer> getValueSet1() {
        return testSet;
    }

    @GetMapping("map1")
    public Map<String, String> getMap1() {
        return map1;
    }

    @GetMapping("map2")
    public Map<String, Integer> getMap2() {
        return map2;
    }

    @GetMapping("array1")
    public String[] getArray1() {
        return testArray1;
    }
}
