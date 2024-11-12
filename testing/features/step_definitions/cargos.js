const assert = require('assert');
const { Given, When } = require('cucumber');
const request = require('sync-request');

Given('el cargo institucional cuyo {string} que da título al mismo', function(nombre) {
    this.aCargo = {
        nombre: nombre,
        tipoDesignacion: "",
        cargaHoraria: 0,
        fechaInicio: "",
        fechaFin: "",
        division: {}
    }
});


Given('que es del {string}', function(tipoDesignacion) {
    this.aCargo.tipoDesignacion = tipoDesignacion;
});

Given('que tiene una {int} con la vigencia {string} {string}', function(cargaHoraria, fechaDesde, fechaHasta) {
    this.aCargo.cargaHoraria = cargaHoraria;
    this.aCargo.fechaInicio = fechaDesde;
    this.aCargo.fechaFin = fechaHasta;
});

Given('que si el tipo es espacio curricular, opcionalmente se asigna a la división {string} {string}', 
function(anio, numero) {
    if (this.aCargo.tipoDesignacion === "ESPACIOCURRICULAR") {
        let res = request('GET', 
        'http://backend:8080/divisiones/' + anio + "-" + numero );
        this.aCargo.division = JSON.parse(res.body, 'utf8').data;
        let response = JSON.parse(res.body, 'utf8');
        return assert.equal(response.status, 200);
    } else {
        this.aCargo.division = null;
    }
});

When('se presiona el botón de guardar cargo institucional', function() {
    this.aCargo.horarios = [{
        id: 0,
        dia: "Lunes",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Martes",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Miércoles",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Jueves",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Viernes",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Sábado",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }, {
        id: 0,
        dia: "Domingo",
        hora: "13:00:00",
        horaFin: "13:30:00"
    }];
    let res = request('POST', 
    'http://backend:8080/cargos',
    { json: this.aCargo }
    );
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});

When('se presiona el botón de eliminar cargo institucional', function () {
    let res = request('GET', 'http://backend:8080/cargos');
    let cargos = JSON.parse(res.body, 'utf8').data;
    let index;

    if (this.aCargo.tipoDesignacion === "ESPACIOCURRICULAR") {
        index = cargos.findIndex((index) => (index.nombre == this.aCargo.nombre) && (index.division.anio == this.aCargo.division.anio) &&
        (index.division.numero == this.aCargo.division.numero) && (index.division.turno == this.aCargo.division.turno));
    } else {
        index = cargos.findIndex((index) => (index.nombre == this.aCargo.nombre));
    }
    res = request('DELETE', 'http://backend:8080/cargos/id/' + cargos[index].id);
    this.response = JSON.parse(res.body, 'utf8');
    return assert.equal(res.statusCode, 200);
});