package br.com.thiago.hotchat.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import br.com.thiago.hotchat.service.UserService;

@Component
@Transactional
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final String URL_AFTER_LOGIN = "/chat/index.html";

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		userService.updateUserOnline(authentication.getName(), true);
		redirectStrategy.sendRedirect(request, response, URL_AFTER_LOGIN);

	}

}
