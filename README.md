
### Introduction
Project nasa-test-automation :  Automation test cases for **https://api.nasa.gov/planetary/sounds** service.
- - - -
### API Reference

#### **GET Sound Request** 

Request format:
```java
GET https://api.nasa.gov/planetary/sounds?q=apollo&api_key=DEMO_KEY

```

####QUERY PARAMETERS

Parameter Type| Default  | Description
------------- | ---------|------------
q	string    | None     |Search text to filter results
limit         | int 10   |number of tracks to return
api_key	      | string   |DEMO_KEY


###Installation

Clone Repo.
git clone https://github.com/psakharkar27/nasa-test-automation.git

Running the project

    * Do "mvn clean install -U" in order to download all artifacts

###Tests

 * Verifying api_key parameter use cases.
    * verify missing api key use case
    * verify invalid api key use case
    * verify valid api key use case
    * verify demo api key use case & rate limit use cases
 
 * Verifying limit parameter use cases.
    * verify default limit use case
    * verify total limit use case
    * verify negative limit use case
    * verify non integer limit use case
    
 * Verifying search 'q' parameter use case.
 
 * Verify http request use case.
 
 * Verify parameter sequence use case.


###Running Test
mvn test -Denv=dev
