# OLXpj

This project was made using flickr api to get a set of images randomized based on some categories.

There are two options of first view to show based if you are already user or not 
the categories are showed using gridLayout with the name and a random image that shows the most viewed category bigger than the others.
also the second most viewed is showed a little bit more bigger but not that most viewed, 

then for each selected category is possible to see 10 pics related to that category, in this case you can select one and see the image in big measure. 

the all the click count is used with a db sqlite using the native android SQLiteOpenHelper 

ARCHITECTURE: MVP (MVVVM LIKE)

used only third party libraries:
-retrofit (api rest calls)
-glide (image download async)
-jackson for Jsonparsing (i think is much better than Gson)
