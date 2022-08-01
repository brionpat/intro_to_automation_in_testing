Feature: Selling a Pet

  Background:
    When the user adds a new pet with "Ralf" name, "Dog", and "sold" status
    Then the pet name is "Ralf"
    * the pet is in the inventory "true"

  Scenario: User deletes pet
    Given the pet status is "sold"
    When the user deletes pet
    Then the pet is in the inventory "false"