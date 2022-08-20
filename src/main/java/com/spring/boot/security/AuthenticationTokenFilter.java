package com.spring.boot.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.boot.service.CustomUserDetailsService;

public class AuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired 
	AuthTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService userService;
//	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		/*
//		 * Authorization Bearer $token
//		 */
//		
//		String authorizationHeader = request.getHeader("Authorization");
//		
//		if(authorizationHeader == null || authorizationHeader.isEmpty()|| !authorizationHeader.startsWith("Bearer ")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		
//		String token = authorizationHeader.split(" ")[1].trim();
//		
//		if(!tokenUtils.validate(token)) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		
//		String username = tokenUtils.getUsername(token);
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		
//		SecurityContextHolder.getContext().setAuthentication(authToken);
//		
//		filterChain.doFilter(request, response);
//	}
	
	/*
	 * Approach 2
	 */
	
	private static final Logger logger = LogManager.getLogger(AuthenticationTokenFilter.class);

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        	String authorizationHeader = request.getHeader("Authorization");
    		
    		if(authorizationHeader == null || authorizationHeader.isEmpty()|| !authorizationHeader.startsWith("Bearer ")) {
    			filterChain.doFilter(request, response);
    			return;
    		}
            String jwt = getJwtFromRequest(request);

            if (tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = userService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
	
}
