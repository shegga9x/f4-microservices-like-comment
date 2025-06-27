package com.f4.commentlike.service.dto;

import com.f4.commentlike.client.model.RedisUserDTO;

public class LikeWithRedisUserDTO {
    private LikeDTO LikeDTO;
    private RedisUserDTO redisUserDTO;

    public LikeDTO getLikeDTO() {
        return LikeDTO;
    }

    public void setLikeDTO(LikeDTO LikeDTO) {
        this.LikeDTO = LikeDTO;
    }

    public RedisUserDTO getRedisUserDTO() {
        return redisUserDTO;
    }

    public void setRedisUserDTO(RedisUserDTO redisUserDTO) {
        this.redisUserDTO = redisUserDTO;
    }

    public LikeWithRedisUserDTO(LikeDTO LikeDTO, RedisUserDTO redisUserDTO) {
        this.LikeDTO = LikeDTO;
        this.redisUserDTO = redisUserDTO;
    }

}
