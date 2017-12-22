$(document).ready(function() {	
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

	$('#inputMessage').keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
	    if(keycode == '13'){
	    	$('#btnSendMessage').click();
	    }
	});
});

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

function loadLoginPage() {
	if(document.URL.split('=')[1]) {
		$('#divAlert').removeClass('hidden');
		$('#divAlert').html('Login e/ou senha inválidos.');
	}
}

function onLoadChat() {
	loadUserLogged();
}

function loadUserLogged() {
	$.post('/user/user/load')
	.done(function(data) {
		$('#userNameLogged').html(data.name);
		$('#userEmailLogged').val(data.email);
		
		connect();
	}).fail(function(data) {
		alert('Erro ao selecionar o contato.');
	});
}

function selectUserChat(id, name, email) {
	$('#userNameChat').html(name);
	$('#userEmailChat').val(email);
	$('#userIdChat').val(id);
	$('#btnSendMessage').removeAttr('disabled');
	$('#inputMessage').removeAttr('disabled');
	$('#btnSendMessage').prop('title', 'Enviar mensagem');
	var divId = 'divChat' + id;
    checkDivChat(divId);
    $('div[id^=\'divChat\']').addClass('hidden');
    $('#' + divId).removeClass('hidden');
    var spanId = "spanChatName" + id;
	$('#' + spanId).removeClass('chat-receive-message-contact');	
}

var stompClient = null;
function connect() {
	var userEmailLogged = $('#userEmailLogged').val();
    if(userEmailLogged) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
	var userEmailLogged = $('#userEmailLogged').val();
    stompClient.subscribe('/channel/user/' + userEmailLogged, onMessageReceived);
    stompClient.subscribe('/channel/listContacts', onListContactsReceived);
    stompClient.subscribe('/channel/messagesOffline/' + userEmailLogged, onMessageReceivedOffline);
    stompClient.send('/app/chat.addUser', {}, JSON.stringify({userEmailFrom: userEmailLogged}))
}

function onError(error) {
	console.log(error);
}

function sendMessage() {
    var messageContent = $('#inputMessage').val();
    var userEmailFrom = $('#userEmailLogged').val();
    var userEmailTo = $('#userEmailChat').val();
    if(messageContent && stompClient) {
        var chatMessage = {
        	userEmailFrom: userEmailFrom,
        	userEmailTo: userEmailTo,
            content: messageContent
        };
        $('#inputMessage').val('');
        var idUser = $('#userIdChat').val();
        var divId = 'divChat' + idUser;
        var message = {date: new Date(), content: messageContent};
        addMessageDiv(divId, message, 'darker-right', 'time-left', idUser);
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage));
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
	var divId = 'divChat' + message.idUserFrom;
	checkDivChat(divId);
	addMessageDiv(divId, message, '', 'time-right', message.idUserFrom);
}

function checkDivChat(divId) {
	if ($('#' + divId).length == 0) {
		var divChat = '<div id="' + divId + '" class="messages hidden"></div>'
		$('#areaMessages').append(divChat);
	}
}

function addMessageDiv(divId, message, cssMessage, cssTime, id) {
	var dateFormat = moment(message.date).format('DD/MM/YYYY HH:mm');
	var html = '<div class="chat-message ' + cssMessage + '">';
	html += '<p>' + message.content + '</p>';
	html += '<span class="' + cssTime + '">' + dateFormat + '</span>';
	$('#' + divId).append(html);
	checkBoldName(divId, id);
}

function checkBoldName(divId, id) {
	if($('#' + divId).is(':hidden')) {
		var spanId = "spanChatName" + id;
		$('#' + spanId).addClass('chat-receive-message-contact');		
	}
}

function onListContactsReceived(payload) {
	var html = '';
	var userEmailLogged = $('#userEmailLogged').val();
	JSON.parse(payload.body).forEach(user => {
		if(userEmailLogged !== user.email) {
			html += createHtmlUserContactList(user);	
		}
     });
	$('#chat-contacts-body').html(html);
}

function createHtmlUserContactList(user) {
	var cssStatus = user.online ? 'chat-contact-status-online' : 'chat-contact-status-offline';
	var html = '<div class=\'chat-contacts-body-name\'>';
	html += '<div class=\'chat-contact-status ' + cssStatus + '\'></div>';
	html += '<div class=\'chat-contact-name\' >';
	html += '<a href=\'#\' onclick=\'selectUserChat("' + user.id + '", "' + user.name + '", "' + user.email + '")\'>';
	html += '<span id=\'spanChatName' + user.id + '\'>' + user.name + '</span><\a>'
	html += '</div></div>';
	return html;
}

function onMessageReceivedOffline(payload) {
	JSON.parse(payload.body).forEach(message => {
		var divId = 'divChat' + message.idUserFrom;
		checkDivChat(divId);
		addMessageDiv(divId, message, '', 'time-right', message.idUserFrom);
     });
}