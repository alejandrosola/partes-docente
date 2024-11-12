const assert = require('assert');
const { Given, When } = require('cucumber');
const request = require('sync-request');

Given('que no existe el espacio físico división con {string} {string}', 
function (anio, numero) {
    let res = request('GET', 
    'http://backend:8080/divisiones/' + anio + "-" + numero);
    
    let response = JSON.parse(res.body, 'utf8');
    return assert.equal(response.status, 404)
});


When('solicito guardar la división {string} {string} {string} {string}', function (anio, numero, orientacion, turno) {
    let aDivision = {
        'anio': +anio,
        'numero': +numero,
        'orientacion': orientacion,
        'turno': turno
    }

    let res = request('POST', 
    'http://backend:8080/divisiones',
    {json: aDivision}
    );
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

Given('que existe el espacio físico división con {string} {string}', function (anio, numero) {
    if (anio != "" && numero != "") {
        let res = request('GET', 
        'http://backend:8080/divisiones/' + anio + "-" + numero);
        this.aDivision = JSON.parse(res.body, 'utf8').data;
        let response = JSON.parse(res.body, 'utf8');
        return assert.equal(response.status, 200);
    }
});


When('solicito editar la orientación de la división {string} {string} a {string}', function (anio, numero, nuevaOrientacion) {
    this.aDivision.orientacion = nuevaOrientacion;
    let res = request('PUT', 'http://backend:8080/divisiones',
        {json: this.aDivision}
    );
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

When('solicito eliminar la división {string} {string} {string} {string}', function (anio, numero, _orientacion, _turno) {
    let res = request('DELETE',
    'http://backend:8080/divisiones/' + anio + "-" + numero);
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});