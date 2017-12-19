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
});

function requestLogin() {
	alert('Login');
}