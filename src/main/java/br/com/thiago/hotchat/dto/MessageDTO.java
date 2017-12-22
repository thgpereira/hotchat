package br.com.thiago.hotchat.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

	private Long id;
	private Long idUserFrom;
	private String userEmailFrom;
	private String content;
	private Date date;

}
