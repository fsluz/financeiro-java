@echo off
echo Compilando o projeto...
javac -cp "lib/*" app/*.java dao/*.java model/*.java db/*.java
if errorlevel 1 (
    echo Erro na compilacao!
    pause
    exit /b 1
)
echo Compilacao concluida!
echo Executando o programa...
java -cp ".;lib/jfreechart-1.0.19.jar;lib/jcommon-1.0.23.jar;lib/postgresql-42.7.3.jar" app.Main
pause 