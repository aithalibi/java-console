# Configuration du Projet Java

## Dépendances requises

- MySQL Connector/J (mysql-connector-j-8.0.33.jar)

## Instructions d'installation

1. Téléchargez MySQL Connector/J depuis le site officiel de MySQL :
   https://dev.mysql.com/downloads/connector/j/

2. Copiez le fichier .jar dans le dossier `lib` du projet

3. Ajoutez le driver au classpath lors de l'exécution :
   ```
   java -cp ".;lib/*" VotreClassePrincipale
   ```
   (Sur Unix/Linux, utilisez `:` au lieu de `;`) 