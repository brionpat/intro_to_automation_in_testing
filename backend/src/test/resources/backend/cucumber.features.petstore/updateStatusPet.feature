Feature: Update status of a Pet

  Background:
    When the user adds a new pet with "Ralf" name, "Dog", and "available" status
    Then the pet name is "Ralf"
    * the pet is in the inventory "true"

  Scenario: User update pet status
    When the user updates the pet status to "sold"
    Then the pet status is "sold"