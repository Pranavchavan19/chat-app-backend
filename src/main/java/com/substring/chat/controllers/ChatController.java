// package com.substring.chat.controllers;

// import java.time.LocalDateTime;

// import org.springframework.messaging.handler.annotation.DestinationVariable;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestBody;

// import com.substring.chat.entities.Message;
// import com.substring.chat.entities.Room;
// import com.substring.chat.playload.MessageRequest;
// import com.substring.chat.repositories.RoomRepository;

// @Controller
// @CrossOrigin("https://chat-app-frontend-beryl-nu.vercel.app")
// public class ChatController {


//     private RoomRepository roomRepository;

//     public ChatController(RoomRepository roomRepository) {
//         this.roomRepository = roomRepository;
//     }


//     //for sending and receiving messages
//     @MessageMapping("/sendMessage/{roomId}")// /app/sendMessage/roomId
//     @SendTo("/topic/room/{roomId}")//subscribe
//     public Message sendMessage(
//             @DestinationVariable String roomId,
//             @RequestBody MessageRequest request
//     ) {

//         Room room = roomRepository.findByRoomId(request.getRoomId());
//         Message message = new Message();
//         message.setContent(request.getContent());
//         message.setSender(request.getSender());
//         message.setTimeStamp(LocalDateTime.now());
//         if (room != null) {
//             room.getMessages().add(message);
//             roomRepository.save(room);
//         } else {
//             throw new RuntimeException("room not found !!");
//         }

//         return message;


//     }
// }


package com.substring.chat.controllers;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.substring.chat.entities.Message;
import com.substring.chat.entities.Room;
import com.substring.chat.playload.MessageRequest;
import com.substring.chat.repositories.RoomRepository;

@Controller
@CrossOrigin("https://chat-app-frontend-beryl-nu.vercel.app")
public class ChatController {

    private final RoomRepository roomRepository;

    // Constructor injection for RoomRepository
    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Method to send and receive messages
    @MessageMapping("/sendMessage/{roomId}") // /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}") // Subscribe to /topic/room/{roomId}
    public Message sendMessage(
            @DestinationVariable String roomId, // Room ID from the URL
            @RequestBody MessageRequest request // Message request from the frontend
    ) {
        // Find the room using the roomId from the request
        Room room = roomRepository.findByRoomId(roomId);
        
        if (room == null) {
            throw new RuntimeException("Room not found!"); // If the room does not exist, throw error
        }

        // Create a new message
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now()); // Set timestamp for when the message was sent

        // Add the message to the room's message list
        room.getMessages().add(message);
        roomRepository.save(room); // Save the room with the new message

        // Return the message to be broadcasted to the subscribed clients
        return message;
    }
}
