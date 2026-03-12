var iniciarSesion = function () {

	var email = $("#correo").val();
	var password = $("#contrasenia").val();

	$.post("/usuario/login", { 'correo': email, 'contrasenia': password }, function (fragmento) {
		$("#contenedor").replaceWith(fragmento);
	});
};


$(document).ready(function () {

	jQuery.validator.addMethod("sololetrasyespacios", function (value, element) {
		return this.optional(element) || /^[a-z\s]+$/i.test(value);
	}, "Solo letras y espacios");


	jQuery.extend(jQuery.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una dirección de correo válida",
		url: "Por favor, escribe una URL válida.",
		date: "Por favor, escribe una fecha válida.",
		dateISO: "Por favor, escribe una fecha (ISO) válida.",
		number: "Por favor, escribe un número entero válido.",
		digits: "Por favor, escribe sólo dígitos.",
		creditcard: "Por favor, escribe un número de tarjeta válido.",
		equalTo: "Por favor, escribe el mismo valor de nuevo.",
		accept: "Por favor, escribe un valor con una extensión aceptada.",
		maxlength: jQuery.validator.format("Por favor, no escribas más de {0} caracteres."),
		minlength: jQuery.validator.format("Por favor, no escribas menos de {0} caracteres."),
		rangelength: jQuery.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		range: jQuery.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		max: jQuery.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		min: jQuery.validator.format("Por favor, escribe un valor mayor o igual a {0}.")
	});

	// ***************** Formularios ***************************************************

	//forma de login
	$("#forma-login").submit(function (e) {

		e.preventDefault();

	}).validate({
		rules: {
			correo: {
				required: true,
				maxlength: 100,
				email: true
			},
			contrasenia: {
				required: true
			}
		},
		errorPlacement: function (error, element) {
			error.appendTo(element.parent());
		},
		submitHandler: function (form) {

			var email = $("#correo").val();
			var password = $("#contrasenia").val();

			$.post("/usuario/login", { 'correo': email, 'contrasenia': password }, function (fragmento) {

				var newDoc = document.open("text/html", "replace");
				newDoc.write(fragmento);
				newDoc.close();

			});

			return false;
		}

	});

	// registro de usuario 
	$("#forma-registro").submit(function (e) {

		e.preventDefault();

	}).validate({
		rules: {
			correo: {
				required: true,
				maxlength: 100,
				email: true
			},
			nombre: {
				required: true
			},
			apellidoPat: {
				required: true
			},
			apellidoMat: {
				required: true
			}
		},
		errorPlacement: function (error, element) {
			error.appendTo(element.parent());
		},
		submitHandler: function (form) {

			var nombre = $("#nombre").val();
	        var apellidopat = $("#apellidoPat").val();
	        var apellidomat = $("#apellidoMat").val();
	        var correo = $("#correo").val();
            var especialidad = $("#especialidad").val();
            var grado = $("#grado").val();
            var telefono = $("#telefono").val();
            var genero = $("#genero").val();
            var rol = $("#rol").val();
    
			$.post("/usuario/registro", {
				'nombre': nombre,
				'apellidoPat': apellidopat,
				'apellidoMat': apellidomat,
				'correo': correo,
				'especialidad': especialidad,
				'grado': grado,
				'telefono': telefono,
				'genero': genero,
				'rol': rol
			}, function (fragmento) {

				$('#modalMensaje').replaceWith(fragmento);

				var myModal = bootstrap.Modal.getOrCreateInstance(document.querySelector('#modalExitosoError'));
				myModal.show();

			});

			return false;
		}

	});

});