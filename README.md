# Distributed systems - final thesis

## Deployment diagram
![image](https://assets.digitalocean.com/articles/architecture/load_balancer.png)

## Components
### Eureka
If you have a distribuded system you’ll probably realize that you will end up with a lot of services. Depending on how fine grained your microservices are and how far you go, you can end up with hundreds of microservices.

But what if you change the location and port of your microservice? Are you going to change it everywhere you use the microservice? That will be really dauting.
Well, what if we had the possibility to make all microservices register themself in some kind of a list, containing their name, IP and port, then those problems are gone as well. Well, that’s exactly what Eureka does. Eureka is a service registry microservice, which allows all microservices to sign themself up to that list.

#### Setting up
```java
@SpringBootApplication
@EnableEurekaServer
public class SpringBootEurekaRegistryApplication {

}
```

application.properties
```properties
server.port=8761
eureka.instance.hostname=localhost
eureka.client.registerWithEureka=false
eureka.client.fetchRegistry=false
eureka.server.enableSelfPreservation=false
```


### Load Balancer
The load balancer is implemented with ``Feign`` from Netflix. It has the following dependecies
- Redis - is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker. It supports data structures such as strings, hashes, lists, sets, sorted sets with range queries, bitmaps, hyperloglogs and geospatial indexes with radius queries.
- Eureka - to register itself as a service.
It comes with the default rule for balancing between services, which is Round-robin. But you can choose to something else.


### Web
This is a Spring Boot application with the following routes:
```java

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {
}


@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
}
```
As dependencies it has:
- Eureka - for registering itself(used for load balance to determine between which nodes to balance the incoming traffic)
- PostgreSQL - used as database
- Liquibase - source control for database, used for migrating.


### Liquibase
All changes to the database are stored in text files (XML, YAML, JSON or SQL) and identified by a combination of an "id" and "author" tag as well as the name of the file itself. A list of all applied changes is stored in each database which is consulted on all database updates to determine what new changes need to be applied. As a result, there is no database version number but this approach allows it to work in environments with multiple developers and code branches.

Liquibase automatically creates DatabaseChangeLog Table and DatabaseChangeLogLock Table when you first execute a changeLog File.

Example

```xml
<include file="../sql/create_company_table.sql" relativeToChangelogFile="true"/>
<include file="../sql/create_customer_table.sql" relativeToChangelogFile="true"/>
```


```sql
CREATE TABLE companies(
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  country TEXT NOT NULL,
  rating NUMERIC
);


CREATE TABLE customers(
  id SERIAL PRIMARY KEY,
  name TEXT,
  surname TEXT,
  age NUMERIC,
  email TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  company_id INTEGER NOT NULL,
  FOREIGN KEY (company_id) REFERENCES companies(id)
);

```

### Redis dependency
 ```bash
 docker pull redis
 docker run -p 6379:6379 redis
 ```
 
 
### Deployment
``` docker-compose up```
