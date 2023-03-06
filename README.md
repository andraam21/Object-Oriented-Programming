Maslaev Andra, 323CD, ACS - UPB

# OOP TV 

## General Aspects
This project implements a platform on which a user can watch movies.
The following structure describes the way this site works:



<div align="center"><img src="Screenshot (293).png" width="500px"></div>


There is a `singleton` design pattern created for every page of the site.
Also, two `interfaces` : OnPage (purple) and ChangePage (blue) describe the actions 
that are assigned to a page. In the second part of the project Back (red) and Database
(green) were added, in order to switch to the previous page and to add/delete
movies from the database of the site.
On the page Upgrades the user can buy a premium account or purchase/
watch/ rate a movie. These actions are found in User, because they refer to
the person that is using the platform.
On the Movies page we can search/ filter/ sort the list of the current movies.
For sorting `streams` are used (and `lambda` functions in order to increment the value of 
likes in the `hashmap` in which the subscriptions are stored), taking into consideration all the cases in
which the user can give one or multiple parameters.


## Implementation

In this project are used the following:
### `actions` 
-> ***ChangePage*** (interface with the function that changes the page)\
-> ***OnPage*** (interface that implements the actions
specific to the current page) \
-> ***Database*** (extern action - add or delete movies) \
-> ***Back*** (returns to the previous page)

### `database` 
-> ***User*** (the information about the user is stored + the function that allow the user
to buy a premium account, purchase/watch/rate a movie, subscribe to a certain genre of movies) \
-> ***Movie*** (all the information about the movies is stored) \
-> ***Page*** (the current user
and the current list of movies + the current page that is kept in order
to verify if an operation is possible) \
-> ***Notification*** (notify the client and gives recommendations based on their subscriptions)

### `fileio` 
-> ***Input*** (and all the classes for the information found
in the input) \
-> ***PrintOutput*** (a singleton that has two methods that print the 
result in case of error or in case the operation was done successfully)
### `startpage` 
-> ***HomeAut*** (singleton, verify if we can change the page)\
-> ***Movies*** (singleton, verify if we can change the page)\
-> ***Filter*** (singleton, filer the movies by some parameters, streams are
used in order to sort the list)\
-> ***Search*** (singleton, function in order to find a certain movie
based on how its name starts\
-> ***Details*** (singleton, function that shows the details of a movie) \
-> ***Upgrades*** (singleton, change to the page upgrades; according to what action
is called, the user can buy/watch/rate a movie -> these methods are found in class User)\
-> ***Logout*** (singleton, change page to log out and resets the data) \
-> ***HomeNeat*** (singleton, waits for a user to authenticate) \
-> ***Login*** (singleton, change the page and stores the user data) \
-> ***Register*** (singleton, verifies if a user is already in the database;
if not, (s)he is registered and automatically connected to the site)

