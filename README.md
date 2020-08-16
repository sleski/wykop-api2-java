# wykop-api2-java
Few examples how to play with wykop api2 from java.

## Documentation

https://www.wykop.pl/dla-programistow/apiv2docs/logowanie-uzytkownika/

https://www.wykop.pl/dla-programistow/apiv2docs/podpisywanie-zadan/

https://www.wykop.pl/dla-programistow/twoje-aplikacje/

https://golb.hplar.ch/2019/01/java-11-http-client.html

## How to use it

1) Please update file [wykop.properties](src/main/resources/wykop.properties) - add your credentials

2) From command line: 
```
mvn clean install

mvn exec:java -Dexec.mainClass="pl.wykop.Login" -Dexec.cleanupDaemonThreads=false

mvn exec:java -Dexec.mainClass="pl.wykop.Mikroblog" -Dexec.args="{copy_user_key_from_previous_command}" -Dexec.cleanupDaemonThreads=false


```
3) Enjoy ( ͡° ͜ʖ ͡° )つ──☆*:・ﾟ


