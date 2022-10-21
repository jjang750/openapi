package com.aegisep.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
public class UserVo implements Serializable {
    private long seq;
    private String token;
    private String username;
    private String password;
    private String authorities;
}
