@echo off
setlocal EnableDelayedExpansion

set SERVER_USER=root
set SERVER_IP=57.128.252.159

echo Start server...
ssh %SERVER_USER%@%SERVER_IP% "cd /home/Hytale/Lobby/ && ./start.sh"
