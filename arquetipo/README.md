# Arquetipo PlantillaAulas

Plantilla Maven con JavaFX, inicio de sesión, registro de aulas e Hibernate.

Requisitos: JDK 26 y Maven.

## Instalar desde la carpeta principal de SistemaAulas

    mvn -f arquetipo/pom.xml clean install

## Generar otro proyecto

Desde una carpeta donde no exista NuevoSistemaAulas, ejecutar:

    mvn org.apache.maven.plugins:maven-archetype-plugin:3.4.1:generate -DarchetypeGroupId=edu.ufg -DarchetypeArtifactId=PlantillaAulas-archetype -DarchetypeVersion=1.0-SNAPSHOT -DarchetypeCatalog=local -DgroupId=edu.ufg -DartifactId=NuevoSistemaAulas -Dversion=1.0-SNAPSHOT -Dpackage=edu.ufg.plantillaaulas -DinteractiveMode=false

Mantener el paquete indicado para conservar las rutas FXML.
Preparar la base de datos y ajustar hibernate.cfg.xml siguiendo el README principal.
