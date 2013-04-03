Android GeoAuth 1.0

By Christian Gerrard

ID 4034351 (Gerr0041)

The application features 3 main functions: The ability to register your android device with our server, 
the ability to request a unique identifying registration key, and the ability to manually send a location
to our server.

Once you’re in the application you will be shown 2 buttons...”Check In” and “Register Device.”

Check in creates an entry in the server’s database that holds your latitude/longitude coordinates,
date/time, and your device ID. It will take these send it to the server. If the httpPost request is 
successful then your screen will simply display “OK” in the top left corner. If an error has occurred 
the error code and web code will be printed out for your viewing.

Register Device registers a device to a user account. It will do 2 things. First, it sends a 
registration key request to our server with a special ‘api key.’ This key just validates that it
is our program sending the request. The server returns a unique registration key. Register Device
then loads that key into an httpPost along with a username, password, and the name of the device. 
If the entire registration process is a success, then a string of hashed characters and numbers 
will appear at the top left of the screen.

Future additions:

-I want to fix my GPS/LocationServices issues. Currently, the network and gps location providers
on my phone do not allow me to extract latitude/longitudinal coordinates from them. I’ve spent 
about 10 hours solely working on this issue and I currently am waiting to hear back from my developer
friends to see what the issue could be.

-User aesthetics (UI) is needed, but won’t be considered until after the majority of the functionality
is in place. This will include a splash/loading screen, better formatted buttons, editText fields for
user input, pretty colors, etc.

-Be able to add regions (i.e. “Home” “School” “Work”)

Code Structure:

For each activity in my Android application I created an xml layout file and a java class file with
the prefix HttpPost_______.java. The blank portion was named after the method I wanted to accomplish
through that class (i.e. to register a device...it would be named HttpPostRegister.java). These classes
then called method classes that would calculate necessary values and send them via http POST to the server.
These files had the prefix of Post_____Method.java. Finally,  the Android manifest xml file held all 
of the activities that could be started with intents (i.e. a button press).


