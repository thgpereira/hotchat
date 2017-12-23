package br.com.thiago.hotchat.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.dto.MessageHistoryDTO;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.service.MessageService;
import br.com.thiago.hotchat.util.DateUtils;
import br.com.thiago.hotchat.util.Messages;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@ApiOperation(value = "Histórico de mensagem", notes = "Serviço REST responsável pela busca do histórico de conversas entre usuários.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userEmailFrom", value = "E-mail do usuário de origem", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "userEmailTo", value = "E-mail do usuário de destino", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "start", value = "Data inicio", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "end", value = "Data fim", required = true, dataType = "string", paramType = "form") })
	@PostMapping(value = "/history")
	public ResponseEntity<?> getCreate(MessageHistoryDTO messageHistoryDTO) {
		try {
			validateHistoryDates(messageHistoryDTO);
			List<MessageDTO> messagesHistory = messageService.findMessagesHistory(messageHistoryDTO);
			return (ResponseEntity<?>) ResponseEntity.ok(messagesHistory);
		} catch (HotChatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(Messages.errorLoadHistory());
		}
	}

	private void validateHistoryDates(MessageHistoryDTO messageHistoryDTO) throws ParseException, HotChatException {
		messageHistoryDTO.setDateStart(DateUtils.formatFirstDateDay(messageHistoryDTO.getStart()));
		messageHistoryDTO.setDateEnd(DateUtils.formatLastDateDay(messageHistoryDTO.getEnd()));
		if (DateUtils.isStartDateAfterEndDate(messageHistoryDTO.getDateStart(), messageHistoryDTO.getDateEnd())) {
			throw new HotChatException(Messages.dateStartAfterDateEnd());
		}
	}

}
