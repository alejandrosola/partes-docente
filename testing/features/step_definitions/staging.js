const { BeforeAll, AfterAll } = require('cucumber');
const request = require('sync-request');

BeforeAll(async function () {
    // Guardar los cargos
    let res = request('GET', 'http://backend:8080/cargos');
    let response = JSON.parse(res.body, 'utf8');
    this.cargos = response.data;

    // Guardo las divisiones
    res = request('GET', 'http://backend:8080/divisiones');
    response = JSON.parse(res.body, 'utf8');
    this.divisiones = response.data;

    // Guardo las personas
    res = request('GET', 'http://backend:8080/personas');
    response = JSON.parse(res.body, 'utf8');
    this.personas = response.data;

    // Guardo las designaciones
    res = request('GET', 'http://backend:8080/designaciones');
    response = JSON.parse(res.body, 'utf8');
    this.designaciones = response.data;

    // Guardo las licencias
    res = request('GET', 'http://backend:8080/licencias');
    response = JSON.parse(res.body, 'utf8');
    this.licencias = response.data;
    
    for (let designacion of this.designaciones) {
        await request('DELETE', `http://backend:8080/designaciones/id/${designacion.id}`);
    }
    
    for (let licencia of this.licencias) {
        await request('DELETE', `http://backend:8080/licencias/id/${licencia.id}`);
    }


    for (let cargo of this.cargos) {
        await request('DELETE', `http://backend:8080/cargos/id/${cargo.id}`);
    }

    for (let division of this.divisiones) {
        await request('DELETE', 'http://backend:8080/divisiones/' + division.anio + "-" + division.numero);
    }

    for (let persona of this.personas) {
        await request('DELETE', 'http://backend:8080/personas/' + persona.cuil);
    }

    res = request('POST', 'http://backend:8080/cargos',
    {json:{
        id: 0,
        nombre: "Portero/a",
        cargaHoraria: 36,
        fechaInicio: "2020-03-01",
        fechaFin: "",
        division: null,
        tipoDesignacion: "CARGO",
        horarios: []
    }});

    this.idPortero = JSON.parse(res.body).data.id;
});

AfterAll(async function () {
    await request('DELETE', 'http://backend:8080/cargos/id/' + this.idPortero);

    // Primero agrego las divisiones, despues los cargos, despues las designaciones
    for (let persona of this.personas) {
        await request('POST', 'http://backend:8080/personas', {json: persona});
    }
    
    for(let division of this.divisiones) {
        await request('POST', 'http://backend:8080/divisiones', {json:division});
    }

    for (let cargo of this.cargos) {
        await request('POST', 'http://backend:8080/cargos', {json: cargo})
    }

    for (let designacion of this.designaciones) {
        let res = await request('GET', 'http://backend:8080/personas/' + designacion.persona.cuil);
        designacion.persona = JSON.parse(res.body).data;
        if (designacion.cargo.tipoDesignacion == 'ESPACIOCURRICULAR') {
            res = await request('GET', `http://backend:8080/cargos/espaciocurricular/${encodeURIComponent(designacion.cargo.nombre)}-${encodeURIComponent(designacion.cargo.division.anio)}-${encodeURIComponent(designacion.cargo.division.numero)}`);
            designacion.cargo = JSON.parse(res.body).data;
            res = await request('GET', 'http://backend:8080/divisiones/' + designacion.cargo.division.anio + "-" + designacion.cargo.division.numero);
            designacion.cargo.division = JSON.parse(res.body).data;    
        } else {
            res = await request('GET', `http://backend:8080/cargos/cargo/${encodeURIComponent(designacion.cargo.nombre)}`);
            designacion.cargo = JSON.parse(res.body).data;
        }
        res = await request('POST', 'http://backend:8080/designaciones', {json: designacion});
    }

    for (let licencia of this.licencias) {
        let res = await request('GET', 'http://backend:8080/personas/' + licencia.persona.cuil);
        licencia.persona = JSON.parse(res.body).data;
        res = await request('POST', 'http://backend:8080/licencias', {json: licencia});
    }
});