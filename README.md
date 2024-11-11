# Mini Home Banking - Trabajo Práctico Integrador

Este es un proyecto de ejemplo desarrollado como Trabajo Práctico Integrador para la materia de Laboratorio I. El proyecto consiste en una aplicación mini home banking que permite gestionar cuentas bancarias.

## Características

- Interfaz gráfica con `Swing` para la visualización y edición de datos.
- Base de datos H2 para almacenamiento de datos.

## Estructura del Proyecto

El proyecto se estructura en 3 capas:

1. **Capa de Datos**: Se encarga de la persistencia, con clases como `ConnectionManager`, `QueryExecutor`, y DAOs para las diferentes entidades.
2. **Capa de Negocio**: Contiene la lógica de negocio.
3. **Capa de Presentación**: Implementada con `Swing`, permite a los usuarios interactuar con el sistema mediante una interfaz gráfica.

## Directorio de Pruebas

El proyecto contiene un directorio llamado `test` con pruebas de las diferentes capas del sistema.


## Instalación y Ejecución

### Prerrequisitos
- **Java 20**: Asegúrate de tener Java 20 instalado y configurado en tu sistema.
- **Git**: Para clonar el repositorio.

### Instalación
1. Importar el proyecto en un IDE (como Eclipse).
2. Añadir el archivo JAR de H2 al classpath del proyecto.

###Ejecución

1. Compilar el proyecto en el IDE.
2. Ejecutar la clase principal HomeBankingApp ubicada en el paquete app.

La aplicación va a abrir una ventana gráfica.

Aclaracion: la primera vez que se corre el proyecto no va a haber datos. Para esto se creo la clase CrearCuentasBancarias, la cual genera varias cuentas bancarias. Se puede correr este main al principio y luego la aplicacion, para poder tener datos iniciales.


## Uso del Proyecto

- La aplicación permite crear diferentes tipos de cuentas bancarias: Caja de Ahorro, Cuenta Corriente, Caja de Ahorro en Dólares y Fondo Fima.

## Tecnologías Utilizadas

- **Java 20**
- **Base de Datos H2**
- **Swing**

## Autor

- **Mariano Sergio Sanchez de Vera Bastone**
