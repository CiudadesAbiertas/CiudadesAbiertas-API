DROP USER "CIUDADESABIERTAS" CASCADE;

CREATE USER ciudadesAbiertas 
IDENTIFIED BY ciudadesAbiertas;

GRANT UNLIMITED TABLESPACE TO ciudadesAbiertas;
GRANT RESOURCE TO ciudadesAbiertas;
GRANT CONNECT TO ciudadesAbiertas;
