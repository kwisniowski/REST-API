call gradlew build
if "%ERRORLEVEL%" == "0" goto rename
echo.
echo GRADLEW BUILD HAS ERRORS - braking work
goto fail

:rename
del build\libs\crud.war
ren build\libs\tasks-0.0.1-SNAPSHOT.war crud.war
if "%ERRORLEVEL%" == "0" goto stoptomcat
echo Can not rename file
goto fail

:stoptomcat
call %CATALINA_HOME%\bin\shutdown.bat

:copyfile
copy build\libs\crud.war %CATALINA_HOME%\webapps
if "%ERRORLEVEL%" == "0" goto runtomcat
echo Cannot copy file
goto fail

:runtomcat
call %CATALINA_HOME%\bin\startup.bat
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.