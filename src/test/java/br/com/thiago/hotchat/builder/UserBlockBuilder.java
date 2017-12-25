package br.com.thiago.hotchat.builder;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.entity.UserBlock;

public class UserBlockBuilder {

	private Long id = null;
	private User userFrom = new UserBuilder().withId(1L).build();
	private User userTo = new UserBuilder().withId(2L).build();

	public UserBlock build() {
		return new UserBlock(id, userFrom, userTo, true);
	}

	public UserBlockBuilder withUserFrom(User userFrom) {
		this.userFrom = userFrom;
		return this;
	}

	public UserBlockBuilder withUserTo(User userTo) {
		this.userTo = userTo;
		return this;
	}

}
