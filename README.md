****
# Rewards API
## Rewards API Java Coding Challenge

****

### Gradle Build
./gradlew bootJar

### Run using Gradle
./gradlew bootRun

### Run as a standalone jar
java -jar ./build/libs/rewards-api-0.0.1-SNAPSHOT.jar

****
******
### Build Docker image

The Dockerfile is in the project root folder

> `cd [workspace]/rewards-api`

To build the image run the command

> `docker build . --tag rewards-api`

After the image is built, run the container

> `docker run -d -p 8080:8080 --name rewards-api rewards-api:latest`

To stop the running container

> `docker stop rewards-api`

To restart the container after first run
> `docker start rewards-api`


****
******

### Rewards API Design Documents


  * [Rewards-API Design](./docs/design/rewards-api_design.md)  


  * [Java Gradle Version](./docs/design/java-gradle_version.md)


  * [Rewards API Class Hierarchy](./docs/design/rewards-api_class-hierarchy.md)  


  * [H2 In-Memory dB Console - Transactions.md](./docs/design/h2-console_transactions.md)

  * [JSON Response Object](./docs/design/rewards-api_json-response.md)

  * [Customer Names and Timestamps](./docs/design/customer-names_timestamps.md)





*****



### Usage

**Rest API endpoints**

http://localhost:8080/api/v1/rewards


http://localhost:8080/api/v1/rewards/{month}


http://localhost:8080/api/v1/rewards/{month}/{customerId}


http://localhost:8080/api/v1/rewards/{firstMonth}/{customerId}/{lastMonth}

**Health endpoint**

http://localhost:8080/actuator/health

****

### JavaDocs
[rewards-api 0.0.1-SNAPSHOT API](https://s3.amazonaws.com/daveray.net/www/rewards-api/javadoc/index.html)

*****

### Limitations
The Data Wrangler will generate only 3 months of demo data for a list of 12 customers. 

Transaction data is randomly generated for {id, customerId, amount}.


### Transaction Events Log
**Generated file location:**   
`./src/main/resources/rewards-api_transaction-list.json
`

**Runtime file location:**   
`./build/resources/main/rewards-api_transaction-list.json
`

The transaction events log is generated at application startup and seeded with random transactions using twelve hardcoded customer names and timestamps for the three month period of July, August and September.

The file is written to src/main/resources, unless the file is already present. Deleting this file will cause a new file to be generated with random data.

The list of customers are: Dean Pacheco, Hope Griffith, Rayan Lin, Regina Hensley, Lucia Bowen, Austin Morales, Zayne Mann, Kenya Mendoza, Alyvia Simmons, Scarlet Barnett, Alayna Collins and Demarcus Oconnell. 
*****
### API Usage Examples:

*****


*****

> **`Find the the total spend amount and earned rewards points for all customers with transactions in the month of July`**

> * request
> 
`GET http://localhost:8080/api/v1/rewards/july
`

> * result:
> 
[{"amount":"8.00","month":"JULY","customerId":"2127260392","name":"Scarlet Barnett","points":"0"},{"amount":"69.00","month":"JULY","customerId":"938247567","name":"Alayna Collins","points":"2"},{"amount":"126.00","month":"JULY","customerId":"954238437","name":"Dean Pacheco","points":"16"},{"amount":"1822.00","month":"JULY","customerId":"1170920713","name":"Alyvia Simmons","points":"3342"},{"amount":"164.00","month":"JULY","customerId":"871832891","name":"Kenya Mendoza","points":"64"},{"amount":"459.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"331"},{"amount":"2.00","month":"JULY","customerId":"443821775","name":"Austin Morales","points":"0"},{"amount":"95.00","month":"JULY","customerId":"124596072","name":"Lucia Bowen","points":"43"},{"amount":"118.00","month":"JULY","customerId":"852848378","name":"Regina Hensley","points":"46"},{"amount":"1494.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"2590"},{"amount":"583.00","month":"JULY","customerId":"147025352","name":"Zayne Mann","points":"946"}]

****
*****

> **`Find the total spend amount and earned rewards points for a single customer with transactions in the month of August by customerId`**

> * request
> 
`GET http://localhost:8080/api/v1/rewards/august/954238437
`

> * result:
> 
[{"amount":"2137.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"3672"}]


****
*****

> **`Find the total spend amount and earned rewards points for a single customer's transactions in the quarter using the range of months from July to September by customerId`**

> * request
> 
`GET http://localhost:8080/api/v1/rewards/july/1975730415/september
`

> * result:
> 
[{"amount":"1494.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"2590"},{"amount":"236.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"77"},{"amount":"426.00","month":"SEPTEMBER","customerId":"1975730415","name":"Hope Griffith","points":"520"}]

****
*****

> **`Find the total spend amount and earned rewards points for a single customer's transactions in the two month range from August to September by customerId`**

> * request
> 
`GET http://localhost:8080/api/v1/rewards/august/871832891/september
`

> * result:
> 
[{"amount":"370.00","month":"AUGUST","customerId":"871832891","name":"Kenya Mendoza","points":"590"},{"amount":"78.00","month":"SEPTEMBER","customerId":"871832891","name":"Kenya Mendoza","points":"24"}]

***** 
*****


> **`Find the spend amount and earned rewards points for each transaction for all customers in all months`**

> * request
> 
`GET http://localhost:8080/api/v1/rewards
`

> * result:
> 
[{"amount":"17.00","month":"JULY","customerId":"938247567","name":"Alayna Collins","points":"0"},{"amount":"52.00","month":"JULY","customerId":"938247567","name":"Alayna Collins","points":"2"},{"amount":"95.00","month":"SEPTEMBER","customerId":"938247567","name":"Alayna Collins","points":"45"},{"amount":"957.00","month":"JULY","customerId":"1170920713","name":"Alyvia Simmons","points":"1764"},{"amount":"864.00","month":"JULY","customerId":"1170920713","name":"Alyvia Simmons","points":"1578"},{"amount":"1.00","month":"JULY","customerId":"1170920713","name":"Alyvia Simmons","points":"0"},{"amount":"90.00","month":"AUGUST","customerId":"1170920713","name":"Alyvia Simmons","points":"40"},{"amount":"89.00","month":"SEPTEMBER","customerId":"1170920713","name":"Alyvia Simmons","points":"39"},{"amount":"2.00","month":"JULY","customerId":"443821775","name":"Austin Morales","points":"0"},{"amount":"9.00","month":"AUGUST","customerId":"443821775","name":"Austin Morales","points":"0"},{"amount":"5.00","month":"AUGUST","customerId":"443821775","name":"Austin Morales","points":"0"},{"amount":"27.00","month":"AUGUST","customerId":"443821775","name":"Austin Morales","points":"0"},{"amount":"10.00","month":"JULY","customerId":"954238437","name":"Dean Pacheco","points":"0"},{"amount":"59.00","month":"JULY","customerId":"954238437","name":"Dean Pacheco","points":"9"},{"amount":"57.00","month":"JULY","customerId":"954238437","name":"Dean Pacheco","points":"7"},{"amount":"373.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"596"},{"amount":"2.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"0"},{"amount":"899.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"1648"},{"amount":"765.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"1380"},{"amount":"98.00","month":"AUGUST","customerId":"954238437","name":"Dean Pacheco","points":"48"},{"amount":"86.00","month":"SEPTEMBER","customerId":"954238437","name":"Dean Pacheco","points":"36"},{"amount":"7.00","month":"SEPTEMBER","customerId":"954238437","name":"Dean Pacheco","points":"0"},{"amount":"25.00","month":"SEPTEMBER","customerId":"954238437","name":"Dean Pacheco","points":"0"},{"amount":"57.00","month":"SEPTEMBER","customerId":"954238437","name":"Dean Pacheco","points":"7"},{"amount":"132.00","month":"AUGUST","customerId":"1218741205","name":"Demarcus Oconnell","points":"114"},{"amount":"66.00","month":"AUGUST","customerId":"1218741205","name":"Demarcus Oconnell","points":"16"},{"amount":"858.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"1566"},{"amount":"34.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"587.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"1024"},{"amount":"15.00","month":"JULY","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"82.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"32"},{"amount":"79.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"29"},{"amount":"66.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"16"},{"amount":"7.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"2.00","month":"AUGUST","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"30.00","month":"SEPTEMBER","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"9.00","month":"SEPTEMBER","customerId":"1975730415","name":"Hope Griffith","points":"0"},{"amount":"333.00","month":"SEPTEMBER","customerId":"1975730415","name":"Hope Griffith","points":"516"},{"amount":"54.00","month":"SEPTEMBER","customerId":"1975730415","name":"Hope Griffith","points":"4"},{"amount":"74.00","month":"JULY","customerId":"871832891","name":"Kenya Mendoza","points":"24"},{"amount":"90.00","month":"JULY","customerId":"871832891","name":"Kenya Mendoza","points":"40"},{"amount":"370.00","month":"AUGUST","customerId":"871832891","name":"Kenya Mendoza","points":"590"},{"amount":"74.00","month":"SEPTEMBER","customerId":"871832891","name":"Kenya Mendoza","points":"24"},{"amount":"4.00","month":"SEPTEMBER","customerId":"871832891","name":"Kenya Mendoza","points":"0"},{"amount":"2.00","month":"JULY","customerId":"124596072","name":"Lucia Bowen","points":"0"},{"amount":"93.00","month":"JULY","customerId":"124596072","name":"Lucia Bowen","points":"43"},{"amount":"1.00","month":"AUGUST","customerId":"124596072","name":"Lucia Bowen","points":"0"},{"amount":"77.00","month":"AUGUST","customerId":"124596072","name":"Lucia Bowen","points":"27"},{"amount":"68.00","month":"AUGUST","customerId":"124596072","name":"Lucia Bowen","points":"18"},{"amount":"68.00","month":"AUGUST","customerId":"124596072","name":"Lucia Bowen","points":"18"},{"amount":"14.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"0"},{"amount":"95.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"45"},{"amount":"186.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"222"},{"amount":"85.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"35"},{"amount":"79.00","month":"JULY","customerId":"56938671","name":"Rayan Lin","points":"29"},{"amount":"18.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"0"},{"amount":"28.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"0"},{"amount":"530.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"910"},{"amount":"26.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"0"},{"amount":"24.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"0"},{"amount":"70.00","month":"AUGUST","customerId":"56938671","name":"Rayan Lin","points":"20"},{"amount":"420.00","month":"SEPTEMBER","customerId":"56938671","name":"Rayan Lin","points":"690"},{"amount":"57.00","month":"SEPTEMBER","customerId":"56938671","name":"Rayan Lin","points":"7"},{"amount":"22.00","month":"JULY","customerId":"852848378","name":"Regina Hensley","points":"0"},{"amount":"96.00","month":"JULY","customerId":"852848378","name":"Regina Hensley","points":"46"},{"amount":"15.00","month":"AUGUST","customerId":"852848378","name":"Regina Hensley","points":"0"},{"amount":"693.00","month":"AUGUST","customerId":"852848378","name":"Regina Hensley","points":"1236"},{"amount":"534.00","month":"AUGUST","customerId":"852848378","name":"Regina Hensley","points":"918"},{"amount":"6.00","month":"AUGUST","customerId":"852848378","name":"Regina Hensley","points":"0"},{"amount":"381.00","month":"AUGUST","customerId":"852848378","name":"Regina Hensley","points":"612"},{"amount":"47.00","month":"SEPTEMBER","customerId":"852848378","name":"Regina Hensley","points":"0"},{"amount":"87.00","month":"SEPTEMBER","customerId":"852848378","name":"Regina Hensley","points":"37"},{"amount":"738.00","month":"SEPTEMBER","customerId":"852848378","name":"Regina Hensley","points":"1326"},{"amount":"8.00","month":"JULY","customerId":"2127260392","name":"Scarlet Barnett","points":"0"},{"amount":"2.00","month":"AUGUST","customerId":"2127260392","name":"Scarlet Barnett","points":"0"},{"amount":"13.00","month":"SEPTEMBER","customerId":"2127260392","name":"Scarlet Barnett","points":"0"},{"amount":"35.00","month":"JULY","customerId":"147025352","name":"Zayne Mann","points":"0"},{"amount":"548.00","month":"JULY","customerId":"147025352","name":"Zayne Mann","points":"946"},{"amount":"6.00","month":"SEPTEMBER","customerId":"147025352","name":"Zayne Mann","points":"0"},{"amount":"147.00","month":"SEPTEMBER","customerId":"147025352","name":"Zayne Mann","points":"144"}]

****
****
11/21/2023 | http://daveray.net | daveray@daveray.net
****


