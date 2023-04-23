# KALAHA GAME
This application is designed to execute rules of the game and keep status of it.

# Game rules
### Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits, each player has a larger pit. At the start of the game, there are six stones in each of the six round pits .

### Rules:
###Game Play
The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones on to the right, one in each of the following pits, including his own big pit. No stones are put in the opponents' big pit. If the player's last stone lands in his own big pit, he gets another turn. This can be repeated several times before it's the other player's turn.

###Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.

###The Game Ends
The game is over as soon as one of the sides runs out of stones. The player who still has stones in his pits keeps them and puts them in his big pit. The winner of the game is the player who has the most stones in his big pit.

## Key features
* Client could create game board
* Client could play game
* Client could retrieve game board

## Tech stack
* Java
* Spring Boot
* Angular
* Maven
* Docker

## How to run
### Backend
* Clone this repository
* Run `docker build -t bol/kalaha .` to build a docker image
* Run `docker run -p3000:3000 bol/kalaha` to run image

### Frontend
* Clone kalaha-fe repository
* Run `ng serve` 

### Usage
Play according to the Game rules

## Endpoints

### POST
```http request
curl --location 'http://localhost:3000/api/boards' --header 'Content-Type: application/json'
```
This endpoint initialize a board game with default settings.

##### Response
This endpoint returns a response with HTTP status code 200 (OK) play request successfully processed.

`boardId` (UUID): the board id \
`nextPlayer` (enum): next player: ONE, TWO \
`rowOne` (RowDetails): object containing pits and kalaha for Player.ONE\
`rowTwo` (RowDetails): object containing pits and kalaha for Player.TWO\
`gameOver` (boolean): state of the end of the game\
`winner` (enum): winner player: ONE, TWO
##### Response models
###### RowDetails
`pits` (Array): keeps pits \
`kalahaStones` (int): number of stones in Kalaha

###### pitDetails
`stones` (int): number  of stones in pit
Example of response:
```json
{
  "boardId": "43ea97d0-b274-4550-8b29-4d14d1b21f04",
  "nextPlayer": "ONE",
  "rowOne": {
    "pits": [
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      }
    ],
    "kalahaStones": 0
  },
  "rowTwo": {
    "pits": [
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      }
    ],
    "kalahaStones": 0
  },
  "gameOver": false,
  "winner": null
}
```



### PUT 
```http request
curl --location 'http://localhost:3000/api/boards/{id}' --header 'Content-Type: application/json'
```
This endpoint receives a play request to execute on boardId. 

#### Request Body
The request body should contain a JSON object with the following fields:

`pitIndex` (int, 0 <= size <= 5): the index of player pit \
`player` (enum): type of player: ONE, TWO

Example:
```json
{
  "pitIndex": "0",
  "player": "ONE"
}
```
##### Response
This endpoint returns a response with HTTP status code 200 (OK) play request successfully processed.\
This endpoint returns a response with HTTP status code 404 (NOT_FOUND) if board not found.\
This endpoint returns a response with HTTP status code 400 (BAD_REQUEST) if the pitIndex is out of range.\
This endpoint returns a response with HTTP status code 400 (BAD_REQUEST) if the pit has no stones.\
This endpoint returns a response with HTTP status code 400 (BAD_REQUEST) if the wrong player order.\
This endpoint returns a response with HTTP status code 400 (BAD_REQUEST) if the game is over.

`boardId` (UUID): the board id \
`nextPlayer` (enum): next player: ONE, TWO \
`rowOne` (RowDetails): object containing pits and kalaha for Player.ONE\
`rowTwo` (RowDetails): object containing pits and kalaha for Player.TWO\
`gameOver` (boolean): state of the end of the game\
`winner` (enum): winner player: ONE, TWO
##### Response models
###### RowDetails
`pits` (Array): keeps pits \
`kalahaStones` (int): number of stones in Kalaha

###### pitDetails
`stones` (int): number  of stones in pit
Example of response:
```json
{
  "boardId": "43ea97d0-b274-4550-8b29-4d14d1b21f04",
  "nextPlayer": "TWO",
  "rowOne": {
    "pits": [
      {
        "stones": 6
      },
      {
        "stones": 0
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      }
    ],
    "kalahaStones": 5
  },
  "rowTwo": {
    "pits": [
      {
        "stones": 7
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      }
    ],
    "kalahaStones": 4
  },
  "gameOver": false,
  "winner": null
}
```


### GET
```http request
curl --location 'http://localhost:3000/api/boards/{id}' --header 'Content-Type: application/json'
```
This endpoint retrieves a board game state by boardId.

##### Response
This endpoint returns a response with HTTP status code 200 (OK) play request successfully processed.
This endpoint returns a response with HTTP status code 404 (NOT_FOUND) if board not found.

`boardId` (UUID): the board id \
`nextPlayer` (enum): next player: ONE, TWO \
`rowOne` (RowDetails): object containing pits and kalaha for Player.ONE\
`rowTwo` (RowDetails): object containing pits and kalaha for Player.TWO\
`gameOver` (boolean): state of the end of the game\
`winner` (enum): winner player: ONE, TWO
##### Response models
###### RowDetails
`pits` (Array): keeps pits \
`kalahaStones` (int): number of stones in Kalaha

###### pitDetails
`stones` (int): number  of stones in pit
Example of response:
```json
{
  "boardId": "43ea97d0-b274-4550-8b29-4d14d1b21f04",
  "nextPlayer": "TWO",
  "rowOne": {
    "pits": [
      {
        "stones": 0
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      },
      {
        "stones": 7
      }
    ],
    "kalahaStones": 1
  },
  "rowTwo": {
    "pits": [
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      },
      {
        "stones": 6
      }
    ],
    "kalahaStones": 0
  },
  "gameOver": false,
  "winner": null
}
```
### Authors
*[Anna Martynova] (https://github.com/Anna87)