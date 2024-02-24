Feature: Getting Photo By Id
  In order to get a Photo from the data base
  Loading previously the data base from Urls: https://jsonplaceholder.typicode.com/albums and https://jsonplaceholder.typicode.com/photos

  Scenario: Send a request to get a photo by Id
    Given Loading the data into a H2 BBDD from Urls
    When I send a GET request
    Then I verify the data received