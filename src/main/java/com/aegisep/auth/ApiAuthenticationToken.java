package com.aegisep.auth;

import com.aegisep.dto.TableAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String token;
    private final Collection<TableAuthority> tableAuthorities;

    public ApiAuthenticationToken(String token,
                                  String credentials,
                                  String principal,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Collection<TableAuthority> tableAuthorities
                                  ) {
        super(credentials, principal, authorities);
        this.token = token;
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

    public String getToken(){
        return this.token;
    }

    public Collection<TableAuthority> getTableAuthorities(){return this.tableAuthorities;}


}
