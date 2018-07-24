package test.spring.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import test.spring.AuthEntryPoint;
import test.spring.entity.UserDetail;
import test.spring.service.UserDetailService;

public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private AuthEntryPoint authEntryPoint;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		String header = httpServletRequest.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			try {
				String authenticationToken = header.substring(7); 
				
				if (!authenticationToken.isEmpty()) {
		        	String username = jwtUtil.getUsernameFromToken(authenticationToken);
		        	
			        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			        	UserDetail userDetail = new UserDetail(userDetailService.findUserByEmail(username));
			            if (jwtUtil.validateToken(authenticationToken, userDetail)) {
			                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
			                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
			                logger.info("authenticated user " + username + ", setting security context");
			                SecurityContextHolder.getContext().setAuthentication(authentication);
			            }
			        }
		        }
			} catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
				authEntryPoint.commence(httpServletRequest, httpServletResponse, e);
				return;
			}
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
