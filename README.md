
# Pay my Buddy

<p align="center">
<img src="https://img.shields.io/badge/Language-Java-green">
<img src="https://img.shields.io/badge/Framework-Spring-green">
<img src="https://img.shields.io/badge/PM-Maven-green">
</p>

Application de transfert d'argent créée dans le cadre d'un projet de formation OpenClassrooms.


## Usage
Cette application comprend une API permettant d'accéder aux données d'une BDD MySQL ainsi qu'un site web permettant d'accéder à l'API et d'effectuer des opérations simples de façon pratique.\
Le code comprend deux dossiers et un fichier SQL :
- `server` : Contient le code de l'API en java
- `client` : Contient le site web (HTML, CSS, JS) prêt à l'emploi
- `database.sql` : Un script SQL permettant de créer la base de données nécessaire au bon fonctionnement de l'API, avec une dizaine de comptes exemples, de transactions test, etc.

Une version `release` sous la forme d'un jar executable est également disponible dans la section releases si vous ne souhaitez pas devoir recompiler l'API vous-même.

L'API est une API REST composée de 3 couches :
- les controlleurs recoivent et gèrent les requêtes HTTP
- les services traitent le contenu des requêtes et des données 
- les repositories communiquent avec la BDD pour insérer et extraire des données

#### Diagramme de classes 
<p align="center">
<img src="https://github.com/Psolyvem/OC-Projet6/blob/dev/readme/Diagramme%20de%20classes.png?raw=true" alt="Diagramme de classes" width="75%">
</p>

La base de données comporte 5 tables :
- `User` : contient les identifiants d'un utilisateur ainsi que son solde
- `Contact` : contient des paires d'id d'utilisateurs
- `Transaction` : contient les détails d'une transaction (destinataire, cible, montant, date, description et taxe)
- `Bank Account` : contient les informations d'un compte bancaire (RIB, IBAN, user)
- `Bank Operation` : de la même façon que transaction, contient les données relatives aux opérations effectuées entre le compte bancaire et l'application (compte, date et montant)

#### Modèle de données physique
<p align="center">
<img src="https://github.com/Psolyvem/OC-Projet6/blob/dev/readme/Mod%C3%A8le%20Physique%20de%20Donn%C3%A9es.png?raw=true" alt="Modèle de données physique" width="75%">
</p> 

Il est à noter que les tables `Bank Account` et `Bank Operation`, de la même façon que leurs repositories et leurs services dans l'API ne sont actuellement pas fonctionnels. Il est tout a fait possible d'effectuer des opérations sur ces tables via les classes de l'API cependant aucun controller n'a été mis en place pour le faire depuis l'interface web étant donné que leur implémentation n'était pas requise pour cette version. Ils ont néanmoins été pris en compte dans le cadre de l'évolution de l'application.

## Installation

Si vous souhaitez installer ce projet il vous faudra au préalable installer :
- Maven : https://maven.apache.org/download.cgi
- MySQL : https://dev.mysql.com/downloads/

Lorsque MySQL sera installé, téléchargez le fichier `database.sql`.\
Ouvrez un terminal et connectez vous a MySQL :
```bash
Mysql -u root -p
```
Déplacez vous ensuite jusqu'a l'emplacement du fichier `database.sql` et exécutez le.
```bash
source database.sql
```
Une fois ces prérequis installés et configurés, vous devrez configurer l'API en ajoutant un fichier `application.properties` dans "server/src/main/resources"

Ce fichier doit contenir les propriétés suivantes : 

```javascript
#Global
spring.application.name=server

#Tomcat
server.port=9010

#MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/pay_my_buddy?serverTimezone=UTC 
spring.datasource.username=Admin
spring.datasource.password=Admin

#Logs
logging.level.root=ERROR
logging.level.org.springframework=ERROR
logging.level.com.paymybuddy=INFO
```

- `server.port` : choisissez le port sur lequel l'application doit s'executer. Si vous décidez de changer le port, vous devrez également changer la configuration du fichier script.js du client
- `spring.datasource.url` : si vous avez gardé la configuration par défaut de MySQL et exécuté le fichier database.sql sans modifications, ne changez rien, sinon, ajustez le port ou le nom de la base de donnée pour correspondre a vos modifications
- `spring.datasource.username` : remplacez "Admin" par le nom d'utilisateur MySQL que vous avez défini
- `spring.datasource.password` : remplacez "Admin" par le le mot de passe de l'utilisateur MySQL que vous avez défini

Vous pouvez maintenant compiler et executer l'API. Si vous souhaitez l'executer, placez vous a la racine du projet et faites un :
```bash
mvn spring-boot:run
```
Si vous souhaitez compiler le projet pour obtenir un jar executable faites :
```bash
mvn package
```
puis dans le dossier server/target exécutez le jar avec :
```bash
java -jar server-0.1.0-jar-with-dependencies.jar
```

Maintenant que l'API fonctionne, vous pouvez télécharger le dossier client et ouvrir login.html (ou n'importe quelle autre page) dans votre navigateur pour avoir accès à l'interface web.

> [!NOTE]
> Si vous avez modifié le port de l'application, vous devez également le modifier dans le fichier `client/js/script.js`\
> Pour cela ouvrez le fichier et modifiez la constante `APIURL` située au début du fichier pour correspondre a vos modifications

Pour vous connecter sur l'application via l'interface web vous pouvez utiliser les identifiants administrateur
```bash
Email : admin@paymybuddy.fr
Password : admin
```
Tout les noms d'utilisateurs et mot de passes sont dans le fichier database.sql
>[!IMPORTANT]
>Les mots de passe sont encodés avec Bcrypt, si vous souhaitez en ajouter manuellement, pensez a les encoder également sans quoi l'API ne vous laissera pas vous connecter.
