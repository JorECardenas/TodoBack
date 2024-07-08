# Todo App Backend

A simple backend to manage an application to create, delete and edit Todo's

This app works with the frontend application found here:
[Frontend App](https://github.com/jorgencora/TodoFront/tree/dev)

## Requirements

For running this application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are many ways to run a spring boot application,  the simplest is t run the `main` method in the `com.example.TodoBack.ToDoBackApplication` class.

You can use the `mvn` command from the terminal inside the app folder:
```shell
mvn spring-boot:run
```

## Running tests

There are some tests already configured in this application to make sure the main funcionalities work as expected

To run this tests you have to run the following command from the terminal:
```shell
mvn test
```

## API Reference
These are the main endpoints used in this app

### Get Items

```http request
GET /api/todos
```
| Parameter        | Type       | Description                                                                        | Default |
|:-----------------|:-----------|:-----------------------------------------------------------------------------------|:--------|
| `page`           | `number`   | `Min: 1` <br/>Page of the data you will get                                        | 1       |
| `textFilter`     | `string`   | `Min Lenght: 1 Max Lenght: 120` <br/>All text without this string will be filtered | `""`    |
| `sortBy`         | `string[]` | The values that willbe considered when sorting data                                | `[]`    |
| `priorityOrder`  | `string`   | The order used when ordering by priority                                           | `""`    |
| `dueDateOrder`   | `string`   | The order used when ordering by due date                                           | `""`    |
| `priorityFilter` | `string[]` | The objects with priorities different to the ones in this array will be ommited    | `[]`    |
| `stateFilter`    | `string`   | The objects with a different state than this one will be ommited                   | `""`    |

### Create item
```http request
POST /api/todos
```
| Parameter | Type          | Description                     |
|:----------|:--------------|:--------------------------------|
| `dto`     | `TodoItemDTO` | `Body` <br/>The object you need | 


### Update item

```http request
POST /api/todos/:id
```
| Parameter | Type          | Description                        |
|:----------|:--------------|:-----------------------------------|
| `id`      | `string`      | The id of the object being updated |
| `dto`     | `TodoItemDTO` | `Body` <br/>The object you need    | 


### Delete item

```http request
DELETE /api/todos/:id
```
| Parameter | Type          | Description                        |
|:----------|:--------------|:-----------------------------------|
| `id`      | `string`      | The id of the object being deleted |


### Mark item as done
```http request
GET /api/todos/:id/done
```
| Parameter | Type          | Description                               |
|:----------|:--------------|:------------------------------------------|
| `id`      | `string`      | The id of the object being marked as done |


### Mark item as undone
```http request
GET /api/todos/:id/undone
```
| Parameter | Type          | Description                                 |
|:----------|:--------------|:--------------------------------------------|
| `id`      | `string`      | The id of the object being marked as undone |


### Examples

Here are some examples of the model used here

`TodoItemDTO`
```json
{
  "text": "test",
  "done": false,
  "priority": "High",
  "dueDate": "2025-03-07T00:00:40.699+00:00",
  "doneDate": "2025-03-07T00:00:40.699+00:00"
}
```

`TodoItem`
```json
{
    "id": "f2f1d98f-880f-45f3-8d2d-92af8c0f0717",
    "text": "test",
    "dueDate": "2035-03-07T00:00:40.699+00:00",
    "done": true,
    "doneDate": "2035-04-04T00:00:40.699+00:00",
    "priority": "Medium",
    "creationDate": "2024-07-08T00:21:40.699+00:00"
}
```

`PaginatedTodoDTO`
```json
{
  "content": [
    {
      "id": "16c96dc8-3bc7-42b2-973a-b7b020f241da",
      "text": "test17",
      "dueDate": "2036-02-05T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Low",
      "creationDate": "2024-07-08T04:10:43.638+00:00"
    },
    {
      "id": "3af398f2-30db-4c9d-9e2c-ab6c2ca7d321",
      "text": "test18",
      "dueDate": "2034-06-02T00:00:43.638+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Medium",
      "creationDate": "2024-07-08T04:10:43.638+00:00"
    },
    {
      "id": "8b24a8d4-03ec-4aa0-8bd0-733a9367dc5c",
      "text": "test19",
      "dueDate": "2024-06-02T00:00:43.638+00:00",
      "done": false,
      "doneDate": null,
      "priority": "High",
      "creationDate": "2024-07-08T04:10:43.638+00:00"
    },
    {
      "id": "ce8e0440-bdcf-401b-981f-33e574624dda",
      "text": "test11",
      "dueDate": "2026-04-07T00:00:43.636+00:00",
      "done": false,
      "doneDate": null,
      "priority": "High",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "53f1b666-86dd-4af4-a0cd-9821c6fe7741",
      "text": "test12",
      "dueDate": "2026-03-01T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Medium",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "126e5595-65a7-4b03-9656-d8bcc3fe165b",
      "text": "test13",
      "dueDate": "2032-05-07T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Low",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "8e58637f-2081-4d0f-a616-7644c261656b",
      "text": "test14",
      "dueDate": "2028-02-05T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "High",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "cd42cb2b-e592-450e-9530-82ebcf86d2c8",
      "text": "test15",
      "dueDate": "2032-04-03T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Low",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "260c049c-a76b-40bd-82df-6c5436d09fe3",
      "text": "test16",
      "dueDate": "2033-02-05T00:00:43.637+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Low",
      "creationDate": "2024-07-08T04:10:43.637+00:00"
    },
    {
      "id": "aa2b613b-d9a6-4ac9-b692-a7c20db3f65c",
      "text": "test3",
      "dueDate": "2026-04-01T00:00:43.635+00:00",
      "done": false,
      "doneDate": null,
      "priority": "Low",
      "creationDate": "2024-07-08T04:10:43.636+00:00"
    }
  ],
  "parameters": {
    "textFilter": "",
    "priorityFilter": [],
    "stateFilter": "",
    "sortBy": [],
    "prioritySortOrder": "DESC",
    "dueDateSortOrder": "DESC"
  },
  "averageData": {
    "generalAverage": 0,
    "lowAverage": 0,
    "mediumAverage": 0,
    "highAverage": 0
  },
  "currentPage": 1,
  "itemsInPage": 10,
  "totalPages": 2,
  "totalItems": 20,
  "allDone": false,
  "firstPage": true,
  "lastPage": false
}
```

## Technologies used

The following tools were used to make this app:

- [SpringBoot](https://spring.io/projects/spring-boot)
- [Lombok](https://projectlombok.org/)


## Things to improve

- Add capabilities for sorting using any value not just Priority and Due Date
- Add more thorough tests 

