# language: es
Característica: otorgar o denegar licencia a una persona a un cargo docente
   actividad central de información de la escuela secundaria

  Esquema del escenario: ingresar nuevas personas
    Dado que no existe la persona con "<CUIL>"
    Y la persona nueva con "<nombre>" "<apellido>" "<DNI>" "<CUIL>" "<sexo>" "<título>" "<domicilio>" "<teléfono>"
    Cuando se presiona el botón guardar persona
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | DNI      | nombre      | apellido     | CUIL        | sexo | título                | domicilio       | teléfono           | status | respuesta                                                          |
      | 20200200 | Susana      | Álvarez      | 20202002009 | F    | Profesora de historia | Mitre 154       | +54 (280) 422-2222 |    200 | Susana Álvarez con CUIL 20202002009 ingresado/a correctamente      |
      | 30300300 | María Rosa  | Gallo        | 27303003009 | M    |                       | Jujuy 255       | +54 (280) 433-3333 |    200 | María Rosa Gallo con CUIL 27303003009 ingresado/a correctamente    |
      | 40400400 | Marisa      | Amuchástegui | 20404004009 | F    | Profesora de historia | Zar 555         | +54 (280) 444-4444 |    200 | Marisa Amuchástegui con CUIL 20404004009 ingresado/a correctamente |
      | 50500500 | Raúl        | Gómez        | 27505005009 | M    | Profesor de Geografía | Roca 2458       | +54 (280) 455-5555 |    200 | Raúl Gómez con CUIL 27505005009 ingresado/a correctamente          |
      | 70700700 | Homero      | Manzi        | 27707007009 | M    |                       | Mitre 1855      | +54 (280) 477-7777 |    200 | Homero Manzi con CUIL 27707007009 ingresado/a correctamente        |
      | 20000000 | Rosalía     | Fernandez    | 20200000009 | F    | Maestra de grado      | Maiz 356        | +54 (280) 420-0000 |    200 | Rosalía Fernandez con CUIL 20200000009 ingresado/a correctamente   |
      | 80800800 | Ermenegildo | Sábat        | 20808008009 | F    | Técnica superior      | Rosa 556        | +54 (280) 488-8888 |    200 | Ermenegildo Sábat con CUIL 20808008009 ingresado/a correctamente   |
      | 50409500 | Raúl        | Gómez        | 27504095009 | M    |                       | José Sanz 639   | +54 (280) 453-4530 |    200 | Raúl Gómez con CUIL 27504095009 ingresado/a correctamente          |
      | 70700700 | Jorge       | Dismal       | 20707007009 | M    |                       | Calle Falsa 123 | +54 (280) 478-1221 |    200 | Jorge Dismal con CUIL 20707007009 ingresado/a correctamente        |
      | 80800800 | Analía      | Rojas        | 20898008009 | F    |                       | Calle Falsa 123 | +54 (280) 478-9312 |    200 | Analía Rojas con CUIL 20898008009 ingresado/a correctamente        |


  Esquema del escenario: ingresar división no existente
    Dado que no existe el espacio físico división con "<año>" "<número>"
    Cuando solicito guardar la división "<año>" "<número>" "<orientación>" "<turno>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | año | número | orientación | turno  | status | respuesta                              |
      |   5 |      2 | Biológicas  | Mañana |    200 | División 5° 2° ingresada correctamente |
      |   3 |      1 | Sociales    | Tarde  |    200 | División 3° 1° ingresada correctamente |
      |   2 |      3 | Matemática  | Mañana |    200 | División 2° 3° ingresada correctamente |
      |   1 |      1 | Sociales    | Tarde  |    200 | División 1° 1° ingresada correctamente |
      |   4 |      3 | Informática | Mañana |    200 | División 4° 3° ingresada correctamente |

  Esquema del escenario: ingresar nuevo cargo institucional
    Dado el cargo institucional cuyo "<nombre>" que da título al mismo
    Y que es del "<tipoDesignación>"
    Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
    Y que existe el espacio físico división con "<anio>" "<numero>"
    Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
    Cuando se presiona el botón de guardar cargo institucional
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | nombre       | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno  | status | respuesta                                                                    |
      | Preceptor/a  | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo de Preceptor/a ingresado correctamente                                 |
      | Geografía    | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 | Tarde  |    200 | Espacio Curricular Geografía para la división 3° 1° ingresado correctamente  |
      | Auxiliar ADM | CARGO             |           30 | 2020-03-01 |            |      |        |        |    200 | Cargo de Auxiliar ADM ingresado correctamente                                |
      | Física       | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 | Mañana |    200 | Espacio Curricular Física para la división 2° 3° ingresado correctamente     |
      | Matemática   | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 | Tarde  |    200 | Espacio Curricular Matemática para la división 1° 1° ingresado correctamente |
      | Tecnología   | ESPACIOCURRICULAR |            4 | 2020-03-01 | 2030-03-01 |    4 |      3 | Mañana |    200 | Espacio Curricular Tecnología para la división 4° 3° ingresado correctamente |

  Esquema del escenario: Designación de persona en cargos NO cubiertos aún en el período indicado de forma existosa
    Dada la persona con "<CUIL>" "<nombre>" y "<apellido>"
    Y que se asigna al cargo con tipo de designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>"
    Y se designa por el período "<fechaDesde>" "<fechaHasta>"
    Y que su situación revista es "<situacionRevista>"
    Cuando se presiona el botón guardar designacion
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | situacionRevista | CUIL        | nombre      | apellido     | tipo              | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                                                                                        |
      | Titular          | 20202002009 | Susana      | Álvarez      | CARGO             | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Susana Álvarez ha sido designado/a como Preceptor/a exitosamente                                 |
      | Titular          | 27505005009 | Raúl        | Gómez        | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-03-01 | 2025-12-31 |    200 | Raúl Gómez ha sido designado/a a la asignatura Geografía a la división 3° 1° exitosamente        |
      | Titular          | 20200000009 | Rosalía     | Fernandez    | CARGO             | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Rosalía Fernandez ha sido designado/a como Auxiliar ADM exitosamente                             |
      | Titular          | 20808008009 | Ermenegildo | Sábat        | ESPACIOCURRICULAR | Física            |   2 |      3 | Mañana | 2023-03-01 |            |    200 | Ermenegildo Sábat ha sido designado/a a la asignatura Física a la división 2° 3° exitosamente    |
      | Titular          | 27303003009 | María Rosa  | Gallo        | ESPACIOCURRICULAR | Matemática        |   1 |      1 | Tarde  | 2023-03-01 |            |    200 | María Rosa Gallo ha sido designado/a a la asignatura Matemática a la división 1° 1° exitosamente |
      | Titular          | 27707007009 | Homero      | Manzi        | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Mañana | 2023-03-01 | 2028-03-01 |    200 | Homero Manzi ha sido designado/a a la asignatura Tecnología a la división 4° 3° exitosamente     |
      | Titular          | 20404004009 | Marisa      | Amuchástegui | CARGO             | Portero/a         |     |        |        | 2023-03-01 |            |    200 | Marisa Amuchástegui ha sido designado/a como Portero/a exitosamente                              |

  Esquema del escenario: Otorgar Distintas licencias según las reglas de los distintos artículos
    Dado el docente con CUIL "<CUIL>", nombre "<Nombre>" y apellido "<Apellido>"
    Cuando solicita una licencia artículo "<Artículo>" con descripción "<Descripción>" para el período "<Desde>" "<Hasta>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL        | Nombre      | Apellido     | Artículo | Descripción                   | Desde      | Hasta      | status | respuesta                                                                                                                      |
      | 20808008009 | Ermenegildo | Sábat        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-07 | 2023-05-17 |    200 | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                             |
      | 20808008009 | Ermenegildo | Sábat        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-18 | 2023-05-31 |    200 | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                             |
      | 20808008009 | Ermenegildo | Sábat        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-06-01 | 2023-06-12 |    409 | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                       |
      | 20808008009 | Ermenegildo | Sábat        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-01 | 2023-10-03 |    200 | Se otorga Licencia artículo 5A a Ermenegildo Sábat                                                                             |
      | 20808008009 | Ermenegildo | Sábat        |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-10-04 | 2023-10-10 |    409 | NO se otorga Licencia artículo 5A a Ermenegildo Sábat debido a que supera el tope de 30 días de licencia                       |
      | 27303003009 | María Rosa  | Gallo        |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-02-15 | 2023-03-01 |    409 | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que el agente no posee ningún cargo en la institución           |
      | 27303003009 | María Rosa  | Gallo        |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-01 | 2023-04-14 |    200 | Se otorga Licencia artículo 23A a María Rosa Gallo                                                                             |
      | 27303003009 | María Rosa  | Gallo        |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-12 | 2023-04-20 |    409 | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que ya posee una licencia en el mismo período                   |
      | 27303003009 | María Rosa  | Gallo        |      23A | ATENCIÓN DE UN MIEMBRO DEL GF | 2023-04-17 | 2023-05-30 |    409 | NO se otorga Licencia artículo 23A a María Rosa Gallo debido a que supera el tope de 30 días de licencia                       |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-05-08 | 2023-05-08 |    200 | Se otorga Licencia artículo 36A a Homero Manzi                                                                                 |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-05-11 | 2023-05-11 |    200 | Se otorga Licencia artículo 36A a Homero Manzi                                                                                 |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-05-20 | 2023-05-20 |    409 | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 2 días de licencia por mes                    |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-08-13 | 2023-08-14 |    200 | Se otorga Licencia artículo 36A a Homero Manzi                                                                                 |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-09-24 | 2023-09-25 |    200 | Se otorga Licencia artículo 36A a Homero Manzi                                                                                 |
      | 27707007009 | Homero      | Manzi        |      36A | ASUNTOS PARTICULARES          | 2023-11-04 | 2023-11-04 |    409 | NO se otorga Licencia artículo 36A a Homero Manzi debido a que supera el tope de 6 días de licencia por año                    |
      | 27504095009 | Raúl        | Gómez        |      36A | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 |    409 | NO se otorga Licencia artículo 36A a Raúl Gómez debido a que el agente no posee ningún cargo en la institución                 |
      | 20404004009 | Marisa      | Amuchástegui |      36A | ASUNTOS PARTICULARES          | 2023-03-04 | 2023-03-04 |    409 | NO se otorga Licencia artículo 36A a Marisa Amuchástegui debido a que el agente no tiene designación ese día en la institución |
      | 20202002009 | Susana      | Álvarez      |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-05-12 | 2023-05-30 |    200 | Se otorga Licencia artículo 5A a Susana Álvarez                                                                                |
      | 20200000009 | Rosalía     | Fernandez    |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-07-05 | 2023-09-15 |    409 | NO se otorga Licencia artículo 5A a Rosalía Fernandez debido a que supera el tope de 30 días de licencia                       |
      | 20202002009 | Susana      | Álvarez      |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-07-12 | 2023-08-30 |    409 | NO se otorga Licencia artículo 5A a Susana Álvarez debido a que supera el tope de 30 días de licencia                          |
      | 20200000009 | Rosalía     | Fernandez    |       5A | ENFERMEDAD DE CORTA EVOLUCIÓN | 2023-07-05 | 2023-07-15 |    200 | Se otorga Licencia artículo 5A a Rosalía Fernandez                                                                             |
      
  Escenario: 1 persona en instancias de designación de cargo que cubre una licencia de otra persona en la misma designación. Infomar que está correcto y que reemplaza al docente que solicitó licencia.
    Dada que existe la persona
        | CUIL          | Nombre    | Apellido     |
        | 20707007009   | Jorge     | Dismal       |
    Y que existen las siguientes instancias de designación asignada
        | TipoDesignacion | NombreTipoDesignacion   | CargaHoraria |
        | CARGO           | Preceptor/a             | 36           |
    Y que la instancia de designación está asignada a la persona con licencia "5A" comprendida en el período desde "2023-05-12" hasta "2023-05-30"
        | CUIL         | Nombre    | Apellido     | Desde        | Hasta        |
        | 20202002009  | Susana    | Álvarez      | 2020-03-01   | 2020-12-31   |
    Cuando se solicita el servicio de designación de la persona al cargo en el período comprendido desde "2023-05-17" hasta "2023-05-29"
    Entonces se recupera el mensaje
        """
        {
          "status": 200,
          "message": "Jorge Dismal ha sido designado/a como Preceptor/a exitosamente, en reemplazo de Susana Álvarez"
        }
        """


  Escenario: 1 persona en instancias de designación de cargo que cubre una licencia de otra persona en la misma designación, pero que no coincide el mismo período. Infomar el error respectivo y abortar la transacción.
    Dado que existe la persona
        | CUIL         | Nombre    | Apellido     |
        | 20898008009  | Analía    | Rojas        |
    Y que existen las siguientes instancias de designación asignada
        | TipoDesignacion | NombreTipoDesignacion | CargaHoraria |
        | CARGO           | Auxiliar ADM          | 30           |
    Y que la instancia de designación está asignada a la persona con licencia "5A" comprendida en el período desde "2023-07-05" hasta "2023-07-15"
        | CUIL         | Nombre    | Apellido     | Desde        | Hasta        |
        | 20200000009  | Rosalía   | Fernandez    | 2023-03-01   | 2023-12-31   |
    Cuando se solicita el servicio de designación de la persona al cargo en el período comprendido desde "2023-06-05" hasta "2023-07-15"
    Entonces se recupera el mensaje
        """
        {
          "status": 409,
          "message": "Analía Rojas NO ha sido designado/a como Auxiliar ADM, pues el cargo solicitado lo ocupa Rosalía Fernandez para el período"
        }
        """

  Esquema del escenario: eliminar licencias
    Dado que existe la licencia "<Artículo>" "<Desde>" "<Hasta>" "<CUIL>"
    Cuando solicito eliminar la licencia
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL        | Artículo | Desde      | Hasta      | status | respuesta                        |
      | 20808008009 |       5A | 2023-05-07 | 2023-05-17 |    200 | Licencia eliminada correctamente |
      | 20808008009 |       5A | 2023-05-18 | 2023-05-31 |    200 | Licencia eliminada correctamente |
      | 20808008009 |       5A | 2023-10-01 | 2023-10-03 |    200 | Licencia eliminada correctamente |
      | 27303003009 |      23A | 2023-04-01 | 2023-04-14 |    200 | Licencia eliminada correctamente |
      | 27707007009 |      36A | 2023-05-08 | 2023-05-08 |    200 | Licencia eliminada correctamente |
      | 27707007009 |      36A | 2023-05-11 | 2023-05-11 |    200 | Licencia eliminada correctamente |
      | 27707007009 |      36A | 2023-08-13 | 2023-08-14 |    200 | Licencia eliminada correctamente |
      | 27707007009 |      36A | 2023-09-24 | 2023-09-25 |    200 | Licencia eliminada correctamente |
      | 20202002009 |       5A | 2023-05-12 | 2023-05-30 |    200 | Licencia eliminada correctamente |
      | 20200000009 |       5A | 2023-07-05 | 2023-07-15 |    200 | Licencia eliminada correctamente |

  Esquema del escenario: eliminar designacion existente
    Dado que existe la designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>" "<fechaDesde>" "<fechaHasta>"
    Cuando solicito eliminar la designación
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | tipo              | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                           |
      | CARGO             | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | CARGO             | Portero/a         |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-03-01 | 2025-12-31 |    200 | Designación eliminada correctamente |
      | CARGO             | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Física            |   2 |      3 | Mañana | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Matemática        |   1 |      1 | Tarde  | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Mañana | 2023-03-01 | 2028-03-01 |    200 | Designación eliminada correctamente |
      | CARGO             | Preceptor/a       |     |        |        | 2023-05-17 | 2023-05-29 |    200 | Designación eliminada correctamente |
      
  Esquema del escenario: eliminar un cargo institucional
    Dado el cargo institucional cuyo "<nombre>" que da título al mismo
    Y que es del "<tipoDesignación>"
    Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
    Y que existe el espacio físico división con "<anio>" "<numero>"
    Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
    Cuando se presiona el botón de eliminar cargo institucional
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | nombre         | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | status | respuesta                     |
      | Preceptor/a    | CARGO             |           36 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Geografía      | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 |    200 | Cargo eliminado correctamente |
      | Auxiliar ADM   | CARGO             |           30 | 2020-03-01 |            |      |        |    200 | Cargo eliminado correctamente |
      | Física         | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 |    200 | Cargo eliminado correctamente |
      | Matemática     | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 |    200 | Cargo eliminado correctamente |
      | Tecnología     | ESPACIOCURRICULAR |            4 | 2020-03-01 | 2030-03-01 |    4 |      3 |    200 | Cargo eliminado correctamente |

  Esquema del escenario: eliminar división existente
    Dado que existe el espacio físico división con "<año>" "<número>"
    Cuando solicito eliminar la división "<año>" "<número>" "<orientación>" "<turno>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | año | número | orientación | turno  | status | respuesta                        |
      |   5 |      2 | Biológicas  | Mañana |    200 | División eliminada correctamente |
      |   3 |      1 | Sociales    | Tarde  |    200 | División eliminada correctamente |
      |   2 |      3 | Matemática  | Mañana |    200 | División eliminada correctamente |
      |   1 |      1 | Sociales    | Tarde  |    200 | División eliminada correctamente |
      |   4 |      3 | Informática | Mañana |    200 | División eliminada correctamente |

  Esquema del escenario: eliminar persona que existe
    Dado que existe la persona con "<CUIL>"
    Cuando solicito eliminar la persona con "<CUIL>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL        | status | respuesta                       |
      | 27303003009 |    200 | Persona eliminada correctamente |
      | 20404004009 |    200 | Persona eliminada correctamente |
      | 27505005009 |    200 | Persona eliminada correctamente |
      | 27707007009 |    200 | Persona eliminada correctamente |
      | 20200000009 |    200 | Persona eliminada correctamente |
      | 20808008009 |    200 | Persona eliminada correctamente |
      | 20202002009 |    200 | Persona eliminada correctamente |
      | 27504095009 |    200 | Persona eliminada correctamente |
      | 20707007009 |    200 | Persona eliminada correctamente |
      | 20898008009 |    200 | Persona eliminada correctamente |

