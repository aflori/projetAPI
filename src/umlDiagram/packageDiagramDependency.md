```mermaid
classDiagram
    class controller
    class DTO
    class exception
    class model
    class repository
    class security
    class service
    class util

    controller --> exception
    controller --> DTO
    controller --> service

    service --> DTO
    service --> exception
    service --> repository
    service --> model
    service --> security
    service --> util

    DTO --> exception
    DTO --> model

    repository --> model
    
    

```