# language: es
Característica: Gestión de cargos
Módulo responsable de administrar a los cargos de una escuela

    Esquema del escenario: ingresar división no existente
        Dado que no existe el espacio físico división con "<año>" "<número>"
        Cuando solicito guardar la división "<año>" "<número>" "<orientación>" "<turno>"
        Entonces se espera el siguiente <status> con la "<respuesta>"

        Ejemplos: 
        | año | número | orientación | turno   | status | respuesta                              |
        |   5 |      2 | Biológicas  | Mañana |    200 | División 5° 2° ingresada correctamente |
        |   3 |      1 | Sociales    | Tarde   |    200 | División 3° 1° ingresada correctamente |
        |   2 |      3 | Matemática  | Mañana |    200 | División 2° 3° ingresada correctamente |
        |   1 |      1 | Sociales    | Tarde   |    200 | División 1° 1° ingresada correctamente |
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
        | nombre         | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno   | status | respuesta                                                                    |
        | Historia       | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    5 |      2 | Mañana |    200 | Espacio Curricular Historia para la división 5° 2° ingresado correctamente   |
        | Vicedirector/a | CARGO             |           36 | 2020-03-01 |            |      |        |         |    200 | Cargo de Vicedirector/a ingresado correctamente                              |
        | Preceptor/a    | CARGO             |           36 | 2020-03-01 |            |      |        |         |    200 | Cargo de Preceptor/a ingresado correctamente                                 |
        | Geografía      | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 | Tarde   |    200 | Espacio Curricular Geografía para la división 3° 1° ingresado correctamente  |
        | Auxiliar ADM   | CARGO             |           30 | 2020-03-01 |            |      |        |         |    200 | Cargo de Auxiliar ADM ingresado correctamente                                |
        | Física         | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 | Mañana |    200 | Espacio Curricular Física para la división 2° 3° ingresado correctamente     |
        | Matemática     | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 | Tarde   |    200 | Espacio Curricular Matemática para la división 1° 1° ingresado correctamente |
        | Tecnología     | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    4 |      3 | Mañana |    200 | Espacio Curricular Tecnología para la división 4° 3° ingresado correctamente |

    Esquema del escenario: eliminar un cargo institucional
        Dado el cargo institucional cuyo "<nombre>" que da título al mismo
        Y que es del "<tipoDesignación>"
        Y que tiene una <cargaHoraria> con la vigencia "<fechaDesde>" "<fechaHasta>"
        Y que existe el espacio físico división con "<anio>" "<numero>"
        Y que si el tipo es espacio curricular, opcionalmente se asigna a la división "<anio>" "<numero>"
        Cuando se presiona el botón de eliminar cargo institucional
        Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
        | nombre         | tipoDesignación   | cargaHoraria | fechaDesde | fechaHasta | anio | numero | turno   | status | respuesta                     |
        | Historia       | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    5 |      2 | Mañana |    200 | Cargo eliminado correctamente |
        | Vicedirector/a | CARGO             |           36 | 2020-03-01 |            |      |        |         |    200 | Cargo eliminado correctamente |
        | Preceptor/a    | CARGO             |           36 | 2020-03-01 |            |      |        |         |    200 | Cargo eliminado correctamente |
        | Geografía      | ESPACIOCURRICULAR |            3 | 2020-03-01 |            |    3 |      1 | Tarde   |    200 | Cargo eliminado correctamente |
        | Auxiliar ADM   | CARGO             |           30 | 2020-03-01 |            |      |        |         |    200 | Cargo eliminado correctamente |
        | Física         | ESPACIOCURRICULAR |            5 | 2020-03-01 |            |    2 |      3 | Mañana |    200 | Cargo eliminado correctamente |
        | Matemática     | ESPACIOCURRICULAR |            6 | 2020-03-01 |            |    1 |      1 | Tarde   |    200 | Cargo eliminado correctamente |
        | Tecnología     | ESPACIOCURRICULAR |            4 | 2020-03-01 |            |    4 |      3 | Mañana |    200 | Cargo eliminado correctamente |

  Esquema del escenario: eliminar división existente
    Dado que existe el espacio físico división con "<año>" "<número>"
    Cuando solicito eliminar la división "<año>" "<número>" "<orientación>" "<turno>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | año | número | orientación | turno   | status | respuesta                        |
      |   5 |      2 | Biológicas  | Mañana |    200 | División eliminada correctamente |
      |   3 |      1 | Sociales    | Tarde   |    200 | División eliminada correctamente |
      |   2 |      3 | Matemática  | Mañana |    200 | División eliminada correctamente |
      |   1 |      1 | Sociales    | Tarde   |    200 | División eliminada correctamente |
      |   4 |      3 | Informática | Mañana |    200 | División eliminada correctamente |