package br.com.thiago.hotchat.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.UserService;

@Component
public class WebSocketEventListener {

	@Autowired
	private UserService userService;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String email = (String) headerAccessor.getSessionAttributes().get("email");
		User user = userService.findByEmail(email);
		if (user != null) {
			user.setOnline(false);
			userService.update(user);
		}
	}
}
