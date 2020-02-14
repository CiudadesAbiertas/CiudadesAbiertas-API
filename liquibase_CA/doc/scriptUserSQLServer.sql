USE master
GO
DROP LOGIN ciudadesAbiertas;
DROP DATABASE ciudadesAbiertas;
CREATE DATABASE ciudadesAbiertas COLLATE SQL_Latin1_General_CP1_CI_AS;
CREATE LOGIN ciudadesAbiertas WITH PASSWORD='Primera1';


USE ciudadesAbiertas
GO
CREATE USER ciudadesAbiertas FOR LOGIN ciudadesAbiertas WITH DEFAULT_SCHEMA=dbo;
ALTER LOGIN ciudadesAbiertas WITH DEFAULT_DATABASE = ciudadesAbiertas;

GO
EXEC sp_addrolemember 'db_ddladmin', 'ciudadesAbiertas';
EXEC sp_addrolemember 'db_datareader', 'ciudadesAbiertas';
EXEC sp_addrolemember 'db_datawriter', 'ciudadesAbiertas';
grant execute on dbo.Translate to ciudadesAbiertas;

--USE ciudadesAbiertas
--GO
--CREATE SCHEMA apiDinamica AUTHORIZATION ciudadesAbiertas;