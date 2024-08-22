package org.example.anibuddy.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomRequest { //가게 상세에서 문의 버튼 누를 시
    private int ownerId;

    @Builder
    public ChatRoomRequest(int ownerId) {
        this.ownerId = ownerId;
    }
}
