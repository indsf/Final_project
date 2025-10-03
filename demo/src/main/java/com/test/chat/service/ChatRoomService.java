package com.test.chat.service;

import com.test.chat.dto.ChatDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatRoomService {

    // 채팅방ID -> 메시지 리스트
    private final Map<Long, List<ChatDto>> chatRooms = new ConcurrentHashMap<>();

    // 메시지 추가
    public void addMessage(Long roomId, ChatDto message) {
        chatRooms.computeIfAbsent(roomId, k -> new ArrayList<>()).add(message);
    }

    // 채팅 히스토리 조회
    public List<ChatDto> getMessages(Long roomId) {
        return chatRooms.getOrDefault(roomId, new ArrayList<>());
    }
}
