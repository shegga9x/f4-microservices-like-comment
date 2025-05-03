package com.f4.commentlike.handler.events;

import org.springframework.stereotype.Component;

import com.f4.commentlike.handler.EventHandler;
import com.f4.commentlike.service.CommentService;
import com.f4.commentlike.service.dto.CommentDTO;

@Component
public class PostReelHandler implements EventHandler<CommentDTO> {
    private final CommentService svc;

    public PostReelHandler(CommentService svc) {
        this.svc = svc;
    }

    public String getEventName() {
        return "postReel";
    }

    public void handle(CommentDTO dto) {
        svc.save(dto);
    }
}
