package com.aegisep.repository;

import com.aegisep.dto.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    UserVo getAuthenticationByToken(String token);
}
