package br.com.thiago.hotchat.controller.test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ChatControllerTest {

	static final String WEBSOCKET_URI = "ws://localhost:8080/ws";
	static final String WEBSOCKET_TOPIC = "/channel";

	private BlockingQueue<String> blockingQueue;
	private WebSocketStompClient stompClient;

	@Before
	public void setup() {
		blockingQueue = new LinkedBlockingDeque<>();
		stompClient = new WebSocketStompClient(
				new SockJsClient(Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));
	}

	@Test
	public void shouldReceiveAMessageFromTheServer() throws Exception {
		StompSession session = stompClient.connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {
		}).get(1, TimeUnit.SECONDS);
		session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

		String message = "MESSAGE TEST";
		session.send(WEBSOCKET_TOPIC, message.getBytes());

		Assert.assertEquals(message, blockingQueue.poll(1, TimeUnit.SECONDS));
	}

	class DefaultStompFrameHandler implements StompFrameHandler {

		@Override
		public Type getPayloadType(StompHeaders arg0) {
			return byte[].class;
		}

		@Override
		public void handleFrame(StompHeaders stompHeaders, Object o) {
			blockingQueue.offer(new String((byte[]) o));
		}
	}

}
