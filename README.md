# Cloud Native Accounts System

### Technology and concepts:

* Java 8
* Spring Framework 5
* Spring Webflux/Reactor
* Spring Data/MongoDB
* Microservice architecture
* Service discovery
* API gateway
* Docker and Docker compose
* Continuous Integration

The following are out of the scope so far:

* Security
* Event Sourcing
* CQRS
* Service configuration 

### Domain

The system contains two main bounded contexts:

* Accounts
* Transactions

The accounts bounded context has account as main entity, and customer as a secondary entity, while the Transactions context in this domain has Transaction as main and only entity.

The following diagram shows the contexts, entities and relations: 

![Domain model](./assets/Cloud_Native_Accounts_Domain.png)

*NOTE*: For the sake of simplicity, customers has been modeled as a secondary entity within the accounts bounded context, but it should be an entire bounded context.

### Microservices

For this system, we are implementing one microserivce per bounded context, plus other non functional services in order to provide service discovery and an entry point for the system.

![Domain model](./assets/Cloud_Native_Accounts_Microservices.png)

### Endpoints

The following table contains the system endpoints:

| Endpoint | Method | Scope | Description |
| ------------ | -------------- | -------------- | ------- |
| `/accounts` | POST | Public | Creates a new account associated to the specified customer.  |
| `/accounts` | GET | Public | Retrieves all the accounts, it implements a customerId filter and limit/offset pagination.  |
| `/accounts/{accountId}` | GET | Public | Retrieves an specific account given the ID.  |
| `/accounts/{accountId}/transactions` | GET | Public | Retrieves all the transactions of a given account.  |
| `/transactions` | GET | Internal | Retrieves all the transactions, it implements offset/size pagination.|
| `/transactions/{transactionId}` | GET | Internal | Retrieves a specific transaction given the transaction id.  |

### Running

Considerations:

* For the sake of simplicity, the microservices are built using in memory MongoDB data bases. 
* A docker compose has been provided to run the entire system.
* The project must be built before running the system.
* When the system runs there are no accounts.
* When the system runs there are some customers loaded in the accounts database. 


```bash
$ mvn clean install
$ docker-compose up --build
$ curl http://localhost:8080/accounts -X POST -H "Content-Type: application/json" -H -d '{"customerId":"57f4dadc6d138cf005711f4e", "initialCredit":"2000.00"}'
$ curl http://localhost:8080/accounts
```

### Build
[![Build Status](https://secure.travis-ci.org/armandorvila/cloud-native-accounts.png)](http://travis-ci.org/armandorvila/cloud-native-accounts)  [![codecov.io](https://codecov.io/github/armandorvila/cloud-native-accounts/coverage.svg)](https://codecov.io/github/armandorvila/cloud-native-accounts) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/62c434b415f444e48bbed29f83b57a1f)](https://www.codacy.com/app/armandorvila/cloud-native-accounts?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=armandorvila/cloud-native-accounts&amp;utm_campaign=Badge_Grade)
