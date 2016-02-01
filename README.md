[![Build Status](https://travis-ci.com/idstein/agt.svg?token=QTWdMaqXyCvwHbkPxMwB&branch=master)](https://travis-ci.com/idstein/agt)

## Synopsis

A Java development task that demonstrates programming experience and skill for AGT.

## Requirements

### Functionality

- It counts the words in each text file (*.txt) in a given directory and all sub-directories 
-	For files longer than 1000 words, it also searches for words repeating more than 50 times in them
-	The service receives as an input a directory path for a directory accessible from the location where the service is running (assume it’s in the local filesystem for simplicity)
-	The output for all the above tasks should be provided as 2 objects (“lists”):
  -	one of “long files” (more than 1000 words in a file, along with all words that repeat >50 times and the number of their occurrences)
  -	the other of “short files”
-	They want to be able to use the service concurrently from different clients (thus the service endpoint should be agnostic to the technology of the client that uses it) and the returned format should be JSON-based

### Non-functional requirements
1. Concurrent access from multiple clients (see ConcurrentClientSupportTest)
2. As the IT department is known to change their mind with regards to the information they need from time to time, it must be possible to extend the service
3. Ensure functionality with unit test and a high code coverage before handing it out to IT

[Use case diagramm](https://www.lucidchart.com/invitations/accept/7bd9d298-3531-4e64-993f-ee86e93b487c)

## Code Example

The webserver can be started using the following command inside the pathvisitor folder
`mvn exec:java`

It will listen per default on `http://localhost:8080/services/`

The response JSON format follows a simple MOXy JAXB marshalling (example by calling `http://localhost:8080/services/visit/src/test/resources/counting`):

    {
      "type": "directory",
      "name": "counting",
      "children": [
        {
          "type": "longTextFile",
          "name": "1000words.txt",
          "wordCount": 1000,
          "usageByMostFrequentlyUsedWord": {
            "entry": [
              {
                "key": "bla",
                "value": 1000
              }
            ]
          }
        },
        {
          "type": "textFile",
          "name": "1word.txt",
          "wordCount": 1
        }
      ]
    }

## Installation

You should have a local Java 8 JRE installed and use it to run Maven.

To create self-contained JARs run on the root folder of this checked out repository:

`mvn package`

Inside the target folder fo client-java and pathvisitor now existing a runnable jar. For the server executable copy any folder you would like to search next to the pathvisitor-VERSION.jar.

## Tests
Run local tests with:

`mvn test`

## Run client

`java -jar client-java-VERSION-with-dependencies.jar [path/relative/to/server/jar] [optional base urihttp://localhost:8080]`

## Third party

assertConcurrent - Test code used from https://github.com/junit-team/junit/wiki/Multithreaded-code-and-concurrency
