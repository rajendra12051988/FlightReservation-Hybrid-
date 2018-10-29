Set projectLocation=C:\Selenium\workspace\FlightReservation(Hybrid)
cd %projectLocation%
Set classPath=%ProjectLocation%\bin;%ProjectLocation%\libs\*
java org.testng.TestNG testng.xml
pause