# Dismantling Station
In this station cars are dismantled into their constituent parts. Each part is weighed and registered, including a reference to the car it came from.
The parts are put on pallets with each pallet containing only one type of parts. Each pallet has a
maximum weight capacity. 

## Create a car part
Make a POST request specifying the <b>car ChassisNo</b> the part came from, the part <b>weight</b> and the part <b>type</b> in  the request body to this URL:
  ```url
http://localhost:8080/DismantleStation/server/parts
```
Request Body example:
```json
{
	"weight": 323.43,
	"type":"door",
	"chassisNo": "DW3WF114590"
}
```

## Get a car part by Id
Make a GET request specifying the <b>part id</b> in the URL:
  ```url
http://localhost:8080/DismantleStation/server/parts/id
```
<b>Example</b>: Query for a part with id 1
```url
http://localhost:8080/DismantleStation/server/parts/1
```

## Get car parts
Make a GET request to get all registered car parts to this URL:
  ```url
http://localhost:8080/DismantleStation/server/parts
```
### Get parts by type
Make a GET request to get all car parts with a given type to this URL:
  ```url
http://localhost:8080/DismantleStation/server/parts?type=...
```

<b>Example</b>: Query for a part with type: door
```url
http://localhost:8080/DismantleStation/server/parts?type=door
```

### Get parts by car chassisNo

Make a GET request to get all car parts with a given type to this URL:
  ```url
http://localhost:8080/DismantleStation/server/parts?chassisNo=...
```

<b>Example</b>: Query for a part with chassisNo: DW3WF114590
```url
http://localhost:8080/DismantleStation/server/parts?chassisNo=DW3WF114590
```

### Get parts by car chassisNo and type
  ```url
http://localhost:8080/DismantleStation/server/parts?chassisNo=...&type=...
```

<b>Example</b>: Query for a part with chassisNo: DW3WF114590 and type: dor
```url
http://localhost:8080/DismantleStation/server/parts?chassisNo=DW3WF114590&type=door
```

To create products and systems the get parts method has two extra optional parameters: number and car model.

To create a product, it's necessary to get a number of parts by type, making a GET request to the following URL:
```url
http://localhost:8080/DismantleStation/server/parts?type=door&number=2
```

To create a system, it's necessary to get parts by type and model, making a GET request to the following URL:
```url
http://localhost:8080/DismantleStation/server/parts?type=door&model=Mercedes
```


## Create a pallet
Make a POST request specifying the <b>pallet weight</b>and the pallet <b>type</b> in the request body to this URL:
  ```url
http://localhost:8080/DismantleStation/server/pallets
```
Request Body example:
```json
{
	"weight": 1323.43,
	"type":"door",
}
```

## Assign a part to a pallet
Make a PUT request specifying the <b>part id</b> in the request body to this URL:
  ```url
http://localhost:8080/DismantleStation/server/pallets
```
Request Body example:
```json
{
	"id": 4
}
```
NOTE: We can specify all the carPart parameters in the body of the PUT request but only the id will be used. 

## Get a pallet by Id
Make a GET request specifying the <b>part id</b> in the URL:
  ```url
http://localhost:8080/DismantleStation/server/pallets/id
```
<b>Example</b>: Query for a pallet with id 1
```url
http://localhost:8080/DismantleStation/server/pallets/1
```

## Get pallets
Make a GET request to get all registered pallets to this URL:
  ```url
http://localhost:8080/DismantleStation/server/pallets
```
### Get parts by type
Make a GET request to get all pallets with a given type to this URL:
  ```url
http://localhost:8080/DismantleStation/server/pallets?type=...
```

<b>Example</b>: Query for a pallet with type: door
```url
http://localhost:8080/DismantleStation/server/pallets?type=door
```
