@echo off
javac *.java
if %errorlevel% equ 0 (
    echo Compilation successful! Running the application...
    java Main
) else (
    echo Compilation failed!
    pause
)
