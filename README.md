<h1>Example Compose Multiplatform (Android + iOS) app with everything you need for modern development</h1>
This is an example Compose Multiplatform/Kotlin Multiplatform mobile app that includes all the bits and pieces required for modern development:

- Shared Compose-based UI
- Reactive UI with Kotlin flows as data sources
- Compose-based animated visual transitions between screens
- Centralized UI styling, including custom fonts
- Dependency injection (Koin)
- Network communication (Ktorfit)
- Database storage (SqlDelight)
- Key-value storage for things like user preferences (multiplatform-settings)
- Single source of truth with remote-local data synchronisation (Store5)
- Remote images (Coil)
- MVVM/MVI with view models (Orbit MVI)
- Shared diagnostic logging (Napier)

It works reliably on both Android and iOS (other platforms are out of scope of this project).

You can use it as a template for kick-starting a new mobile app.

<h2>Functional description</h2>

The app features artworks by Calude Monet sourced from the Art Institute of Chicago's API. Artwork information received from network is stored locally in an SQL database (SqlDelight library), and images themselves are cached on disk and in memory (Coil library), both as thumbnails and header images. 

home screen features a grid of 100 square artwork tiles with images and one-line titles (ellipsized if needed). Tapping a tile on the home screen brings a user to the artwork details screen featuring a larger image, title, author, date, type, and description. Both home and the details screens include favorite icons that enable the user to add an artwork to their favorites. Favorites screen is invoked by tapping the top-right icon on the home screen. You can un-favorite artworks there, and the list gets updated with a nice animation.

Going between screens includes transitions that animate images and titles.

<p float="left">
  <img src="https://github.com/user-attachments/assets/bb02c81b-bb38-438a-a461-81f70c60b461" width="200"/>
  &nbsp; &nbsp; &nbsp; &nbsp;
  <img src="https://github.com/user-attachments/assets/bf3a6e91-ab72-45ce-aa06-77d7d4a585d2" width="200"/>
  &nbsp; &nbsp; &nbsp; &nbsp;
  <img src="https://github.com/user-attachments/assets/7ff8f2e2-2bec-40e1-89ad-23e2e8d8f5c5" width="200"/>
</p>
