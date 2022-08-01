Feature: Get all available pets

  Background:
    When the user adds a new pet with "Ralf" name, "Dog", and "available" status
    Then the pet name is "Ralf"
    * the pet is in the inventory "true"

  Scenario: Get available pets
    When the user wants to know all the "available" pets
    Then all the returned pets should be "available"