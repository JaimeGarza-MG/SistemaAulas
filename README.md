# Sistema de aulas

Laboratorio 3 de Programación Orientada a Objetos y Estructuras de Datos — UFG.

Autor: Jaime Garza.

Aplicación de escritorio para iniciar sesión y registrar aulas. El proyecto fue generado desde el arquetipo personalizado edu.ufg:PlantillaAulas-archetype:1.0-SNAPSHOT.

## Herramientas

- JDK 26 y Maven.
- Apache NetBeans.
- JavaFX 26 y FXML.
- Hibernate ORM y MariaDB.

## Preparar la base de datos

1. Iniciar MySQL/MariaDB desde XAMPP.
2. En phpMyAdmin, crear una base vacía llamada laboratorio_aulas con cotejamiento utf8mb4_unicode_ci.
3. Seleccionar esa base e importar database/laboratorio_aulas.sql.
4. Revisar src/main/resources/hibernate.cfg.xml y ajustar puerto, usuario y contraseña según el equipo.

La configuración de desarrollo utiliza localhost, puerto 2807, usuario root y contraseña vacía.

## Ejecutar

Abrir la carpeta del proyecto en NetBeans y seleccionar Run.

También puede ejecutarse desde la carpeta del proyecto con JDK 26 y Maven configurados:

    mvn clean javafx:run

## Usuario de demostración

- Correo: admin@ufg.edu.sv
- Clave: Aulas2026!

Las contraseñas se almacenan mediante PBKDF2 con sal aleatoria.

## Funciones

- Inicio de sesión validado contra la base de datos.
- Registro de aulas mediante Hibernate.
- Planta, tipo y estado cargados desde la base de datos.
- Validación de campos obligatorios y números enteros positivos.
- Prevención de aulas con el mismo nombre en el mismo edificio.
- Limpieza del formulario y cierre de sesión.

## Organización

- entidades: clases que representan los datos.
- controladores: eventos y validaciones de las pantallas.
- dao: consultas y operaciones de persistencia.
- util: conexión Hibernate y procesamiento de contraseñas.
- resources: pantallas FXML y configuración.

## Pruebas manuales realizadas

Acceso correcto e incorrecto, carga de catálogos, guardado de aula, rechazo de duplicados, campos vacíos, capacidad cero y texto en capacidad, limpieza y cierre de sesión.
