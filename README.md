## Name
BCI-Exercise

## Description
Ejercicio de Springboot/Gradle para el banco BCI.

## Installation
Para correr el proyecto en nuestra maquina local se debe cumplir con los siguientes requerimientos:

- Java8
- Gradle
- Intellij IDEA
- Plugin Lombok instalado en nuestro IDE. En las nuevas versiones ya viene instalado por defecto.

Una vez que clonamos el proyecto, lo importamos desde nuestro IDE, el cual
nos va a solicitar que seleccionemos un SKD, seleccionamos Java8.
Luego, nos dirigimos a nuestro archivo build.gradle y hacemos un refresh para que
baje todas las dependencias. Este proceso puede demorarse unos minutos.
Durante el proceso, el plugin de lombok puede solicitarnos algunos permisos, le damos
aceptar, esto le permite a Lombok inspeccionar nuestro codigo.
Por ultimo, nos paramos sobre la clase Main y le damos Run. Por defecto, nuestra aplicacion
va a correr en el puerto 8080.
Finalmente, deberiamos poder acceder a los siguientes endpoints:

- localhost:8080/sign-up
- localhost:8080/login