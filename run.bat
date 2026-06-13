@echo off

echo Compiling the Project...
call mvnw.cmd clean install

if errorlevel 1 (
    echo Compilation Error
    exit /b 1
)

echo Booting up the simulation...
call mvnw.cmd javafx:run
