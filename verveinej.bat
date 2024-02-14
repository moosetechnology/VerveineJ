@echo off
setlocal ENABLEDELAYEDEXPANSION

rem Directory for Verveine source
set "ROOT=%~dp0"
set "BASELIB=%ROOT%lib"

rem JVM options e.g. -Xmx2500m to augment maximum memory size of the vm to 2.5Go.
set "JOPT=-Xmx16000m"

rem Verveine option
rem set "VOPT=."

FOR /R "%BASELIB%" %%G IN (*.jar) DO set "LOCALCLASSPATH=%%G;!LOCALCLASSPATH!"
set "CLASSPATH=%CLASSPATH%;%LOCALCLASSPATH%"


java -Dfile.encoding=UTF-8 %JOPT% fr.inria.verveine.extractor.java.VerveineJMain %*