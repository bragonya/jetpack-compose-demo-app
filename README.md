# ComposeDemoApp
This project uses Unsplash API to get images, you can save favorite images and see details

![Demo Unsplash](./assets/demoapp.gif)

## Add your Unsplash Key to local.properties
Go to the root of your project and look for local.properties (it should be in your git.ignore), then you should add something like:

```kotlin

UNSPLASH_TOKEN=<TOKEN YOU GET FROM UNSPLASH>

```

To get a token you should register [here](https://unsplash.com/oauth/applications) and create an application.

## What will you find here?
### Functionality
This is the tipical master-detail app over a list of images provided by Unsplas API. Additioinally you can choose favorites (locally) and it will persist. The main purpose of this project is try to be a playground of the new Jetpack Compose for declaratives UIs.

All the screens are hosted by MainActivity.kt and you can navigate throught [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) among the three  screens the app has which are:

* **HomeScreen.kt** you can find the complete list of images here. They are consumed from the Network and it has cache policy to work without Internet, this was implemented using ***Paging 3*** library (Using ***RemoteMediator***), ***Room DataBase***.
* **FavoritesScreen.kt** This is pretty similar to HomeScreen.kt, the difference is the source of data, which is ***Room*** which handles the persistency locally, additionally you can find an implementation of a SearchBar to find specific images.
* **DetailScreen.kt** this is the most complex screen in terms of Jetpack Compose due the ammount of composables it has, you can find an implementation of a sliding ***Toolbar***, ***Shimmer loader***, ***Box composable***. Additionally you can add favorites from there.

## Architecture MVVM
### SharedViewModel
One interesting thing of this project is that just one ViewModel was created to handle all the screens. ***SharedViewModel.kt*** is shared and is the bridge of communication among the screens. It offers a [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) to get updates in case some favorite is modified. Appart from that it is observing changes in the local database created with Room.

### Navigation and Single Screen Application
The orchestrator for navigation is ***UnsplashApp.kt*** you can find there the main structure of the application which is an [Scaffold](https://foso.github.io/Jetpack-Compose-Playground/material/scaffold/) with a [BottomNavigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419#bottom-nav). The BottomNavigation navigates either HomeScreen or FavoritesScreen and inside those screens you can navigate to DetailScreen.

The navigation is managed by [AnimatedNavHost](https://google.github.io/accompanist/navigation-animation/) which is similar to NavHost with the difference that you can create animations everytime you navigate from one screen to another.

### Hilt
Hilt is the new tool to handle dependency injection in Android so it was used to do that. You can find three modules:
* **CoreModule.kt** provides the data bases (created in Room) to ***UnsplasRepository.kt***
* **NetworkModule.kt** provides the REST client which is Retrofit 2 in this case. It also provides AuthInterceptor which sets the auth config to Retrofit.
* **ViewModelsModule.kt** this is scoped with ViewModelComponent and provides the dependencies to ***SharedViewModel.kt*** for this particular case.

## Known issues
* Everytime you go to DetailScreen and you go back the scroll position is lost.
* Liskov principle is not implement 
