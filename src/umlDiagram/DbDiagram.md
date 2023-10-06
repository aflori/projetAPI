```mermaid
classDiagram
    class Pool
    class Candidate
    class Role
    class Group
    class Observer
    class Category
    class Criteria

    Pool "n" *-- "1" Group
    Pool "n" o-- "n" Candidate
    Pool "n" o-- "n" Observer
    Observer "n" o-- "n" Role
    Candidate "n" --o "n" Group
    Category "n" *-- "1" Criteria
    Pool "n" o-- "n" Criteria

    Pool: Integer id
    Pool: String location
    Pool: String name
    Pool: Timestamp dateStart
    Pool: Timestamp dateEnd

    Observer: Integer id
    Observer: String lastName
    Observer: String firstName
    Observer: String email
    Observer: String password
    
    Category: Integer id
    Category: String name

    Candidate: Integer id
    Candidate: String lastName
    Candidate: String firstName
    Candidate: String photoName
    
    Role: Integer id
    Role: String name
    
    Group: Integer id
    Group: String name

    Criteria: Integer id
    Criteria: String name
    Criteria: String description
```