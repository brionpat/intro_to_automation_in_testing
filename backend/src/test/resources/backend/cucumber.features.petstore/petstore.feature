Feature: Selling a Pet

  Scenario: Get "available" pets
    When the user wants to know all the "available" pets
    Then all the returned pets should be "available"

  Scenario: User adds a pet
    When the user adds a new pet with "Ralf" name, "Dog", and "available" status
    Then the pet name is "Ralf"
    * the pet exists in the store

  Scenario: User update pet status
    When the user updates the pet status to "sold"
    Then the pet status is "sold"

  Scenario: User deletes pet
    When the user deletes pet
    Then the pet is not in the inventory