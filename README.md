
   # PixabayApp
An Android App to search and display list of images, built with MVVM and Clean Architecture.

Single activity, Kotlin-powered reliable Star Wars app.

* Type the name of the character
* See results in the list
* ✨Magic ✨

# Prerequisite

* This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.
* Add your Pixabay API key in the `local.properties` file, with `PIXABAY_API_KEY`. 

# Features

Full Image name-based search

* Shows Image details of the searched character.
* Shows information like Likes, Comments and Downloades.

> Supports devices with Android Version > **20 (Lollipop)** and targets the Android Version **32 (R)**

# Assumptions

* Because a lot of new features can be added to the app in the future, adding those features or changes should be performed easily.
* Data source(s) might get modified in the future and the app design/architecture should be able to adapt to it effortlessly.
* Code should be readable to make collaboration within the team effortless.
* PixabayApp is made while keeping simplicity a priority.

## Before Writing the Code

### Why MVVM, not MVI?

Since MVI is a great option when a lot of user input is involved in the app user interface, the current required features are not heavily user-driven, so driving the presentation layer architecture around MVI would be an overkill. Here only one input from the user, that is, making a search is part of the user interface design, hence MVVM architecture is chosen.

### Why Clean Architecture?

Clean Architecture allows better control over the dependencies flow in an app which leads to a better testable, flexible, and easier-to-follow codebase. As mentioned in the assumptions, new features should be extremely easy to accommodate and the scalability of the app in the future must be immensely adaptable. For example, redesigning app UI or modifying the data source(s) should be a piece of cake, as should be adding a new business requirement.

The third-party libraries needed to build the app had to be identified before starting to write the code.

| **Purpose**                       | **Library Used**  | **Why this Library?**                                                |
| ----------------------------------| ----------------- | ---------------------------------------------------------------------|
| Dependency Injection              | Hilt              | Easy to use DI containers with automatic lifecycle event management  |
| Networking                        | okhttp3           | Easy to use HTTP library                                             |
|                                   | Retrofit          | Easy to use type-safe HTTP client                                    |
| JSON Serialization/Deserialization| Gson              | Works well with Retrofit for handling request & response body        |
| Handling Paginated Data           | Pagination 3      | Provides really efficient paging implementation                      |
| Concurrency                       | Coroutines        | Makes it very easy to do asynchronous programming                    |
| Database                          | Room              | Makes it very easy work with SqLite                                  |
| Testing                           | Mockito           | Provides easy API to create mocks of objects during testing          |
|                                   | JUnit             | Best Java unit testing framework                                     |

## Project Structure

* **Domain**: This is the domain layer. It is responsible for defining all the entities and APIs used by Presentation and the Data layer.

* **Data**: This is the data layer. It is responsible for implementing Domain APIs and interacting with data. It defines its own entities which it uses to interact with the outside world (i.e remote server).

* **Presentation**: This is the _presentation layer_. It is responsible for handling all the UI-related logic and activities/fragments that users can use and interact with.

There are a few more directories:

* **DI**: This is where Hilt modules reside, connected to the different layers of the application.

* **Utils**: Classes and functions which are used in the corresponding layer.

* **Extensions**: Extension functions on the objects which are used in the corresponding layer.

# Screenshots
<p align="center"><img src="/imgs/Screenshot_Search_Screen_Potr.jpg" width="350"/></p>

<p align="center"><img src="/imgs/Screenshot_Search_Screen_Land.jpg" width="350"/></p>

<p align="center"><img src="/imgs/Screenshot_Confirmation_Dialog.jpg" width="350"/></p>

<p align="center"><img src="/imgs/Screenshot_Image_Detail_Screen.jpg" width="350"/></p>
