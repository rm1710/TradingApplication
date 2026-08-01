package com.trading.config;

import java.io.IOException;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.trading.constants.JwtConstant;
import com.trading.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private final JwtService jwtService;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(JwtConstant.JWT_HEADER);

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7);
		token = header.substring(7);

		if (token != null) {
			
			try {

				String email = jwtService.extractEmail(token);

				String authorities = jwtService.extractAuthorities(token);

				List<GrantedAuthority> authoritiesList = AuthorityUtils
						.commaSeparatedStringToAuthorityList(authorities);

				Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authoritiesList);

				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
				return;
			}

			filterChain.doFilter(request, response);
		}
	}

}
