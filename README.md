![alt text](./Slides/images/NaTourLogo.svg)

# Informazioni
Questo software è stato creato per il corso INGSW21-22, che prevede nel documentare l'intero ciclo di vita di un sistema Client-Server con l'utilizzo di servizi Public Cloud.<br>
Si può leggere tale documentazione qui: [Link](./Slides/INGSW2122_N_34.pdf)

# Come Funziona
Il sistema è composto da un client Android scritto in Java e un server SpringBoot anch'esso in Java, inoltre sono stati usati AWS Cognito per l'autentificazione e AWS S3 per lo storage delle foto.

# Funzionalità
- :closed_lock_with_key: Autenticazione via email oppure facilitata (Google e Facebook)
- :unlock: Accedere senza Account
- :herb: Creare e visualizzare percorsi
- :camera: Caricare foto del percorso
- :raising_hand_man: Esprimere un opinione sul percorso, indicando tempo di percorrenza e difficoltà
- :mag: Ricerca dei percorsi:
	- Località
	- Difficoltà
	- Durata
	- Accessibilità ai disabili
- :man_mechanic: Amministrare percorsi e inviare mail promozionali (solo admin)

# Istruzioni d'uso

## Server

#### Configurare Spring
```bash
nano Backend/SpringBoot/Server/main/resources/application.properties
```

```bash
#Server
server.address=#ipadress         example:192.168.1.2
server.port=#port                example:8080
spring.application.name=Server

#Database
spring.datasource.url=#database url       example:jdbc:postgresql://192.168.1.2:5432/spring
spring.datasource.username=#username      example:postgres
spring.datasource.password=#password      example:admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

#Java Mail
spring.mail.host=#smtpclient              example:smtp.gmail.com
spring.mail.port=#port                    example587
spring.mail.username=#username            example:natour21@gmail.com
spring.mail.password=#token              
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

```bash
cd Backend/SpringBoot/Server/
mvn -f pom.xml clean package
java -jar target/Server-0.0.1-SNAPSHOT.jar.original
```

## Client

Il Client va compilato tramite Android Studio.<br>
Per fare funzionare il login e lo storage di foto è necessario configuare Amplify tramite la guida ufficiale: [Link](https://docs.amplify.aws/start/getting-started/installation/q/integration/android/#option-2-follow-the-instructions)

#### Cognito-S3
Configurare in modo standard entrambi i servizi

## Presentazione
Questa è la presentazione che abbiamo mostrato durata la discussione del progetto: [Link]()
