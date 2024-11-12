# language: es
Característica: Gestión de divisiones
Módulo responsable de administrar a las divisiones (espacios físicos) de una escuela

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

  Esquema del escenario: editar división existente
    Dado que existe el espacio físico división con "<año>" "<número>"
    Cuando solicito editar la orientación de la división "<año>" "<número>" a "<nueva_orientación>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | año | número | orientación | nueva_orientación | turno   | status | respuesta                                |
      |   5 |      2 | Biológica   | Matemáticas      | Mañana |    200 | División 5° 2° actualizada correctamente |

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
