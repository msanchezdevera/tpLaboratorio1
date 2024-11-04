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

## Instalación y Ejecución

### Prerrequisitos
- **Java 20**: Asegúrate de tener Java 20 instalado y configurado en tu sistema.
- **Git**: Para clonar el repositorio.

### Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/msanchezdevera/tpLaboratorio1.git
   cd tpLaboratorio1
   ```
2. Importar el proyecto en un IDE (como Eclipse).
3. Añadir el archivo JAR de H2 al classpath del proyecto.

### Ejecución


## Uso del Proyecto

- La aplicación permite crear diferentes tipos de cuentas bancarias: Caja de Ahorro, Cuenta Corriente, Caja de Ahorro en Dólares y Fondo Fima.

## Tecnologías Utilizadas

- **Java 20**
- **Base de Datos H2**
- **Swing**

## Autor

- **Mariano Sergio Sanchez de Vera Bastone**
