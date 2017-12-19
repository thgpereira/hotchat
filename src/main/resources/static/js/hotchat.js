$(document).ready(function() {
	$('#formLogin').validate({
		rules: {
			inputEmail: {
				required: true,
				email: true
			},
			inputPassword: 'required'
		},			
		messages: {
			inputEmail: {
				required: 'Campo e-mail é obrigatório.',
				email: 'Informe um e-mail válido.'					
			},
			inputPassword: 'Campo senha é obrigatório.'
				
		},
		errorElement: 'em',
		errorPlacement: function (error, element) {
			error.addClass('error-block');
			error.insertAfter(element);
		},
		highlight: function (element, errorClass, validClass) {
			$(element).parents('.form-control').addClass('has-error').removeClass('has-success');
		},
		unhighlight: function (element, errorClass, validClass) {
			$(element).parents('.form-control').addClass('has-success').removeClass('has-error');
		},
		submitHandler: function(form) {
			requestLogin();
		}
	});
	
	$("#formRegistration").validate({
		rules: {
			inputName: "required",
			inputEmail: {
				required: true,
				email: true
			},
			inputPassword: {
				required: true,
				minlength: 6
			},
			inputPasswordConfirm: {
				required: true,
				equalTo: "#inputPassword"
			}
		},			
		messages: {
			inputName: "Campo nome é obrigatório.",
			inputEmail: {
				required: "Campo e-mail é obrigatório.",
				email: "Informe um e-mail válido."					
			},
			inputPassword: {
				required: "Campo senha é obrigatório.",
				minlength: "Campo senha deve ter no mínimo 6 caracteres."
			},
			inputPasswordConfirm: {
				required: "Campo confirme a senha é obrigatório.",
				equalTo: "Senhas digitadas não conferem."
			},
		},
		errorElement: "em",
		errorPlacement: function (error, element) {
			error.addClass('error-block');
			error.insertAfter(element);
		},
		highlight: function (element, errorClass, validClass) {
			$(element).parents(".form-control").addClass("has-error").removeClass("has-success");
		},
		unhighlight: function (element, errorClass, validClass) {
			$(element).parents(".form-control").addClass("has-success").removeClass("has-error");
		},
		submitHandler: function(form) {
			requestCreateUser();
		}
	});
});

function requestLogin() {
	alert('Login');
}

function requestCreateUser() {
	alert('CreateUser');
}