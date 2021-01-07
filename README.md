# repolist
Fetching user information and repositories list using Github REST API

Implementation details:

1. Fetching information from github server
  * Setup retrofit and okhttp dependencies 
  * Generate data classes from JSON responses, clean them up to keep only what was needed in the UI
  * Retrofit now supports coroutines, so it is a great opportunity to exercise the use of coroutines 
  and flows (especialy when it comes to testing, it can get tricky).
  
2. Overal project structure
   * Using MVVM approach with the Repository pattern, I created a modelview class, repository class 
   and service class (communication with the API, our data source).
   * No local data source (DB) was implemented for this version.
 
   The search feature assumes that the username in the EditText field is valid, no specific
   error handling was implemented to deal with invalid user names.
   
3. Used Glide for loading the user avatar

4. Unit Testing
  
   Testing with coroutines can get tricky due to the async nature of the network calls (there may
   be dispatchers/scope confusion), so I used kotlinx-coroutines-test to get help.
   Assertions with flows are also kind of difficult without help functions, so I used turbine for 
   that and kotest for the matchers (test runner is still JUnit, kotest seemed to large for this project)
   
5. Instrumentation Tests

   Mocking with flow and coroutines is a little challenging, so not a lot was done so far. Still 
   a work in progress.
   Because the aim of those tests are just to check the UI, my first try is to create a 
   custom test runner and a test application to get fake data to test the UI
