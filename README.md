# Bus route application


https://github.com/azentner1/BusLeft/assets/42001876/c0e972da-6e4e-4ad2-a9f9-ec40f43c9603


### Stack:
    - Kotlin
    - MVVM
    - Compose
    - Retrofit

### Architecture 
  Split into: `repository` -> `view model` -> `ui` layers. `data source` layer can optionally be added as first in the downstream flow but i have not added it for simplicity.
  `feature` folder is split per feature/screen. 
  `feature` is further structured into these packages:
     `mapper` - maps remote data to domain data and viceversa
     `repository` - handles remote calls, mapping and flowing data to the `viewmodel`
     `viewmodel` - translates domain data into ui state
     `ui` - consumes the ui state

  Single module, splitting into multiple modules doesn't make sense for this. However, if required, package structure is ready for modularization

### Implementation 

  Simple REST consumer, follow MVVM pattern with Flow API used for moving data between layers. 
  Mapping is achieved using Mapbox SDK, Routing using Mapbox Services SDK.
  Utilizes a pattern for Compose Navigation
  Design - award winning
  
### Tests
  Contains Compose UI test of app navigation flow - currently not used as it requires targetSdk = 34 & APG = 8
  Simple mapper unit test

### How to run
  Clone the project, set Java version to 11 and you should be good to go.
