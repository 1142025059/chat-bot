package com.taoz.ai_app.chat_bot.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfiguration {

    @Bean
    public ChatClient chatClient(OpenAiChatModel model) {
        return ChatClient
                .builder(model)
                .build();
    }
}

