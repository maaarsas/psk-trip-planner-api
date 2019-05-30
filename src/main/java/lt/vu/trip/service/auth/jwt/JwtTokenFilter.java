package lt.vu.trip.service.auth.jwt;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import lt.vu.trip.entity.response.ErrorResponse;
import lt.vu.trip.entity.response.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	private Gson gson = new Gson();

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = jwtTokenProvider.resolveToken(request);
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(request, response);
		} catch (InvalidJwtAuthenticationException e) {
			log.debug("InvalidJwtAuthenticationException: " + e.getMessage());
			sendError(response, ErrorType.AUTHENTICATION_INVALID_OR_EXPIRED_TOKEN);
		} catch (AuthenticationException e) {
			log.debug("AuthenticationException: " + e.getMessage());
			sendError(response, ErrorType.AUTHENTICATION_ERROR);
		} catch (RuntimeException e) {
			log.debug("RuntimeException: " + e.getMessage());
			sendError(response, ErrorType.AUTHENTICATION_ERROR);
		}
	}

	private void sendError(HttpServletResponse response, ErrorType error) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ErrorResponse errorResponse = new ErrorResponse(error, null);
		String errorResponseBody = this.gson.toJson(errorResponse);

		response.getWriter().write(errorResponseBody);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
