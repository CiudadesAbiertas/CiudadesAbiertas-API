@ECHO OFF
cls
echo "                                    |
echo "                                   / \
echo " _____        _____     __________/ o \/\_________      _________
echo "|o o o|_______|    |___|               | | # # #  |____|o o o o  |  /|\
echo "|o o o|  * * *|: ::|. .|               |o| # # #  |. . |o o o o  | //|\\
echo "|o o o|* * *  |::  |. .| []  []  []  []|o| # # #  |. . |o o o o  | ((|))
echo "|o o o|**  ** |:  :|. .| []  []  []    |o| # # #  |. . |o o o o  | ((|))
echo "|_[]__|__[]___|_||_|__<|____________;;_|_|___/\___|_.|_|____[]___|   |

D:
cd D:\git\CiudadesAbiertas\liquibase_CA
call mvn install
rem actualizar bbdd destino
call mvn liquibase:update
rem exportar estructuras de origen
rem call mvn liquibase:generateChangeLog
rem datos de origen
rem call mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
PAUSE
