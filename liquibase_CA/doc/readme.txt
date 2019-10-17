El fichero de configuracion se encuentra en:
	src\main\resources\liquibase\liquibase.properties

Generar todo el esquema de BBDD
	mvn liquibase:update

Exportar estructuras de datos a partir de BBDD existente:
	mvn liquibase:generateChangeLog

Exportar datos a partir de BBDD existente:
	mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data

Rollback
	mvn liquibase:rollback -Dliquibase.rollbackTag=1.8


Documentación: http://shengwangi.blogspot.com/2016/04/liquibase-helloworld-example.html