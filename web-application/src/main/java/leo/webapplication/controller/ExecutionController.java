package leo.webapplication.controller;

import leo.webapplication.model.ApiTestData;
import org.springframework.web.bind.annotation.*;

/**
 * Created by leozhang on 8/31/16.
 * ...
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "ConstantConditions"})
@RestController
@RequestMapping("/try")
public class ExecutionController {






    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "leo") String name) {
        System.out.println("hello");
        if (name.equalsIgnoreCase("1"))
            throw new RuntimeException("create a run time exception");
        return "hello " + name;
    }


    @RequestMapping(value = "/model", method = RequestMethod.POST)
    public String tryModel(@RequestBody ApiTestData myTestData) {
        System.out.println(myTestData == null);
        return myTestData.process(null);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public Map exception(Exception exception, Model model) {
//        model.addAttribute("exception", exception);
//        Map map = new HashMap();
//        map.put("a", "exception");
//        return map;
//    }

}
