Feature: Automation Exercise user journey

  Scenario: Register user and purchase a women dress product
    Given I open the Automation Exercise home page
    When I register a new user
    And I navigate to Women category and Dress subcategory
    And I select the second product above configured threshold sorted ascending
    And I add the selected product to cart
    And I complete checkout and payment
    Then the order should be placed successfully