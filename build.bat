@echo off
REM BindSMP Build Script for Windows
REM This script will compile your plugin JAR locally without needing Maven installed

echo Downloading Maven...
powershell -Command "(New-Object System.Net.ServicePointManager).SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://raw.githubusercontent.com/goatedonyx274-web/BindSMP/main/build-local.ps1'))"

pause