package br.com.thiago.hotchat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.thiago.hotchat.enumerator.MessageStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message implements Serializable {

	private static final long serialVersionUID = 3616307628412252048L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_from")
	@NotNull(message = "Campo usuário de envio da mensagem é obrigatório.")
	private User userFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_to")
	@NotNull(message = "Campo usuário de recebimento da mensagem é obrigatório.")
	private User userTo;

	@Length(max = 255, message = "Mensagem não pode ter mais de 255 caracteres.")
	@NotEmpty(message = "Campo mensagem é obrigatório.")
	private String content;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status")
	@NotNull(message = "Campo status da mensagem é obrigatório.")
	@ApiModelProperty(hidden = true)
	private MessageStatus messageStatus;

	@NotNull(message = "Campo data da mensagem é obrigatório.")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Transient
	private String userEmailFrom;

	@Transient
	private String userEmailTo;

}
