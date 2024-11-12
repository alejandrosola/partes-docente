# language: es

Característica: Emitir el parte diario de licencias de una escuela para un determinado día

Esquema del escenario: ingresar nuevas personas
    Dado que no existe la persona con "<CUIL>"
    Y la persona nueva con "<nombre>" "<apellido>" "<DNI>" "<CUIL>" "<sexo>" "<título>" "<domicilio>" "<teléfono>"
    Cuando se presiona el botón guardar persona
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | DNI          | nombre       | apellido   | CUIL        | sexo | título                    | domicilio         | teléfono           | status | respuesta                                                       |
      | 88100000     | Raúl         | Orellanos  | 20881000009 | M    | Profesor de Biología      | Calle falsa 111   | +54 (280) 411-1111 | 200    | Raúl Orellanos con CUIL 20881000009 ingresado/a correctamente   |
      | 88200000     | Matías       | Barto      | 20882000009 | M    | Profesor de Matemática    | Calle falsa 222   | +54 (280) 422-2222 | 200    | Matías Barto con CUIL 20882000009 ingresado/a correctamente     |
      | 88300000     | Andrea       | Sosa       | 27883000009 | F    |                           | Calle falsa 333   | +54 (280) 433-3333 | 200    | Andrea Sosa con CUIL 27883000009 ingresado/a correctamente      |
      | 88400000     | Laura        | Barrientos | 27884000009 | F    | Licenciada en Informática | Calle falsa 444   | +54 (280) 444-4444 | 200    | Laura Barrientos con CUIL 27884000009 ingresado/a correctamente |
      | 88500000     | Natalia      | Zabala     | 27885000009 | F    | Profesora de Biología     | Calle falsa 555   | +54 (280) 455-5555 | 200    | Natalia Zabala con CUIL 27885000009 ingresado/a correctamente   |
      | 88600000     | Marta        | Ríos       | 27886000009 | F    |                           | Calle falsa 666   | +54 (280) 466-6666 | 200    | Marta Ríos con CUIL 27886000009 ingresado/a correctamente       |
      | 88700000     | Rosalía      | Ramón      | 27887000009 | F    |                           | Calle falsa 777   | +54 (280) 477-7777 | 200    | Rosalía Ramón con CUIL 27887000009 ingresado/a correctamente    |
      | 88800000     | José         | Pérez      | 20888000009 | M    |                           | Calle falsa 888   | +54 (280) 488-8888 | 200    | José Pérez con CUIL 20888000009 ingresado/a correctamente       |


  Esquema del escenario: ingresar nuevo cargo institucional
    Dado el cargo institucional cuyo "<nombre>" que da título al mismo
    Y que es del "<tipoDesignación>"
    Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
    Y que existe el espacio físico división con "<anio>" "<numero>"
    Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
    Cuando se presiona el botón de guardar cargo institucional
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | nombre          | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno  | status | respuesta                                        |
      | Preceptor/a     | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo de Preceptor/a ingresado correctamente     |
      | Director/a      | CARGO             |            3 | 2020-03-01 |            |      |        |        |    200 | Cargo de Director/a ingresado correctamente      |
      | Vicedirector/a  | CARGO             |           30 | 2020-03-01 |            |      |        |        |    200 | Cargo de Vicedirector/a ingresado correctamente  |
      | Auxiliar ADM    | CARGO             |            5 | 2020-03-01 |            |      |        |        |    200 | Cargo de Auxiliar ADM ingresado correctamente    |
      | Tutor/a         | CARGO             |            6 | 2020-03-01 |            |      |        |        |    200 | Cargo de Tutor/a ingresado correctamente         |
      | Bibliotecario/a | CARGO             |            4 | 2020-03-01 | 2030-03-01 |      |        |        |    200 | Cargo de Bibliotecario/a ingresado correctamente |
      | Consejero/a     | CARGO             |            6 | 2020-03-01 |            |      |        |        |    200 | Cargo de Consejero/a ingresado correctamente     |
      | Enfermero/a     | CARGO             |            6 | 2020-03-01 |            |      |        |        |    200 | Cargo de Enfermero/a ingresado correctamente     |
      | Secretario/a    | CARGO             |            6 | 2020-03-01 |            |      |        |        |    200 | Cargo de Secretario/a ingresado correctamente    |

  Esquema del escenario: Designación de persona en cargos NO cubiertos aún en el período indicado de forma existosa
    Dada la persona con "<CUIL>" "<nombre>" y "<apellido>"
    Y que se asigna al cargo con tipo de designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>"
    Y se designa por el período "<fechaDesde>" "<fechaHasta>"
    Y que su situación revista es "<situacionRevista>"
    Cuando se presiona el botón guardar designacion
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | situacionRevista | CUIL        | nombre      | apellido     | tipo   | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                                                                |
      | Titular          | 20881000009 | Raúl        | Orellanos    | CARGO  | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Raúl Orellanos ha sido designado/a como Preceptor/a exitosamente         |
      | Titular          | 20882000009 | Matías      | Barto        | CARGO  | Director/a        |     |        |        | 2023-03-01 | 2025-12-31 |    200 | Matías Barto ha sido designado/a como Director/a exitosamente            |
      | Titular          | 27883000009 | Andrea      | Sosa         | CARGO  | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Andrea Sosa ha sido designado/a como Auxiliar ADM exitosamente           |
      | Titular          | 27884000009 | Laura       | Barrientos   | CARGO  | Vicedirector/a    |     |        |        | 2023-03-01 |            |    200 | Laura Barrientos ha sido designado/a como Vicedirector/a exitosamente    |
      | Titular          | 27885000009 | Natalia     | Zabala       | CARGO  | Tutor/a           |     |        |        | 2023-03-01 |            |    200 | Natalia Zabala ha sido designado/a como Tutor/a exitosamente             |
      | Titular          | 27886000009 | Marta       | Ríos         | CARGO  | Consejero/a       |     |        |        | 2023-03-01 |            |    200 | Marta Ríos ha sido designado/a como Consejero/a exitosamente             |
      | Titular          | 27887000009 | Rosalía     | Ramón        | CARGO  | Enfermero/a       |     |        |        | 2023-03-01 |            |    200 | Rosalía Ramón ha sido designado/a como Enfermero/a exitosamente          |
      | Titular          | 20888000009 | José        | Pérez        | CARGO  | Secretario/a      |     |        |        | 2023-03-01 |            |    200 | José Pérez ha sido designado/a como Secretario/a exitosamente            |

  Esquema del escenario: Otorgar Distintas licencias según las reglas de los distintos artículos
    Dado el docente con CUIL "<CUIL>", nombre "<Nombre>" y apellido "<Apellido>"
    Cuando solicita una licencia artículo "<Artículo>" con descripción "<Descripción>" para el período "<Desde>" "<Hasta>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL        | Nombre      | Apellido     | Artículo | Descripción                   | Desde      | Hasta      | status | respuesta                                                                                                                      |
      | 20881000009 | Raúl        | Orellanos    |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-07 | 2023-05-15 |    200 | Se otorga Licencia artículo 5A a Raúl Orellanos       |
      | 20882000009 | Matías      | Barto        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-10 | 2023-05-15 |    200 | Se otorga Licencia artículo 5A a Matías Barto         |
      | 27883000009 | Andrea      | Sosa         |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-11 | 2023-05-17 |    200 | Se otorga Licencia artículo 5A a Andrea Sosa          |
      | 27884000009 | Laura       | Barrientos   |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-05-08 | 2023-05-16 |    200 | Se otorga Licencia artículo 23A a Laura Barrientos     |
      | 27885000009 | Natalia     | Zabala       |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-05-13 | 2023-05-22 |    200 | Se otorga Licencia artículo 23A a Natalia Zabala       |

    Escenario: Verificar el funcionamiento de licencias para un día
    Dada la existencia de las siguientes licencias 
      | CUIL         | Nombre       | Apellido  | Artículo  | Descripción                    | Desde        | Hasta        | 
      | 20881000009  | Raúl         | Orellanos | 5A        | ENFERMEDAD DE CORTA EVOLUCIÓN  | 2023-05-07   | 2023-05-15   | 
      | 20882000009  | Matías       | Barto     | 5A        | ENFERMEDAD DE CORTA EVOLUCIÓN  | 2023-05-10   | 2023-05-15   | 
      | 27883000009  | Andrea       | Sosa      | 5A        | ENFERMEDAD DE CORTA EVOLUCIÓN  | 2023-05-11   | 2023-05-17   | 
      | 27884000009  | Laura        | Barrientos| 23A       | ATENCIÓN DE UN MIEMBRO DEL GF  | 2023-05-08   | 2023-05-16   | 
      | 27885000009  | Natalia      | Zabala    | 23A       | ATENCIÓN DE UN MIEMBRO DEL GF  | 2023-05-13   | 2023-05-22   | 
    Y que se otorgan las siguientes nuevas licencias
      | CUIL         | Nombre       | Apellido  | Artículo  | Descripción                    | Desde        | Hasta        | 
      | 27886000009  | Marta        | Ríos      | 36A       | ASUNTOS PARTICULARES           | 2023-05-15   | 2023-05-15   | 
      | 27887000009  | Rosalía      | Ramón     | 36A       | ASUNTOS PARTICULARES           | 2023-05-15   | 2023-05-15   | 
      | 20888000009  | José         | Pérez     | 36A       | ASUNTOS PARTICULARES           | 2023-05-15   | 2023-05-15   | 
    Cuando se solicita el parte diario para la fecha "2023-05-15"
    Entonces el sistema responde
      """
      {
            "fecha": "2023-05-15",
            "docentes": [
                {"CUIL": "20881000009", "Nombre": "Raúl", "Apellido": "Orellanos","Articulo": "5A", "Descripcion": "ENFERMEDAD DE CORTA EVOLUCIÓN","Desde": "2023-05-07", "Hasta": "2023-05-15"},
                {"CUIL": "20882000009", "Nombre": "Matías", "Apellido": "Barto","Articulo": "5A", "Descripcion": "ENFERMEDAD DE CORTA EVOLUCIÓN","Desde": "2023-05-10", "Hasta": "2023-05-15"},
                {"CUIL": "27883000009", "Nombre": "Andrea", "Apellido": "Sosa","Articulo": "5A", "Descripcion": "ENFERMEDAD DE CORTA EVOLUCIÓN","Desde": "2023-05-11", "Hasta": "2023-05-17"},
                {"CUIL": "27884000009", "Nombre": "Laura", "Apellido": "Barrientos","Articulo": "23A", "Descripcion": "ATENCIÓN DE UN MIEMBRO DEL GF","Desde": "2023-05-08", "Hasta": "2023-05-16"},
                {"CUIL": "27885000009", "Nombre": "Natalia", "Apellido": "Zabala","Articulo": "23A", "Descripcion": "ATENCIÓN DE UN MIEMBRO DEL GF","Desde": "2023-05-13", "Hasta": "2023-05-22"},
                {"CUIL": "27886000009", "Nombre": "Marta", "Apellido": "Ríos","Articulo": "36A", "Descripcion": "ASUNTOS PARTICULARES","Desde": "2023-05-15", "Hasta": "2023-05-15"},
                {"CUIL": "27887000009", "Nombre": "Rosalía", "Apellido": "Ramón","Articulo": "36A", "Descripcion": "ASUNTOS PARTICULARES","Desde": "2023-05-15", "Hasta": "2023-05-15"},
                {"CUIL": "20888000009", "Nombre": "José", "Apellido": "Pérez","Articulo": "36A", "Descripcion": "ASUNTOS PARTICULARES","Desde": "2023-05-15", "Hasta": "2023-05-15"}
            ]
      }
      """




  Esquema del escenario: eliminar licencias
    Dado que existe la licencia "<Artículo>" "<Desde>" "<Hasta>" "<CUIL>"
    Cuando solicito eliminar la licencia
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL        | Artículo | Desde      | Hasta      | status | respuesta                        |
      | 20881000009 |       5A | 2023-05-07 | 2023-05-15 |    200 | Licencia eliminada correctamente |
      | 20882000009 |       5A | 2023-05-10 | 2023-05-15 |    200 | Licencia eliminada correctamente |
      | 27883000009 |       5A | 2023-05-11 | 2023-05-17 |    200 | Licencia eliminada correctamente |
      | 27884000009 |      23A | 2023-05-08 | 2023-05-16 |    200 | Licencia eliminada correctamente |
      | 27885000009 |      23A | 2023-05-13 | 2023-05-22 |    200 | Licencia eliminada correctamente |
      | 27886000009 |      36A | 2023-05-15 | 2023-05-15 |    200 | Licencia eliminada correctamente | 
      | 27887000009 |      36A | 2023-05-15 | 2023-05-15 |    200 | Licencia eliminada correctamente | 
      | 20888000009 |      36A | 2023-05-15 | 2023-05-15 |    200 | Licencia eliminada correctamente | 


  Esquema del escenario: eliminar designacion existente
    Dado que existe la designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>" "<fechaDesde>" "<fechaHasta>"
    Cuando solicito eliminar la designación
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos:
      | tipo   | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                           |
      | CARGO  | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | CARGO  | Director/a        |     |        |        | 2023-03-01 | 2025-12-31 |    200 | Designación eliminada correctamente |
      | CARGO  | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | CARGO  | Vicedirector/a    |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | CARGO  | Tutor/a           |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | CARGO  | Consejero/a       |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | CARGO  | Enfermero/a       |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | CARGO  | Secretario/a      |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |


  Esquema del escenario: eliminar un cargo institucional
    Dado el cargo institucional cuyo "<nombre>" que da título al mismo
    Y que es del "<tipoDesignación>"
    Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
    Y que existe el espacio físico división con "<anio>" "<numero>"
    Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
    Cuando se presiona el botón de eliminar cargo institucional
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | nombre          | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | status | respuesta                     |
      | Preceptor/a     | CARGO             |           36 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Director/a      | CARGO             |            3 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Vicedirector/a  | CARGO             |           30 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Auxiliar ADM    | CARGO             |            5 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Tutor/a         | CARGO             |            6 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Bibliotecario/a | CARGO             |            4 | 2020-03-01 | 2030-03-01 |      |        |    200 | Cargo eliminado correctamente |
      | Consejero/a     | CARGO             |            6 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Enfermero/a     | CARGO             |            6 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Secretario/a    | CARGO             |            6 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |

  Esquema del escenario: eliminar persona que existe
    Dado que existe la persona con "<CUIL>"
    Cuando solicito eliminar la persona con "<CUIL>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos:
      | CUIL        | status | respuesta                       |
      | 20881000009 | 200    | Persona eliminada correctamente |
      | 20882000009 | 200    | Persona eliminada correctamente |
      | 27883000009 | 200    | Persona eliminada correctamente |
      | 27884000009 | 200    | Persona eliminada correctamente |
      | 27885000009 | 200    | Persona eliminada correctamente |
      | 27886000009 | 200    | Persona eliminada correctamente |
      | 27887000009 | 200    | Persona eliminada correctamente |
      | 20888000009 | 200    | Persona eliminada correctamente |
