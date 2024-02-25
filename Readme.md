
# Pictures Management

Este microservicio sera utilizado para la gestion de albunes y sus respectivas fotos.

Se utilizó sando Java 17, Spring Boot 3, JPA, H2, JUnit 5, Cucumber y Gherkins con sus respectivos plugings como Jacoco y lombok para la creación de un API con 3 Endpoints descritos en la seccion API Reference.
Ademas fue utilizado Domain Driven Design (DDD) para la separación de los modulos del micro servicio contando de la capa de applicacion, encargada de alojar lo referente a acceso por red al micro servicio, como los endpoints y los API contracts (Estos ultimos que por falta de tiempo no los realicé, pero se pudo iniciar aplicando API First tambien). Continuada por la capa Domain, encargada de contener todos los Model y logica de negocios.
Para finalizar con la capa de infraestructura encargada de contener los repositorios, las entities y los web client para conectar con otros microservicios.
A su vex, fue agregada una cuarta capa encargada de mantener separados los test de integracion del micro, esto con la finalidad de no agregar carga extra a los modulos, y para tener la posiblidad de testear cualquiera de los 3 modulos mencionados anteriormente, por separado si asi lo requiere.

Debo aclarar de que por falta de tiempo agregue 2 estructuras de datos con el sufijo "model" a la capa de infraestructura que idealmente deberian ir en la capa domain, pero el limite de tiempo no me permitió reparar ello.

Lo que encontrarán, lo hice lo mejor posible adaptandome a las exigencias tecnicas que entendi de la prueba y el limite de tiempo dicho por el reclutador.



## Running Tests

Los test unitarios se encuentran en la carpeta Test del proyecto, hay 3 que se encargan de probar de principio a fin la funcionalidad de cada endpoint solicitado en la prueba, los realice de esa manera para poder testear todo el recorrido desde la llamada a nuestro controller hasta la capa de infraestrutura con la persistencia y las llamadas a otros micros. En la revision realice otros 3 test mas pequeños para probar solamente una funcion especifica del servicio con cada test. En total son 6 tests:

1.- initilizeDataBase(): Se encarga de testear el proceso de enriquecer cada album con sus fotos.

2.- getAlbums(): Se encarga de testear el proceso de solicitar la informacion de un album a traves de una tercera API.

3.- getPhotoById(): Se encarga de testear el proceso de solicitar la informacion de una foto por ID, esta informacion se extrae de la BBDD.

4.- test_Web_Client_To_Get_Albums(): testear el clientService, especificamente la llamada que solicita los albums.

5.- test_Service_To_Get_Albums_From_An_API_Call(): Se encarga de testear la funcion de service que recupera la informacion de los album desde la API externa.

6.- test_Service_To_Get_Albums_With_Photos_From_DataBase(): Se encargade testear la funcion que recupera de la BBDD la informacion de los album enriquecidos con sus fotos.

El test de integración esta en la carpeta Test del proyecto, en dicho test, se ejecutan basicamante 2 de los test unitarios realizados arriba, es decir, primero de ejecutala funcion de inicializar la base de datos y luego la funcion de obtener una foto de acuerdo a un deteminado id. Fue elaborado con Gherkins y cucumber ya que a mi parecer puede ser muy util para los QA poder crear "Escenarios" de prueba que permitan seguir paso a paso la funcionalidad entera de un proceso que deba ejecutar nuestro microservicio.

To run tests, run the following command

```bash
  mvn test
```
# ![check-code-coverage](https://img.shields.io/badge/code--coverage-23%25-brightgreen)


## API Reference

#### Get all items and insert into data base

Se encarga de recibir una solicitud GET, llamar a una API externa y cargar cada Album con sus respectivas fotos en una base de datos H2, es decir, este se encarga de enriquecer cada album con sus fotos.

```http
  GET http://localhost:8080/initialize
```

#### Get album by ID
Se puede proceder a solicitar la información de los album por id, la cual se obtendra a traves de una llamada a una API externa.

```http
  GET http://localhost:8080/webclient/albums/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**. Id of album to fetch |

#### Get photo by ID from data base

Una vez que esta cargada la infomacion en la base de datos H2 (llamando al primer endpoint antes que éste), se puede proceder a solicitar la inforamcion de las fotos por id.

```http
  GET http://localhost:8080/local/storage/photo/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**. Id of photo to fetch |


#### Get album with their photos by ID from data base

Una vez que esta cargada la infomacion en la base de datos H2 (llamando al primer endpoint antes que éste), se puede proceder a solicitar la inforamcion de cada album por id.

```http
  GET http://localhost:8080/local/storage/albums/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `int` | **Required**. Id of photo to fetch |

#### Compilation

Para iniciar el proyecto ya no es necesario activar el perfil local, simplemente ejecutar el comando de abajo y arrancar desde el IDE es suficiente.


GIT Repository:

https://github.com/darkskull1297/pictures-manager


```bash
  mvn clean install
```
