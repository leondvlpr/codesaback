# CRUD BACK

## Base de datos

Para la creación de la base de datos, el proyecto utiliza la propiedad `spring.jpa.hibernate.ddl-auto=update` para instanciar las tablas cuando se inicie el proyecto. Solo es necesario configurar lo siguiente para que la base de datos se cree correctamente:

En el archivo application-dev.properties, modifique la etiqueta `spring.datasource.url=jdbc:postgresql://localhost:5432/crud`, reemplazando /crud por el nombre de la base de datos que se ha creado. Por ejemplo, `spring.datasource.url=jdbc:postgresql://localhost:5432/NOMBRE_BASE_DE_DATOS`.

## Funcionamiento

Este proyecto implementa las funciones básicas de un CRUD, permitiendo eliminar, editar, crear y listar facturas. Cada factura tiene una relación con productos, permitiendo visualizar correctamente los productos asociados a cada una. Para obtener más información sobre el flujo en la interfaz de usuario, puede consultar el repositorio del frontend.

GitHub frontend: https://github.com/leondvlpr/codesafront
