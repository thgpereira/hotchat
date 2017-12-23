package br.com.thiago.hotchat.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageHistoryDTO {

	private String userEmailFrom;
	private String userEmailTo;
	private String start;
	private String end;

	@ApiModelProperty(hidden = true)
	private Date dateStart;

	@ApiModelProperty(hidden = true)
	private Date dateEnd;

}
