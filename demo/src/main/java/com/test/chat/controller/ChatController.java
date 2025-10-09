//package com.test.chat.controller;
//
//import com.test.chat.dto.ChatDto;
//import com.test.chat.service.ChatRoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatRoomService chatRoomService;
//
//    // 클라이언트가 메시지를 보내는 경로
//    @MessageMapping("/chat.sendMessage")
//    public void sendMessage(ChatDto dto) {
//        // 메모리에 메시지 저장
//        chatRoomService.addMessage(dto.getChatRoomId(), dto);
//
//        // 같은 채팅방 구독자들에게 브로드캐스트
//        messagingTemplate.convertAndSend("/topic/chatRoom/" + dto.getChatRoomId(), dto);
//    }
//}

package com.test.chat.controller;

import com.test.chat.dto.ChatDto;
import com.test.matching.entity.Matching;
import com.test.matching.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MatchingRepository matchingRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // 특정 매칭룸 HTML 접속
    @GetMapping("/chat/{matchingId}")
    public String chatPage(@PathVariable("matchingId") Long matchingId, Model model) {
        Matching matching = matchingRepository.findById(matchingId)
                .orElseThrow(() -> new IllegalArgumentException("매칭룸 없음"));

        Long roomId = matching.getMatchingId(); // 룸ID
        Long writerId = matching.getPost().getAuthor().getId();
        Long commenterId = matching.getComment().getMember().getId();

        List<Long> memberIds = List.of(writerId, commenterId); // 참여 멤버

        // 로그인 유저는 post 작성자라고 가정
        Long loginMemberId = writerId;

        model.addAttribute("roomId", roomId);
        model.addAttribute("memberIds", memberIds);
        model.addAttribute("loginMemberId", loginMemberId);

        return "ChatTest"; // templates/ChatTest.html
    }

    // 웹소켓 메시지 처리
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatDto message) {
        System.out.println("Received from " + message.getSenderId() + ": " + message.getContent());

        String destination = "/topic/chatRoom/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
    }
}

