package com.taoz.ai_app.chat_bot.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final OpenAiChatModel chatModel;
    private final ChatClient chatClient;

    @Autowired
    public ChatController(OpenAiChatModel chatModel, ChatClient chatClient) {
        this.chatModel = chatModel;
        this.chatClient = chatClient;
    }

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "你好") String prompt) {
        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    @GetMapping(value = "/chatStream", produces = "text/html;charset=UTF-8")
    public Flux<String> chatStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String prompt) {
         return chatClient
                 .prompt(prompt)
                 .stream()
                 .content();
    }
}