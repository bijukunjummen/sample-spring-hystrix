package agg.samples.controller;

import agg.samples.commands.simple.HelloWorldCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String callHelloWorldCommand(@RequestParam(value = "greeting", defaultValue = "World", required = false) String greeting) {
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand(greeting);
        return helloWorldCommand.execute();
    }
}
