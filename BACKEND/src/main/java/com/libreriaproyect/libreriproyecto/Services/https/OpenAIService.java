package com.libreriaproyect.libreriproyecto.Services.https;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OpenAIService {

    private String apiKey;

    private final WebClient webClient;

    public OpenAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
    }

    public Mono<String> getChatGPTResponse(String prompt) {
        return this.webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + "")
                .body(Mono.just(new ChatGPTRequest(prompt)), ChatGPTRequest.class)
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent());
    }

    static class ChatGPTRequest {
        private String model;
        private ChatGPTMessage[] messages;

        public ChatGPTRequest(String prompt) {
            this.model = "gpt-3.5-turbo";
            this.messages = new ChatGPTMessage[]{new ChatGPTMessage("user", prompt)};
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public ChatGPTMessage[] getMessages() {
            return messages;
        }

        public void setMessages(ChatGPTMessage[] messages) {
            this.messages = messages;
        }
    }

    static class ChatGPTMessage {
        private String role;
        private String content;

        public ChatGPTMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    static class ChatGPTResponse {
        private List<Choice> choices;

        public List<Choice> getChoices() {
            return choices;
        }

        public void setChoices(List<Choice> choices) {
            this.choices = choices;
        }

        static class Choice {
            private ChatGPTMessage message;

            public ChatGPTMessage getMessage() {
                return message;
            }

            public void setMessage(ChatGPTMessage message) {
                this.message = message;
            }
        }
    }
}
