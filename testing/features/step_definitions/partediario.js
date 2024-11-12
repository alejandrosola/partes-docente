const assert = require('assert');
const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const jd = require('json-diff');

Given('la existencia de las siguientes licencias', function (dataTable) {
    let filas = dataTable.rows();

    let res = request('GET', 'http://backend:8080/licencias');
    let licencias = JSON.parse(res.body, 'utf8').data;

    let index;
    for (let fila of filas) {
        index = licencias.findIndex((index) => (index.persona.cuil == fila[0]) && (index.articulo.nombre == fila[3]) 
            && (index.pedidoDesde == fila[5]) && index.pedidoHasta == fila[6]);
        return assert.notEqual(index, -1);
    }

    return assert.ok(true);
});

Given('que se otorgan las siguientes nuevas licencias', function (dataTable) {

    let filas = dataTable.rows();
    let licencia;
    let res;
    let aPersona;
    let anArticulo
    for (let fila of filas) {
        res = request('GET', 'http://backend:8080/personas/' + fila[0]);
        aPersona = JSON.parse(res.body, 'utf8').data;
        assert.equal(JSON.parse(res.body).status, 200);
        res = request('GET', 'http://backend:8080/articulos/' + fila[3]);
        anArticulo = JSON.parse(res.body, 'utf8').data;
        assert.equal(JSON.parse(res.body).status, 200);

        licencia = {
            id: 0,
            persona: aPersona,
            pedidoDesde: fila[5],
            pedidoHasta: fila[6],
            certificadoMedico: false, 
            articulo: anArticulo
        };
        res = request('POST', 'http://backend:8080/licencias', {json: licencia});
        assert.equal(JSON.parse(res.body).status, 200);
    }

    assert.ok(true);
});

When('se solicita el parte diario para la fecha {string}', function (fecha) {
    let res = request('GET', 'http://backend:8080/licencias/partediario/' + fecha);
    this.aParteDiario = JSON.parse(res.body, 'utf8').data;

    assert.equal(JSON.parse(res.body, 'utf8').status, 200);
});

Then('el sistema responde', function (jsonResponse) {
    let newDocentes = [];
    for (let docente of this.aParteDiario.docentes) {
        docente = {
            CUIL: docente.persona.cuil, Nombre: docente.persona.nombre, Apellido: docente.persona.apellido,Articulo: docente.articulo.nombre, Descripcion: docente.articulo.descripcion,Desde: docente.pedidoDesde,Hasta: docente.pedidoHasta
        };
        newDocentes.push(docente);
    }
    this.aParteDiario.docentes = newDocentes;

    let d = jd.diff(JSON.parse(jsonResponse), this.aParteDiario);
    return assert.equal(d, null);
});