package com.aegisep.auth;

import com.aegisep.dto.TableAuthority;
import com.aegisep.dto.UserVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Setter
public class ApiUserDetails implements UserDetails, Serializable {
    private UserVo userVo;
    private String password;
    private String username;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean isEnabled = true;

    private Collection<TableAuthority> tableAuthorities;

    public ApiUserDetails(UserVo userVo, Collection<TableAuthority> tableAuthorities) {
        this.userVo = userVo;
        this.tableAuthorities = tableAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> userVo.getAuthorities());
        return collection;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Collection<TableAuthority> getTableAuthorities() {return this.tableAuthorities;}

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
