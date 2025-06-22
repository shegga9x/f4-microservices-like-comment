package com.f4.commentlike.kafka.handler.events;

import org.springframework.stereotype.Component;

import com.f4.commentlike.kafka.handler.EventHandler;
import com.f4.commentlike.service.LikeService;
import com.f4.commentlike.service.dto.LikeDTO;

@Component
public class PostCommentlikeHandler implements EventHandler<LikeDTO> {
    private final LikeService svc;

    public PostCommentlikeHandler(LikeService svc) {
        this.svc = svc;
    }

    public String getEventName() {
        return "postReel";
    }

    public void handle(LikeDTO dto) {
        svc.save(dto);
    }
}
