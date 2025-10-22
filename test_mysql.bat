@echo off
echo Testing MySQL connection...
mysql -u root -e "SELECT VERSION();"
if %ERRORLEVEL% EQU 0 (
    echo MySQL connection successful!
) else (
    echo MySQL connection failed.
)
pause
