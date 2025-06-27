package com.f4.commentlike.service.dto;

import com.f4.commentlike.client.model.RedisUserDTO;

public class CommentWithRedisUserDTO {
    private CommentDTO commentDTO;
    private RedisUserDTO redisUserDTO;

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }

    public void setCommentDTO(CommentDTO commentDTO) {
        this.commentDTO = commentDTO;
    }

    public RedisUserDTO getRedisUserDTO() {
        return redisUserDTO;
    }

    public void setRedisUserDTO(RedisUserDTO redisUserDTO) {
        this.redisUserDTO = redisUserDTO;
    }

    public CommentWithRedisUserDTO(CommentDTO commentDTO, RedisUserDTO redisUserDTO) {
        this.commentDTO = commentDTO;
        this.redisUserDTO = redisUserDTO;
    }

}
