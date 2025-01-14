// package com.substring.chat.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry config) {

//         config.enableSimpleBroker("/topic");
//         // /topic/messages

//         config.setApplicationDestinationPrefixes("/app");
//         // /app/chat
//         // server-side: @MessagingMapping("/chat)


//     }

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         registry.addEndpoint("/chat")//connection establishment
//                 .setAllowedOrigins("https://chat-app-frontend-beryl-nu.vercel.app")
//                 .withSockJS();
//     }
//     // /chat endpoint par connection apka establish hoga
// }



package com.substring.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker
        config.enableSimpleBroker("/topic", "/queue"); // Enable message broadcasting to clients
        config.setApplicationDestinationPrefixes("/app"); // Prefix for sending messages
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // WebSocket endpoint
                .setAllowedOrigins("https://chat-app-frontend-beryl-nu.vercel.app") // Allow your frontend domain
                .withSockJS(); // Enable SockJS fallback options
    }
}
