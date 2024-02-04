package tumminia.wrpsrlibrary.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tumminia.wrpsrlibrary.exceptions.UnauthorizedException;
import tumminia.wrpsrlibrary.users.User;
import tumminia.wrpsrlibrary.users.UserService;

@Component
public class JWTAuthFilter extends OncePerRequestFilter { // ESTENDE LA CLASSE CHE IMPLEMENTA FILTER (OGNI FILTRO VIENE
															// ESEGUITO UNA VA VOLTA AD OGNI RICHIESTA
	@Autowired
	JWTTools jwttools;

	@Autowired
	UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore passa il token nell'authorization header");

		String token = authHeader.substring(7);
		System.out.println("TOKEN = " + token);

		jwttools.verifyToken(token);

		UUID userId = jwttools.extractUserId(token);
		String userRole = jwttools.extractUserRole(token);

		User currentUser = userService.findById(userId);
		System.out.println(currentUser);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUser, null,
				AuthorityUtils.createAuthorityList("ROLE_" + userRole));

		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println(request.getServletPath());
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}