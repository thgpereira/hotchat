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
	
	$('#formRegistration').validate({
		rules: {
			inputName: 'required',
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
				equalTo: '#inputPassword'
			}
		},			
		messages: {
			inputName: 'Campo nome é obrigatório.',
			inputEmail: {
				required: 'Campo e-mail é obrigatório.',
				email: 'Informe um e-mail válido.'					
			},
			inputPassword: {
				required: 'Campo senha é obrigatório.',
				minlength: 'Campo senha deve ter no mínimo 6 caracteres.'
			},
			inputPasswordConfirm: {
				required: 'Campo confirme a senha é obrigatório.',
				equalTo: 'Senhas digitadas não conferem.'
			},
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
			requestCreateUser();
		}
	});
});

function requestLogin() {
	$.ajax({
		method: 'POST',
		url: '/login',
		data: { 
			email: $('#inputEmail').val(),
			password: $('#inputPassword').val() 
		}
	});
//	$.post('/login', {
//		email : $('#email').val(),
//		password : $('#password').val()
//	}).done(function(data) {
//		$(location).attr('href', '/chat/index.html');
//	}).fail(function(data) {
//		$('#divAlert').removeClass('hidden');
//		$('#divAlert').addClass('alert alert-danger');
//		$('#divAlert').html(data.responseText);
//	});
}

function requestCreateUser() {
	$.post('/user/create', {
		name: $('#inputName').val(),
		email: $('#inputEmail').val(),
		password: $('#inputPassword').val()
	}).done(function(data) {
		$('#divAlert').removeClass('alert-danger hidden');
		$('#divAlert').addClass('alert alert-success');
		$('#btnCreate').addClass('hidden');
		$('#btnCancelar').addClass('hidden');
		$('.form-control').prop('readonly', true);
		$('.form-control').addClass('readonly');
		$('#divAlert').html(showMessageSucessCreateUser());
		timeLogin();
	}).fail(function(data) {
		$('#divAlert').removeClass('hidden');
		$('#divAlert').addClass('alert alert-danger');
		$('#divAlert').html(data.responseText);
	});
}

function showMessageSucessCreateUser() {
	var html = 'Seu cadastro foi realizado com sucesso! ;)<br/><br/>';
	html += 'Você será redirecionado para a tela de login em ';
	html += '<span id=\'timeLogin\'>5</span> segundos.';
	return html;
}

function timeLogin() {
	var interval, time = 5;
	function timer() {
		$('#timeLogin').html(time);
		if (time === 0) {
			clearInterval(interval);
			redirectLogin();
		} else {
			time--;
		}
	}
	interval = setInterval(timer, 1000);
}

function redirectLogin() {
	$(document).ready(function() {
		$(location).attr('href', '/');
	});
}