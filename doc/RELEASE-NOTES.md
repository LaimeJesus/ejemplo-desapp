---------------------------------------------------------------------
### TAG 1.1
---------------------------------------------------------------------
### NEW FEATURES :
#### Core :
* Travis Build - DONE (GREEN)
* Using HSQLDB - DONE
* Dummy Data - DONE
* I18n- US_ES - NOT DONE
* Backend Layout Implementation - DONE
* Presentation Tier Implementation - DONE
* TAG - DONE
* RELEASE-NOTES - DONE

#### Model :
* CleanCode according to Codacy

#### Testing
* Integration Test

#### Model Features

##### Backend:

###### Services:
* Added services: user, product, productlist, shop.

##### Rest:

###### UserController:
* Added path /user.
* Added endpoints: /signup, /login y /logout.

###### ProductController:
* Added path /product.
* Added endpoints: /upload y /all.

###### ProductListController:
* Added path /productlist.
* Added endpoints: /create, /selectproduct y /mylists

###### ShopController:
* Added path /shop.
* Added endpoints: /waitingtime y /ready.

###### DummyController:
* Added dummy creator.
* Added path /dummy
* Added endpoints: /example

##### UI:
* Added frontend app using angular.
* URL: https://aloloco-app-grupo-b-frontend.herokuapp.com/
* GIT: https://github.com/yoLUkAsss/grupo-b-012017-frontend

### NOTES :
* We don’t have an admin validation for upload a csv file.
* Deletion are not implemented.

### KNOWN ISSUES :
* When you remove a product from a list, some applied offers related to that product don’t disapply.

---------------------------------------------------------------------
### TAG 1.0
---------------------------------------------------------------------
### NEW FEATURES :

#### Core :
* Repository Created - DONE
* Travis Configuration - DONE
* Build - DONE (GREEN)
* Codacy Configuration - DONE
* Coverage - DONE (92%)
* UML Diagram - DONE
* Mockups - DONE (Lobby/AddProduct)
* GMaps Window - DONE (route between two marks)
* Automatic Deploy - DONE (Heroku)
* Github Tag - DONE ()
* RELEASE-NOTES.txt - DONE

#### Model :
* CleanCode according to Codacy, Issues : 6.

#### Testing
* Unit test

#### Model Features

##### Alerts:
* We can create alerts.
* We can activate and shut down alerts.
* We can check if an alert can be display for a product list or for a
productlist when is trying to add a new product.

##### Offers:
* We have three possible offers: by combination of two products, by category or by quantity of a product.
* We can get the previous and final (also the discount) of the offer’s price.

##### Recommendations:
* We have a scalable strategy to support different methods to create a recommendation.
* We can get recommendations from category of products.

##### Registers:
* We can create cash-registers which are able of simulate the queue for waiting
users when are going to pay its productlist.
* We can close, open or put some filter for a cash-register. A filter just tell if a cash-register accepts a productlist.
* We can get the waiting time for a cash-register.
* We can create registers manager, this can do the following things:
    * get the cash register with the lesser time for a product list.
    * add an user with a productlist to a cash register.
    * change the filter for a cash register(example: close filter, open filter, quantity of products filter, etc)

##### Products:
* We can create products which has a name, a brand, a stock, a price, a category, a processing time and an image-url.
* We can get all products.
* We can create product lists which has a name, applicable offers and products.
* We can add products to a product list.

##### Users:
* We can create users with: an username, an email and a password.
* We can create profiles which has: an address, product lists, a purchase history
and alerts.
* A profile can create a shop list with a name.

##### Util:
* We have our own implementation of Money, which has several useful methods such as: Times, Plus, Minus, Divide, Percentage, etc.
* We can parse a csv file and get the products loaded in that file.

### NOTES :
* We don't have the validation for offers, that just can be created for a marketing manager.
* We still don’t have recommendation given purchase history of product.
* We still don’t have the possibility to deliver a product list.
* Authentication for users.

### KNOWN ISSUES :
* When you remove a product from a list, some applied offers related to that product don’t disapply.
