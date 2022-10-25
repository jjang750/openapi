package com.aegisep.auth;

import com.aegisep.dto.TableAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String token;

    private final String UUID;
    private final Collection<TableAuthority> tableAuthorities;

    public ApiAuthenticationToken(String token,
                                  String credentials,
                                  String principal,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Collection<TableAuthority> tableAuthorities,
                                  String UUID
                                  ) {
        super(credentials, principal, authorities);
        this.token = token;
        this.UUID = UUID;
        this.tableAuthorities = tableAuthorities;
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    /* 사용자 토큰 */
    public String getToken(){
        return this.token;
    }

    /* 테이블 접근 권한 */
    public Collection<TableAuthority> getTableAuthorities(){return this.tableAuthorities;}

    /* Request UUID */
    public String getUUID(){
        return this.UUID;
    }


}
