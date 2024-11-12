const assert = require('assert');
const { Given, When } = require('cucumber');
const request = require('sync-request');

Given('la persona con {string} {string} y {string}', function (cuil, nombre, apellido) {
   let res = request('GET', `http://backend:8080/personas/${cuil}`);
   let response = JSON.parse(res.body, 'utf8');
   
    this.aDesignacion = {
        persona: response.data,
        situacionRevista: "",
        fechaInicio: "",
        fechaFin: "",
        cargo: null
    }
   return assert.equal(response.status, 200);
});


Given('que se asigna al cargo con tipo de designación {string} {string} y división con {string} {string}', 
    function (tipo, nombreDesignacion, anio, numero) {
    let res = request('GET', 'http://backend:8080/cargos');
    let cargos = JSON.parse(res.body, 'utf8').data;
    let index;

    if (tipo === "ESPACIOCURRICULAR") {
        index = cargos.findIndex((index) => (index.nombre == nombreDesignacion) && (index.division.anio == +anio) &&
        (index.division.numero == +numero));
    } else {
        index = cargos.findIndex((index) => (index.nombre == nombreDesignacion));
        this.aDesignacion.cargo = cargos[index];
    }

    this.aDesignacion.cargo = cargos[index];
    return assert.notEqual(this.aDesignacion.cargo, undefined);
});

Given('se designa por el período {string} {string}', function (fechaDesde, fechaHasta) {
    this.aDesignacion.fechaInicio = fechaDesde;
    this.aDesignacion.fechaFin = fechaHasta;
});

Given('que su situación revista es {string}', function (situacionRevista) {
    this.aDesignacion.situacionRevista = situacionRevista;
});

When('se presiona el botón guardar designacion', function () {

    let res = request('POST', 
    'http://backend:8080/designaciones',
    {json: this.aDesignacion}
    );
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

Given('que existe la designación {string} {string} y división con {string} {string} {string} {string}', 
    function (tipo, nombre, anio, numero, fechaDesde, fechaHasta) {
    
    let res = request('GET', 'http://backend:8080/designaciones');
    let designaciones = JSON.parse(res.body, 'utf8').data;
    let index;

    if (tipo == "ESPACIOCURRICULAR") {
        index = designaciones.findIndex((index) => (index.cargo.nombre == nombre) && (index.cargo.division.anio == anio) &&
        (index.cargo.division.numero == numero) && (index.fechaInicio === fechaDesde) && 
        ((!index.fechaFin && !fechaHasta) || (index.fechaFin === fechaHasta)));
    } else {
        index = designaciones.findIndex((index) => (index.cargo.nombre == nombre) && (index.fechaInicio === fechaDesde) && 
        ((!index.fechaFin && !fechaHasta) || (index.fechaFin === fechaHasta)));
    }
    this.aDesignacion = designaciones[index];
    return assert.notEqual(index, -1);

});

When('solicito eliminar la designación', function () {
    let res = request('DELETE', 'http://backend:8080/designaciones/id/' + this.aDesignacion.id);
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

