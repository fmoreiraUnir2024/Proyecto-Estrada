package com.libreriaproyect.libreriproyecto.Controller;

import com.libreriaproyect.libreriproyecto.Services.https.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("gpt/v1")
public class ChatGPTController {
    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/chat")
    public Mono<ResponseEntity<String>> chat(@RequestParam String prompt) {
        return openAIService.getChatGPTResponse(prompt)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
