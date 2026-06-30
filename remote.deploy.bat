@echo off
setlocal EnableDelayedExpansion

:: === CONFIGURA QUI ===
set PLUGIN_DIR=C:\Users\kliti\Desktop\Project\__game\__hytale\Hytale-Plugin-Crates
set BUILD_CMD=gradlew build
set JAR_NAME=AnimatedCrates.jar
set BUILD_OUTPUT=%PLUGIN_DIR%\build\libs\%JAR_NAME%
set SERVER_USER=root
set SERVER_IP=57.128.252.159
set REMOTE_DIR=/home/Hytale/Lobby/Server/mods

:: === SCRIPT ===
cd /d "%PLUGIN_DIR%"

echo Building plugin...
call %BUILD_CMD%

if %ERRORLEVEL% neq 0 (
    echo Build fallito!
    pause
    exit /b 1
)

if not exist "%BUILD_OUTPUT%" (
    echo JAR non trovato: %BUILD_OUTPUT%
    pause
    exit /b 1
)

echo Deploy JAR...
scp "%BUILD_OUTPUT%" %SERVER_USER%@%SERVER_IP%:%REMOTE_DIR%/%JAR_NAME%

if %ERRORLEVEL% neq 0 (
    echo SCP fallito!
    pause
    exit /b 1
)

echo Start server...
ssh %SERVER_USER%@%SERVER_IP% "cd /home/Hytale/Lobby/ && ./start.sh"

echo Deploy completato!
pause