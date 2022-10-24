package com.aegisep.auth;

import com.aegisep.dto.TableAuthority;
import com.aegisep.dto.UserVo;
import com.aegisep.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ApiUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public ApiUserDetails loadUserByToken(String token) throws TokenNotFoundException {
        UserVo userVo = userMapper.getAuthenticationByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Unauthorized token"));
        Collection<TableAuthority> tableAuthorities = userMapper.getTableAuthenticationByToken(token);
        if (tableAuthorities.isEmpty()) {
            throw new TokenNotFoundException("Table Unauthorized");
        }
        return new ApiUserDetails(userVo, tableAuthorities);
    }
}
