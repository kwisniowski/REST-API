call runcrud
if "%ERRORLEVEL%" == "0" goto runchrome
echo.
echo There was an error running runcrud.bat
goto end

:runchrome
echo Waiting for Tomcat server...
timeout /t 30
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were some errors. Finishing script

:end
echo.
echo Script finished