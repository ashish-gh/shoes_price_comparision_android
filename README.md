# Shoes Price Comparision
Shoes Price Comparison is an application which allows user to compare different store price for same shoes. This application includes a selection of different brands and compare price based on selected
shoes. Information is displayed to user, which makes the user easily search store in a map as well.

## Features 
The main features of this project are listed below:
* Login and Sign up for consumers
* Add information about products i.e. shoes
* Add information about stores
* Display information about stores in a map
* Review about stores
* Compare prices of shoes

## Pre-requisites
#### Android Studio
#### Dependencies

[Android Studio](https://developer.android.com/studio/install) provides a practical guide for installation of Android Studio.

**Dependencies**  Open `build.gradle` file and add dependencies. For example, the following `build.gradle` file implements `recyclerview` dependency.

``
implementation 'com.android.support:recyclerview-v7:28.0.0'
``    

## API usage
```

 * @api {get} /shoes/:shoesId Request Shoes information
 * @shoesName Shoes
 * @shoesDescription Shoes
 
 * @apiParam {Number} id User's unique ID.
 
 * @apiSuccess {String} firstname Firstname of the User.
 * @apiSuccess {String} lastname  Lastname of the User.
```

## HTTP requests
All API requests are made by sending a secure HTTPS request using one of the following methods, depending on the action being taken:

* [POST] Create a resource
* [PUT] Update a resource
* [GET] Get a resource or list of resources
* [DELETE] Delete a resource

## HTTP response
Each response will include a status object and (if successful) a `result` (result will be an object for single-record queries and an array for list queries). The status object contains an HTTP status_code, text, description, error_code (if an error occurred - see Error Codes) about the `result`. The `result` contains the result of a successful request. 

## HTTP response code
Each response will be returned with one of the following HTTP status codes:

* `200` `OK` The request was successful
* `400` `Bad Request` There was a problem with the request (security, malformed, data validation, etc.)
* `401` `Unauthorized` The supplied API credentials are invalid
* `404` `Not found` An attempt was made to access a resource that does not exist in the API

## Record Filtering
Record filters may also be specified in the query string. Exactly which fields may be filtered and sorted on for each resource are covered in the resource sections. A simple filter for `shoes` in  would look like this:

`/api/shoes=addidas`