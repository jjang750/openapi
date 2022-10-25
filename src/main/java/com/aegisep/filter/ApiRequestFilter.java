package com.aegisep.filter;

import com.aegisep.Constants;
import com.aegisep.auth.ApiAuthenticationToken;
import com.aegisep.auth.ApiUserDetails;
import com.aegisep.auth.ApiUserDetailsService;
import com.aegisep.auth.TokenNotFoundException;
import com.aegisep.dto.TableAuthority;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/openapi")
@Order(0)
public class ApiRequestFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiRequestFilter.class);

    @Autowired
    private ApiUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("doFilterInternal : start " );

        String uuid = UUID.randomUUID().toString();
        log.info( "request UUID : " + uuid );

        String path = request.getRequestURI();
        log.debug("doFilterInternal : " + path);

        if(!path.startsWith("/openapi")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("doFilterInternal start path : " + path);

        final String authorizationHeader = request.getHeader("Authorization");

        log.debug("doFilterInternal Authorization : " + authorizationHeader);

        if(authorizationHeader == null) {
            response.setStatus(401);
            return;
        }

        String token = authorizationHeader.substring(7);

        try {

            log.debug("doFilterInternal token : " + token);

            SecurityContext context = SecurityContextHolder.getContext();
            ApiUserDetails userDetails = userDetailsService.loadUserByToken(token);

            ApiAuthenticationToken authenticationToken = new ApiAuthenticationToken(
                    token,
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities(),
                    userDetails.getTableAuthorities(),
                    uuid
            );

            context.setAuthentication(authenticationToken);

        }catch (TokenNotFoundException ex) {
            log.error(ex.getMessage());
            response.setStatus(401);
            return;
        }

        log.info("doFilterInternal end");
        filterChain.doFilter(request, response);
    }
}
