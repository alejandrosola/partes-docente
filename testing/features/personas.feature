# language: es
Característica: Gestión de personas
Módulo responsable de administrar a las personas del sistema
.
  Esquema del escenario: ingresar nuevas personas
    Dado que no existe la persona con "<CUIL>"
    Y la persona nueva con "<nombre>" "<apellido>" "<DNI>" "<CUIL>" "<sexo>" "<título>" "<domicilio>" "<teléfono>"
    Cuando se presiona el botón guardar persona
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | DNI      | nombre  | apellido     | CUIL        | sexo | título                  | domicilio    | teléfono           | status | respuesta                                                          |
      | 10100100 | Alberto | Lopez        | 27101001009 | M    | Profesor de Biología    | Charcas 54   | +54 (280) 411-1111 |    200 | Alberto Lopez con CUIL 27101001009 ingresado/a correctamente       |
      | 20200200 | Susana  | Álvarez      | 20202002009 | F    | Profesora de historia   | Mitre 154    | +54 (280) 422-2222 |    200 | Susana Álvarez con CUIL 20202002009 ingresado/a correctamente      |
      | 30300300 | Pedro   | Benítez      | 27303003009 | M    |                         | Jujuy 255    | +54 (280) 433-3333 |    200 | Pedro Benítez con CUIL 27303003009 ingresado/a correctamente       |
      | 40400400 | Marisa  | Amuchástegui | 20404004009 | F    | Profesora de historia   | Zar 555      | +54 (280) 444-4444 |    200 | Marisa Amuchástegui con CUIL 20404004009 ingresado/a correctamente |
      | 50500500 | Raúl    | Gómez        | 27505005009 | M    | Profesor de Geografía   | Roca 2458    | +54 (280) 455-5555 |    200 | Raúl Gómez con CUIL 27505005009 ingresado/a correctamente          |
      | 60600600 | Inés    | Torres       | 20606006009 | F    | Licenciada en Geografía | La Pampa 322 | +54 (280) 466-6666 |    200 | Inés Torres con CUIL 20606006009 ingresado/a correctamente         |
      | 70700700 | Jorge   | Dismal       | 27707007009 | M    |                         | Mitre 1855   | +54 (280) 477-7777 |    200 | Jorge Dismal con CUIL 27707007009 ingresado/a correctamente        |
      | 20000000 | Rosalía | Fernandez    | 20200000009 | F    | Maestra de grado        | Maiz 356     | +54 (280) 420-0000 |    200 | Rosalía Fernandez con CUIL 20200000009 ingresado/a correctamente   |
      | 80800800 | Analía  | Rojas        | 20808008009 | F    | Técnica superior        | Rosa 556     | +54 (280) 488-8888 |    200 | Analía Rojas con CUIL 20808008009 ingresado/a correctamente        |

  Esquema del escenario: editar nombre de persona
    Dado que existe la persona con "<CUIL>"
    Cuando se cambia el nombre a "<nuevo_nombre>"
    Entonces se espera el siguiente <status> con la "<respuesta>"
    Y la persona con "<nombre>" "<apellido>" "<DNI>" "<CUIL>" "<sexo>" "<título>" "<domicilio>" "<teléfono>"

    Ejemplos: 
      | DNI      | nombre  | nuevo_nombre | apellido     | CUIL        | sexo | título                  | domicilio    | teléfono           | status | respuesta                                                            |
      | 10100100 | Alberto | alberto      | Lopez        | 27101001009 | M    | Profesor de Biología    | Charcas 54   | +54 (280) 411-1111 |    200 | Alberto Lopez con CUIL 27101001009 actualizado/a correctamente       |
      | 20200200 | Susana  | susana       | Álvarez      | 20202002009 | F    | Profesora de historia   | Mitre 154    | +54 (280) 422-2222 |    200 | Susana Álvarez con CUIL 20202002009 actualizado/a correctamente      |
      | 30300300 | Pedro   | pedro        | Benítez      | 27303003009 | M    |                         | Jujuy 255    | +54 (280) 433-3333 |    200 | Pedro Benítez con CUIL 27303003009 actualizado/a correctamente       |
      | 40400400 | Marisa  | marisa       | Amuchástegui | 20404004009 | F    | Profesora de historia   | Zar 555      | +54 (280) 444-4444 |    200 | Marisa Amuchástegui con CUIL 20404004009 actualizado/a correctamente |
      | 50500500 | Raúl    | raúl         | Gómez        | 27505005009 | M    | Profesor de Geografía   | Roca 2458    | +54 (280) 455-5555 |    200 | Raúl Gómez con CUIL 27505005009 actualizado/a correctamente          |
      | 60600600 | Inés    | inés         | Torres       | 20606006009 | F    | Licenciada en Geografía | La Pampa 322 | +54 (280) 466-6666 |    200 | Inés Torres con CUIL 20606006009 actualizado/a correctamente         |
      | 70700700 | Jorge   | jorge        | Dismal       | 27707007009 | M    |                         | Mitre 1855   | +54 (280) 477-7777 |    200 | Jorge Dismal con CUIL 27707007009 actualizado/a correctamente        |
      | 20000000 | Rosalía | rosalía      | Fernandez    | 20200000009 | F    | Maestra de grado        | Maiz 356     | +54 (280) 420-0000 |    200 | Rosalía Fernandez con CUIL 20200000009 actualizado/a correctamente   |
      | 80800800 | Analía  | analía       | Rojas        | 20808008009 | F    | Técnica superior        | Rosa 556     | +54 (280) 488-8888 |    200 | Analía Rojas con CUIL 20808008009 actualizado/a correctamente        |

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
      | 20606006009 |    200 | Persona eliminada correctamente |
      | 27707007009 |    200 | Persona eliminada correctamente |
      | 20200000009 |    200 | Persona eliminada correctamente |
      | 20808008009 |    200 | Persona eliminada correctamente |

  Esquema del escenario: eliminar persona que no existe
    Dado que no existe la persona con "<CUIL>"
    Cuando solicito eliminar la persona con "<CUIL>"
    Entonces se espera el siguiente <status> con la "<respuesta>"

    Ejemplos: 
      | CUIL   | status | respuesta                            |
      | 123123 |    404 | La persona con CUIL 123123 no existe |
      | 555555 |    404 | La persona con CUIL 555555 no existe |
      | 898989 |    404 | La persona con CUIL 898989 no existe |
      | 123456 |    404 | La persona con CUIL 123456 no existe |
