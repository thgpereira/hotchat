package br.com.thiago.hotchat.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_block")
public class UserBlock implements Serializable {

	private static final long serialVersionUID = 3616307628412252048L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_from")
	@NotNull(message = "Campo usuário de solicitação do bloqueio é obrigatório.")
	private User userFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_to")
	@NotNull(message = "Campo usuário a ser bloqueado é obrigatório.")
	private User userTo;

	@Transient
	private boolean block;

}
