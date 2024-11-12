const assert = require('assert');
const { When, Given, Then } = require('cucumber');
const request = require('sync-request');
const jd = require('json-diff');


Given('el docente con CUIL {string}, nombre {string} y apellido {string}', function (cuil, nombre, apellido) {
      this.aLicencia = {};
      let res = request('GET', 'http://backend:8080/personas/' + cuil)
      let response = JSON.parse(res.body, 'utf8');
      this.aLicencia.persona = response.data;

      return assert.equal(res.statusCode, 200);
    });

When('solicita una licencia artículo {string} con descripción {string} para el período {string} {string}', 
    function (articulo, descripcion, fechaDesde, fechaHasta) {
    this.aLicencia.pedidoDesde = fechaDesde;
    this.aLicencia.pedidoHasta = fechaHasta;
    this.aLicencia.certificadoMedico = false; // ? No está en el test, después lo agrego
    let res = request('GET', 'http://backend:8080/articulos/' + articulo);
    this.aLicencia.articulo = JSON.parse(res.body, 'utf8').data;

    res = request('POST', 'http://backend:8080/licencias', {json: this.aLicencia});
    this.response = JSON.parse(res.body, 'utf8');
    assert.equal(res.statusCode, 200);
});

Given('que existe la licencia {string} {string} {string} {string}', function (articulo, desde, hasta, cuil) {
    let res = request('GET', 'http://backend:8080/licencias');
    let licencias = JSON.parse(res.body, 'utf8').data;
    let index;

    index = licencias.findIndex((index) => (index.articulo.nombre == articulo && index.pedidoDesde == desde &&
        index.pedidoHasta == hasta && index.persona.cuil == cuil ))

    this.aLicencia = licencias[index];
    return assert.notEqual(index, -1);
});

When('solicito eliminar la licencia', function () {

    let res = request('DELETE', 'http://backend:8080/licencias/id/' + this.aLicencia.id);
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

Given('que existe la persona', function (dataTable) {
    let filas = dataTable.rows();

    let cuil = filas[0][0];
    let res = request('GET', 'http://backend:8080/personas/' + cuil);

    this.aDesignacion = {
        id: 0,
        persona: JSON.parse(res.body, 'utf8').data
    }
    return assert.equal(res.statusCode, 200);
});

Given('que existen las siguientes instancias de designación asignada', function (dataTable) {
    let filas = dataTable.rows();
    let res = request('GET', 'http://backend:8080/cargos');
    let cargos = JSON.parse(res.body, 'utf8').data;
    let index;    

    let nombre = filas[0][1];
    index = cargos.findIndex((index) => (index.nombre == nombre));

    this.aDesignacion.cargo = cargos[index];
    return assert.notEqual(index, -1);

});

Given('que la instancia de designación está asignada a la persona con licencia {string} comprendida en el período desde {string} hasta {string}',
    function (articulo, fechaDesde, fechaHasta, dataTable) {
    let filas = dataTable.rows();
    let res = request('GET', 'http://backend:8080/licencias');
    let licencias = JSON.parse(res.body, 'utf8').data;
    let index;
    let cuil = filas[0][0];

    index = licencias.findIndex((index) => (index.articulo.nombre == articulo) && (index.pedidoDesde == fechaDesde) &&
        (index.pedidoHasta == fechaHasta) && (index.persona.cuil == cuil));
    return assert.notEqual(index, -1);        
});

When('se solicita el servicio de designación de la persona al cargo en el período comprendido desde {string} hasta {string}', 
    function (fechaDesde, fechaHasta) {
    this.aDesignacion.fechaInicio = fechaDesde;
    this.aDesignacion.fechaFin = fechaHasta;
    this.aDesignacion.situacionRevista = "Suplente";

    let res = request('POST', 'http://backend:8080/designaciones', 
    {json: this.aDesignacion});
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

Then('se recupera el mensaje', function (jsonResponse) {
    delete this.response.data;
    let d = jd.diff(JSON.parse(jsonResponse), this.response);
    return assert.equal(d, null);
});


Given('que la instancia de designación está asignada a la persona', function (dataTable) {
    let filas = dataTable.rows();
    let res = request('GET', 'http://backend:8080/designaciones');
    let designaciones = JSON.parse(res.body, 'utf8').data;
    let index;
    let cuil = filas[0][0];

    index = designaciones.findIndex((index) => (index.persona.cuil == cuil) && (index.cargo.nombre == this.aDesignacion.cargo.nombre));
    return assert.notEqual(index, -1);
});