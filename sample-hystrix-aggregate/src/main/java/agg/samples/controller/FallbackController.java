package agg.samples.controller;

import agg.samples.commands.fallback.FallbackCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String callFallback() {
        FallbackCommand fallbackCommand = new FallbackCommand();
        return fallbackCommand.execute();
    }
}
