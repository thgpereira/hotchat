package br.com.thiago.hotchat.builder;

import br.com.thiago.hotchat.entity.User;

public class UserBuilder {

	private Long id = null;
	private String name = "Usuario Teste Unitário";
	private String email = "unitteste@email.com.br";
	private String password = "123456";
	private boolean online;

	public User build() {
		return new User(id, name, email, password, online);
	}

	public UserBuilder withId(Long id) {
		this.id = id;
		return this;
	}

}
