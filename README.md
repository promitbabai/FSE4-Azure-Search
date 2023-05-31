# FSE4-Azure-Search
------------------------


The code included in this sample is intended to illustrate using Azure Cosmos DB for MongoDB API from SpringBoot application

Search Microservices with Monggo DB Deployed on Azure with below 2 DB connection options -
    1) MongoDB Atlas Cloud connection
    2) CosmosDb for MongoDb Api on Azure,

    Please navigate to the application.yml file and select one of the URI's

## Running this sample


1. Create a new database in your Azure Cosmos DB account.
 a. Create the Collection and Documents from the JSON data

2. Clone this repository

3A. Substitute the ``spring.data.mongodb.uri`` in  with your Azure Cosmos DB account's connection string.

Change in the file = *src\main\resources\application.yml*
Replace with the name of the database in your Azure Cosmos DB account.
            spring:
              data:
                mongodb:
                  uri: ##########   ( copy the value from PRIMARY CONNECTION STRING)
                  database: ########

OR

3B. Change in the file = *src\main\resources\application.properties*
Substitute the ``spring.data.mongodb.uri`` in  with your Azure Cosmos DB account's connection string.
    ```java
    spring.data.mongodb.database=<database-name>
    spring.data.mongodb.uri=mongodb://<account-name>:<account-key>@<account-name>.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@<account-name>@
    ```

    > *If the key string used in the MongoDB URI contains special characters, such as plus signs, use the URL-encoded value of that key. Otherwise, you may face issues trying to connect and see errors pertaining to SASL Authentication failure. This is especially true when working with [Azure Cosmos DB Emulator](https://docs.microsoft.com/azure/cosmos-db/local-emulator) over a local network.*

4. Run your application:

    ```bash
    mvn spring-boot:run
    ```

