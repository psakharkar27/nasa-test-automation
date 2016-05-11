
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

Parameter	Type  | Default  | Description
------------- | -------------|------------
q	string  | None|Search text to filter results
limit  | int 10 | number of tracks to return
api_key	|string |DEMO_KEY


###Installation

Clone Repo.
git clone https://github.com/psakharkar27/nasa-test-automation.git

mvn build


###Tests

 * Verifying api_key parameter.
    * verify missing api key use case
    * verify invalid api key use case
    * verify valid api key use case
 
 * Verifying limit parameter
    * verify defualt limit use case
    * verify total limit use case
    * verify nagative limit use case
    * verify non interger limit use case
    
 * Verifying search 'q' parameter
    * verify defualt limit use case
    * verify total limit use case
    * verify nagative limit use case
    * verify non interger limit use case
 
 * Verify http request


###Running Test
mvn test -Denv=dev
