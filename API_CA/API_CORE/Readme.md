
# API CIUDADES ABIERTAS - MÓDULO CORE

Esta es la documentación asociada al módulo **CORE**.


## DESCRIPCIÓN

Este módulo es el nucleo del API de Ciudades Abiertas.

Contiene todas las clases necesarias para que el API arranque y funcione correctamente.

Este módulo es una dependencia de todos los conjuntos de datos. Esto significa que todos los demás modulos ven todos los componentes de este módulo.

Por regla genera, este módulo no tiene acceso a las clases del resto de los módulos.

Exceptuando las clases que se utilizan en distintos conjuntos de datos, estas clases se encuentran incluidas dentro del core:

	- Equipamiento: que se utiliza en los módulos:
	
		- Aparcamientos
		- Equipamientos
		- Instalaciones deportivas
		- Puntos wifi
	
	- PuntoInteresTuristico: se utiliza en los módulos:

		- Monumentos
		- Puntos de interés turístico
		

# Autores
Juan Carlos Ballesteros (Localidata)
Carlos Martínez de la Casa (Localidata)
Oscar Corcho (UPM, Localidata)