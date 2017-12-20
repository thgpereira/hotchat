package br.com.thiago.hotchat.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -8399638623571444828L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(hidden = true)
	private Long id;

	@NotEmpty(message = "Campo nome é obrigatório.")
	private String name;

	@Email(message = "Informe um e-mail válido.")
	@NotEmpty(message = "Campo e-mail é obrigatório.")
	private String email;

	@NotEmpty(message = "Campo senha é obrigatório.")
	private String password;

	@ApiModelProperty(hidden = true)
	private boolean online;

}
