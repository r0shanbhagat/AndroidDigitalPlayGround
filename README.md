## Android Digital Playground

## Introduction

A Sample project that shows the popular movie list from source feature of 500px which using
the [500px API](https://github.com/500px/legacy-api-documentation) built using modern Android
development strategies focusing on the following key aspects:

- Code structuring as per clean Architecture
- Using MVVM/MVI Pattern as per Google's recommendation
- Android Architecture Components (LiveData, ViewModel, Navigation)
- Kotlin features (Lambdas, Extension functions, typealias, sealed class and Coroutines)

## App Overview

1. Search the Movies by Name </br>
2. Show the List of Suggested Movies </br>
3. Show the Details of Movie by Tapping on that.</br>

<img alt="Search Movie" height="250px" width="120px" src="https://user-images.githubusercontent.com/22414106/168666766-16c718e7-1267-4c00-a7b0-3065f497fc7b.png" > <img alt="Popular Movie List" height="250px" width="120px" src="https://user-images.githubusercontent.com/22414106/168666738-4ba5ddf6-a4ae-4ffd-a4f8-6fff0057ab49.png" > <img alt="Movie Detail" height="250px" width="120px" src="https://user-images.githubusercontent.com/22414106/168666768-cc296ede-395b-477b-9341-f31c07f6d7f9.png" >   

Navigation between the screens has been done using the Jetpack Navigation library and the following
is its nav graph:

## Libraries The App uses libraries and tools used to build Modern Android application, mainly part of Android Jetpack

- [Kotlin](https://kotlinlang.org/) first
- [Clean Architecture](https://pub.dev/documentation/flutter_clean_architecture/latest/)
- [Coroutines Flow API](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Retrofit2](https://square.github.io/retrofit/) for Networking
- [OkHttp3](https://square.github.io/okhttp/) for Networking
- [Glide](https://github.com/bumptech/glide) for image loading
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Navigation Component](https://developer.android.com/guide/navigation/) for App Navigation
- [Android KTX](https://developer.android.com/kotlin/ktx) features
- [MockK](https://mockk.io/) for unit testing

### Scope for Improvements

The app can be further improved with the addition of the following features

- Adding Level 2 implementation
- Build UI tests
- Start blog series