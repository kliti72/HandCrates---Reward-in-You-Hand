@echo off
cd /d "C:\Users\kliti\Desktop\Project\__game\__hytale\Hytale-Plugin-Spawn"
call gradlew build
if errorlevel 1 (echo BUILD FAILED & exit /b 1)
scp "build\libs\AnimatedCrates.jar" root@57.128.252.159:/home/Hytale/Lobby/Server/mods/AnimatedCrates.jar
echo Deploy completato!