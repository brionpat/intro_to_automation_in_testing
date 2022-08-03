Feature: Ordering of products on Demoblaze e-commerce

  Scenario: Order a product
    Given I visit Demoblaze
    When navigate to category "Phones"
    * navigate to category "Monitors"
    * navigate to category "Laptops"
    * select product "Sony vaio i5"
    * click on "Add to cart"
    * accept pop up "true"
    * navigate to menu "Home"
    * navigate to category "Laptops"
    * select product "Dell i7 8gb"
    * click on "Add to cart"
    * accept pop up "true"
    * navigate to menu "Cart"
    * delete "Dell i7 8gb" from the cart
    * capture total amount from the cart
    * click on "Place Order"
    * fill the fields
      | name     | country | city     | card    | month | year |
      | Patricia | Spain   | Zaragoza | 6666666 | 06    | 2023 |
    * click on "Purchase"
    * capture info from purchase summary
    * log "Id"
    * log "Amount"
    Then amount expected is correct
    * click on "OK"
    * close page