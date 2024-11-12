const assert = require('assert');
const { Given, When, Then } = require('cucumber');
const request = require('sync-request');

Given('que no existe la persona con {string}', 
function (cuil) {
    let res = request('GET',
        'http://backend:8080/personas/' + cuil);
    let response = JSON.parse(res.body, 'utf8');
    return assert.equal(response.status, 404)
});

Given('la persona nueva con {string} {string} {string} {string} {string} {string} {string} {string}',
    function(nombre, apellido, dni, cuil, sexo, titulo, domicilio, telefono) {
    this.aPersona = {
        nombre: nombre,
        apellido: apellido,
        dni: dni,
        cuil: cuil,
        sexo: sexo,
        titulo: titulo,
        domicilio: domicilio,
        telefono: telefono
    };
});

When('se presiona el botón guardar persona', function () {
    let res = request('POST', 
    'http://backend:8080/personas',
    {json: this.aPersona}
    );
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

Then('se espera el siguiente {int} con la {string}', function (aStatus, aRespuesta) {
    assert.equal(this.response.status, aStatus);
    return assert.equal(this.response.message, aRespuesta);
});

// Otro escenario.
Given('que existe la persona con {string}', 
function (cuil) {
    let res = request('GET',
    'http://backend:8080/personas/' + cuil);
    this.aPersona = JSON.parse(res.body, 'utf8').data;
    let response = JSON.parse(res.body, 'utf8');
    return assert.equal(response.status, 200);
});

When('se cambia el nombre a {string}', function (string) {
    let res = request('PUT', 'http://backend:8080/personas',
    {json: this.aPersona});
    this.response = JSON.parse(res.body, 'utf8');

    return assert.equal(res.statusCode, 200);
});

Then('la persona con {string} {string} {string} {string} {string} {string} {string} {string}', 
function (nombre, apellido, dni, cuil, sexo, titulo, domicilio, telefono) {
    if (this.response.status == 200) {
        assert.equal(this.response.data.nombre, nombre);
        assert.equal(this.response.data.apellido, apellido);
        assert.equal(this.response.data.dni, dni);
        assert.equal(this.response.data.cuil, cuil);
        assert.equal(this.response.data.sexo, sexo);
        assert.equal(this.response.data.titulo, titulo);
        assert.equal(this.response.data.domicilio, domicilio);
        assert.equal(this.response.data.telefono, telefono);
    }
});

When('solicito eliminar la persona con {string}', function (cuil) {
    let res = request('DELETE', 
    'http://backend:8080/personas/' + cuil
    );
    this.response = JSON.parse(res.body, 'utf8');

    return assert.equal(res.statusCode, 200);
});


