# Movie App

This is a project that builds an app that displays a list of movies and their detail when clicking on it.

**Application**

Polished UI, with thought put into the user experience.

Android app which make use of themoviedb API - https://www.themoviedb.org/  to get movies and display the information accordingly.

**Separation of Concerns**

The Project Follows MVVM Architecture Pattern in decoupling the logic from the Activity to a ViewModel Class that extends ViewModel.


Design pattern used - MVVM (Model-View-ViewModel), ViewModel, Repository pattern, and Android Recommended App Architecture


This App uses following TechStack : 

-- Kotlin Library
-- Room Database
-- Dagger Hilt
-- Recyclerviews
-- Android Architechure Component(ViewModel and LiveData)
-- Retrofit and Flow
-- JUnit4 Library For Testing


## Project file

data folder contain everything relating to the data which includes the database, Model, network calls via retrofit class to make request to the server

view folder contains activity and their adapter- to hold ui

viewmodels folder contain MainViewModel to give any activity that want to observe changes in life cycle.

util folder contains the NetworkBoundResource, Resource, and Extention Files

di folder contains the AppModule for Dependency injection of the various component



## App Demo / Video
https://user-images.githubusercontent.com/43482405/119821419-70dba680-beea-11eb-9649-22a2d1978d68.mp4



**App Images**

![appimae two](https://user-images.githubusercontent.com/43482405/119821426-746f2d80-beea-11eb-98fb-5aa093e5af48.jpeg)
![appImage one](https://user-images.githubusercontent.com/43482405/119821437-7933e180-beea-11eb-815b-1f584ae5b0e5.jpeg)

