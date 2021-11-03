# Trains 420

A train simulation code example

## Domain

Here is an example:

```
   line A |--------------------|------[train]-------|
   line B |-------[train]------|--------------------|
       Terminal A           Terminal B           Terminal C
```

Requirements:
* The scheduling system should be able to handle multiple lines.
* The scheduling system should be able to handle multiple terminals.
* The scheduling system needs an interface to query the state of all trains. That should include at least the following information: current terminal and direction.
* The endpoint is required that allows a train to update its state within the scheduling system.
* Scheduling system needs to be able to process **pick-up** requests.
* It is recommended to manage time on discrete steps.

### Stack

- Scala 2.13
- Cats Effect 3
- Http4s
- Circe
- Pureconfig

### Things done:

* basic business logic (line, train and pickup request creation, update status endpoint, retrieve current statuses endpoint and basic scheduling service)
* unit tests of basic behaviors
* repository layer implementation

### Endpoints

- Get all train status

```
curl http://localhost:9000/trains/status
```

- Pickup request

```
curl -X POST http://localhost:9000/pickups -d '{"from":1, "to": 2, "createdAt": 15, "passenger": "john"}'
```

- Update train status

```
curl -X POST http://localhost:9000/trains/1/status -d '{"train_id":1, "pickup_id": 1, "current_location": 1, "destiny_location": 2, "ride_status": "riding", "direction":"forth"}'
```

### Run the app

```
sbt run
```

It will run the app on `http://localhost:9000`