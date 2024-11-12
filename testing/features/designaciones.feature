# language: es
Característica: designar una persona a un cargo docente
   actividad central de información de la escuela secundaria

  Esquema del escenario: ingresar nuevas personas
    Dado que no existe la persona con "<CUIL>"
    Y la persona nueva con "<nombre>" "<apellido>" "<DNI>" "<CUIL>" "<sexo>" "<título>" "<domicilio>" "<teléfono>"
    Cuando se presiona el botón guardar persona
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | DNI      | nombre      | apellido     | CUIL        | sexo | título                | domicilio  | teléfono           | status | respuesta                                                          |
      | 10100100 | Alberto     | Lopez        | 27101001009 | M    | Profesor de Biología  | Charcas 54 | +54 (280) 411-1111 |    200 | Alberto Lopez con CUIL 27101001009 ingresado/a correctamente       |
      | 20200200 | Susana      | Álvarez      | 20202002009 | F    | Profesora de historia | Mitre 154  | +54 (280) 422-2222 |    200 | Susana Álvarez con CUIL 20202002009 ingresado/a correctamente      |
      | 30300300 | María Rosa  | Gallo        | 27303003009 | M    |                       | Jujuy 255  | +54 (280) 433-3333 |    200 | María Rosa Gallo con CUIL 27303003009 ingresado/a correctamente    |
      | 40400400 | Marisa      | Amuchástegui | 20404004009 | F    | Profesora de historia | Zar 555    | +54 (280) 444-4444 |    200 | Marisa Amuchástegui con CUIL 20404004009 ingresado/a correctamente |
      | 50500500 | Raúl        | Gómez        | 27505005009 | M    | Profesor de Geografía | Roca 2458  | +54 (280) 455-5555 |    200 | Raúl Gómez con CUIL 27505005009 ingresado/a correctamente          |
      | 70700700 | Homero      | Manzi        | 27707007009 | M    |                       | Mitre 1855 | +54 (280) 477-7777 |    200 | Homero Manzi con CUIL 27707007009 ingresado/a correctamente        |
      | 20000000 | Rosalía     | Fernandez    | 20200000009 | F    | Maestra de grado      | Maiz 356   | +54 (280) 420-0000 |    200 | Rosalía Fernandez con CUIL 20200000009 ingresado/a correctamente   |
      | 80800800 | Ermenegildo | Sábat        | 20808008009 | F    | Técnica superior      | Rosa 556   | +54 (280) 488-8888 |    200 | Ermenegildo Sábat con CUIL 20808008009 ingresado/a correctamente   |

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
      | nombre         | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno  | status | respuesta                                                                    |
      | Historia       | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    5 |      2 | Mañana |    200 | Espacio Curricular Historia para la división 5° 2° ingresado correctamente   |
      | Vicedirector/a | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo de Vicedirector/a ingresado correctamente                              |
      | Preceptor/a    | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo de Preceptor/a ingresado correctamente                                 |
      | Geografía      | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 | Tarde  |    200 | Espacio Curricular Geografía para la división 3° 1° ingresado correctamente  |
      | Auxiliar ADM   | CARGO             |           30 | 2020-03-01 |            |      |        |        |    200 | Cargo de Auxiliar ADM ingresado correctamente                                |
      | Física         | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 | Mañana |    200 | Espacio Curricular Física para la división 2° 3° ingresado correctamente     |
      | Matemática     | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 | Tarde  |    200 | Espacio Curricular Matemática para la división 1° 1° ingresado correctamente |
      | Tecnología     | ESPACIOCURRICULAR |            4 | 2020-03-01 | 2030-03-01 |    4 |      3 | Mañana |    200 | Espacio Curricular Tecnología para la división 4° 3° ingresado correctamente |

  Esquema del escenario: Designación de persona en cargos NO cubiertos aún en el período indicado de forma existosa
    Dada la persona con "<CUIL>" "<nombre>" y "<apellido>"
    Y que se asigna al cargo con tipo de designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>"
    Y se designa por el período "<fechaDesde>" "<fechaHasta>"
    Y que su situación revista es "<situacionRevista>"
    Cuando se presiona el botón guardar designacion
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | situacionRevista | CUIL        | nombre      | apellido     | tipo              | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                                                                                         |
      | Titular          | 27101001009 | Alberto     | Lopez        | CARGO             | Vicedirector/a    |     |        |        | 2023-03-01 |            |    200 | Alberto Lopez ha sido designado/a como Vicedirector/a exitosamente                                |
      | Titular          | 20202002009 | Susana      | Álvarez      | CARGO             | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Susana Álvarez ha sido designado/a como Preceptor/a exitosamente                                  |
      | Titular          | 20404004009 | Marisa      | Amuchástegui | ESPACIOCURRICULAR | Historia          |   5 |      2 | Mañana | 2023-03-01 |            |    200 | Marisa Amuchástegui ha sido designado/a a la asignatura Historia a la división 5° 2° exitosamente |
      | Titular          | 27505005009 | Raúl        | Gómez        | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-03-01 | 2025-12-31 |    200 | Raúl Gómez ha sido designado/a a la asignatura Geografía a la división 3° 1° exitosamente         |
      | Titular          | 20200000009 | Rosalía     | Fernandez    | CARGO             | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Rosalía Fernandez ha sido designado/a como Auxiliar ADM exitosamente                              |
      | Titular          | 20808008009 | Ermenegildo | Sábat        | ESPACIOCURRICULAR | Física            |   2 |      3 | Mañana | 2023-03-01 |            |    200 | Ermenegildo Sábat ha sido designado/a a la asignatura Física a la división 2° 3° exitosamente     |
      | Titular          | 27303003009 | María Rosa  | Gallo        | ESPACIOCURRICULAR | Matemática        |   1 |      1 | Tarde  | 2023-03-01 |            |    200 | María Rosa Gallo ha sido designado/a a la asignatura Matemática a la división 1° 1° exitosamente  |
      | Titular          | 27707007009 | Homero      | Manzi        | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Mañana | 2023-03-01 | 2028-03-01 |    200 | Homero Manzi ha sido designado/a a la asignatura Tecnología a la división 4° 3° exitosamente      |
      | Titular          | 27303003009 | María Rosa  | Gallo        | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2027-03-01 | 2028-12-31 |    200 | María Rosa Gallo ha sido designado/a a la asignatura Geografía a la división 3° 1° exitosamente   |

  Esquema del escenario: Designación de persona en una instancia de designación de cargo que YA cuenta con una designación para el mismo período. Informar el error respectivo y abortar la transacción
    Dada la persona con "<CUIL>" "<nombre>" y "<apellido>"
    Y que se asigna al cargo con tipo de designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>"
    Y se designa por el período "<fechaDesde>" "<fechaHasta>"
    Y que su situación revista es "<situacionRevista>"
    Cuando se presiona el botón guardar designacion
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | situacionRevista | CUIL        | nombre      | apellido  | tipo              | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                                                                                                                                            |
      | Titular          | 20200000009 | Rosalía     | Fernandez | CARGO             | Preceptor/a       |     |        |        | 2023-05-01 | 2024-12-31 |    409 | Rosalía Fernandez NO ha sido designado/a como Preceptor/a, pues el cargo solicitado lo ocupa Susana Álvarez para el período                          |
      | Titular          | 27707007009 | Homero      | Manzi     | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-07-01 | 2023-10-15 |    409 | Homero Manzi NO ha sido designado/a debido a que la asignatura Geografía de la división 3° 1° turno Tarde lo ocupa Raúl Gómez para el período        |
      | Titular          | 27505005009 | Raúl        | Gómez     | CARGO             | Historia          |   5 |      2 | Mañana | 2023-03-08 |            |    409 | Raúl Gómez NO ha sido designado/a debido a que la asignatura Historia de la división 5° 2° turno Mañana lo ocupa Marisa Amuchástegui para el período |
      | Titular          | 20808008009 | Ermenegildo | Sábat     | CARGO             | Vicedirector/a    |     |        |        | 2023-02-20 | 2023-03-05 |    409 | Ermenegildo Sábat NO ha sido designado/a como Vicedirector/a, pues el cargo solicitado lo ocupa Alberto Lopez para el período                        |
      | Titular          | 20808008009 | Ermenegildo | Sábat     | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-02-20 | 2027-04-05 |    409 | Ermenegildo Sábat NO ha sido designado/a debido a que la asignatura Geografía de la división 3° 1° turno Tarde lo ocupa María Rosa Gallo para el período   |

  Esquema del escenario: Designación de persona en un período no cubierto por el cargo
    Dada la persona con "<CUIL>" "<nombre>" y "<apellido>"
    Y que se asigna al cargo con tipo de designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>"
    Y se designa por el período "<fechaDesde>" "<fechaHasta>"
    Y que su situación revista es "<situacionRevista>"
    Cuando se presiona el botón guardar designacion
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | situacionRevista | CUIL        | nombre  | apellido  | tipo              | nombreDesignación | año | número | turno | fechaDesde | fechaHasta | status | respuesta                                                                        |
      | Titular          | 20200000009 | Rosalía | Fernandez | CARGO             | Preceptor/a       |     |        |       | 1991-05-01 | 2024-12-31 |    409 | El cargo Preceptor/a no está disponible para el período                          |
      | Titular          | 27707007009 | Homero  | Manzi     | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Tarde | 1513-07-01 |            |    409 | La asginatura Tecnología de la división 4° 3° no está disponible para el período |
      | Titular          | 27707007009 | Homero  | Manzi     | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Tarde | 2021-07-01 |            |    409 | La asginatura Tecnología de la división 4° 3° no está disponible para el período |
      | Titular          | 27707007009 | Homero  | Manzi     | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Tarde | 2019-07-01 | 2031-07-01 |    409 | La asginatura Tecnología de la división 4° 3° no está disponible para el período |
      | Titular          | 20200000009 | Rosalía | Fernandez | CARGO             | Preceptor/a       |     |        |       | 2019-05-01 |            |    409 | El cargo Preceptor/a no está disponible para el período                          |

  Esquema del escenario: eliminar designacion existente
    Dado que existe la designación "<tipo>" "<nombreDesignación>" y división con "<año>" "<número>" "<fechaDesde>" "<fechaHasta>"
    Cuando solicito eliminar la designación
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | tipo              | nombreDesignación | año | número | turno  | fechaDesde | fechaHasta | status | respuesta                           |
      | CARGO             | Vicedirector/a    |     |        |        | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | CARGO             | Preceptor/a       |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Historia          |   5 |      2 | Mañana | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Geografía         |   3 |      1 | Tarde  | 2023-03-01 | 2025-12-31 |    200 | Designación eliminada correctamente |
      | CARGO             | Auxiliar ADM      |     |        |        | 2023-03-01 | 2023-12-31 |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Física            |   2 |      3 | Mañana | 2023-03-01 |            |    200 | Designación eliminada correctamente |
      | ESPACIOCURRICULAR | Tecnología        |   4 |      3 | Mañana | 2023-03-01 | 2028-03-01 |    200 | Designación eliminada correctamente |

  Esquema del escenario: eliminar un cargo institucional
    Dado el cargo institucional cuyo "<nombre>" que da título al mismo
    Y que es del "<tipoDesignación>"
    Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
    Y que existe el espacio físico división con "<anio>" "<numero>"
    Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
    Cuando se presiona el botón de eliminar cargo institucional
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | nombre         | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno  | status | respuesta                     |
      | Historia       | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    5 |      2 | Mañana |    200 | Cargo eliminado correctamente |
      | Vicedirector/a | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo eliminado correctamente |
      | Preceptor/a    | CARGO             |           36 | 2020-03-01 |            |      |        |        |    200 | Cargo eliminado correctamente |
      | Geografía      | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 | Tarde  |    200 | Cargo eliminado correctamente |
      | Auxiliar ADM   | CARGO             |           30 | 2020-03-01 |            |      |        |        |    200 | Cargo eliminado correctamente |
      | Física         | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 | Mañana |    200 | Cargo eliminado correctamente |
      | Matemática     | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 | Tarde  |    200 | Cargo eliminado correctamente |
      | Tecnología     | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    4 |      3 | Mañana |    200 | Cargo eliminado correctamente |

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
      | 27101001009 |    200 | Persona eliminada correctamente |
      | 20202002009 |    200 | Persona eliminada correctamente |
      | 27303003009 |    200 | Persona eliminada correctamente |
      | 20404004009 |    200 | Persona eliminada correctamente |
      | 27505005009 |    200 | Persona eliminada correctamente |
      | 27707007009 |    200 | Persona eliminada correctamente |
      | 20200000009 |    200 | Persona eliminada correctamente |
      | 20808008009 |    200 | Persona eliminada correctamente |
