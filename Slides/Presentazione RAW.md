---
transition: slide
theme: solarized
---

![[images/Risorsa2.svg|850]]
### Presentazione di Ingegneria del Software Anno 2021/2022
---
<style>.reveal { font-size: 2em; }</style>
## L’idea di NaTour
- NaTour è un software creato per unire appassionati di escursioni in tutto il mondo attraverso un click.<!-- element class="fragment" data-frament-index="1" -->
- Il software è stato ideato principalmente per dare una spinta a tutte le persone, appassionate e non, nell'attività del trekking. <!-- element class="fragment" data-frament-index="2" -->
- Idea che deriva dal periodo... L'applicazione vuole porporsi di portare le persone fuori da casa propria e magari scoprire una nuova passione. <!-- element class="fragment" data-frament-index="3" -->
---
<style>.reveal { font-size: 2em; }</style>
## La nostra filosofia
- Alla base della nostra idea c'è la semplicità d'uso, così da garantire le stesse possibilità tra utenti meno esperti e più esperti. <!-- element class="fragment" data-frament-index="1" -->
- La grafica e l'interfaccia minimale per consentire una chiara lettura e comprensione di tutto quello che sta succedendo sull'app. <!-- element class="fragment" data-frament-index="2" -->
---
<style>.reveal { font-size: 2em; }</style>
## Alcuni Dati 
Secondo l'Isnart nel 2020, la popolazione italiana ha avuto un boom di turismo di prossimità, nel proprio paese, e sopratutto un turismo naturale... <br>
![[DatiNatura.png|700]]<!-- element class="fragment" data-frament-index="1" -->
--
## Dati Anagrafici
![[DatiOrizonjatalNoBackogun.png]]
---
<style>.reveal { font-size: 2em; }</style>
## Le funzionalità di NaTour
- Autenticazione via email oppure facilitata (Google e Facebook) <!-- element class="fragment" data-frament-index="1" -->
- Creare e visualizzare percorsi <!-- element class="fragment" data-frament-index="2" -->
- Caricare foto del percorso <!-- element class="fragment" data-frament-index="3" -->
- Esprimere un opinione sul percorso, indicando tempo di percorrenza e difficoltà <!-- element class="fragment" data-frament-index="4" -->
- Ricerca dei percorsi: <!-- element class="fragment" data-frament-index="5" -->
	- Località
	- Difficoltà 
	- Durata 
	- Accessibilità ai disabili 
- Amministrare percorsi e inviare mail promozionali (solo admin)<!-- element class="fragment" data-frament-index="6" -->
- Accedere senza Account <!-- element class="fragment" data-frament-index="7" -->

---
## Backend
Per il backend ci siamo affidati a uno dei framework più famosi in ambito Java per svillupare il nostro servizio REST API:

![[Spring_Framework_Logo_2018.svg|500]] <!-- element class="fragment" data-frament-index="1" -->
- Semplice da usare<!-- element class="fragment" data-frament-index="2" -->
- Veloce e Portabile <!-- element class="fragment" data-frament-index="3" -->
- Scalabile <!-- element class="fragment" data-frament-index="4" -->
---
## La nostra architettura
![[Rest.png]]
---
## Client 
![[aaaa.png|400]] <!-- element class="fragment" data-frament-index="1" -->
Consiste in un app Android sviluppata in Java <br> <!-- element class="fragment" data-frament-index="2" -->

Ci siamo avvalsi dei seguenti servizi e risorse: <!-- element class="fragment" data-frament-index="3" -->
<grid  drag="100 500" drop="down" flow="row" align="center" animate="type speed">
![[osma.png|150]] <!-- element class="fragment" data-frament-index="4" -->

![[material2.png|150x150]] <!-- element class="fragment" data-frament-index="5" -->

![[Amplify.png|150x150]] <!-- element class="fragment" data-frament-index="6" -->
</grid> 
---
## Servizi Cloud
Per la gestione del cloud ci siamo affidati a uno dei colossi di questo abito:

![[Amazon_Web_Services_Logo.svg|150x150]]
In particolare abbiamo usato i seguenti servizi:
---
<style>.reveal { font-size: 2em; }</style>
## Amazon Cognito 
![[cognito.png|150x150]]
Ci aiuta nell’autenticazione degli utenti
grazie all’uso di una sua interna pool di utenti, esso ci permette di tenere traccia di qualsiasi utente all’interno del nostro software.
---
<style>.reveal { font-size: 2em; }</style>
## Amazon EC2
![[Amazon-EC2@4x-e1593195270371.png|150x150]]
Ci hostrare il server (SpringBoot) su Cloud. <br>Il servizio offre la possibilità di scalare in base alle esigenze su una macchina più performante per gestire al meglio tutte le richieste.
---
<style>.reveal { font-size: 2em; }</style>
## Amazon RDS
![[Arch_Amazon-Aurora_64@5x.png|150x150]]
Database relazionale che ci permette, attraverso SpringBoot, di tenere traccia di tutte le modifiche e aggiunte degli utenti nell'applicazione.<br><br><br>
---
<style>.reveal { font-size: 2em; }</style>
## Amazon S3
![[amazon-s3-simple-storage-service-logo-9A3F37976E-seeklogo.com.png|150x150]]
Ci permette di immagazzinare le foto che gli utenti caricano dall'applicazione, è comodamente diviso in "bucket", cioè secchi, per poter separare i diversi tipi di file.<br><br>
---
## Conclusione
![[images/Risorsa2.svg|650]]
### Sviluppata Da:
### Alex Ciacciarella &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; Francesco Ciccarelli
##### N86003179 &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj;&zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; &zwnj; N86003285
