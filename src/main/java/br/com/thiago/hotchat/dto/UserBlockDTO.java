package br.com.thiago.hotchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBlockDTO {

	private Boolean userBlocked;
	private String message;

}
