Feature: Bolttech mobile screen insurance

  Scenario: Should list the price ranges of insurance for phone
    Given I access Bolttech in mobile view
    When The price range input loads
    Then It should be possible to retrieve it's options

  Scenario: Should update price by range THB 2,000 - 6,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 2,000 - 6,000"
    Then The price should be "฿39"

  Scenario: Should update price by range THB 6,001 - 10,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 6,001 - 10,000"
    Then The price should be "฿59"

  Scenario: Should update price by range THB 10,001 - 15,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 10,001 - 15,000"
    Then The price should be "฿79"

  Scenario: Should update price by range THB 15,001 - 22,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 15,001 - 22,000"
    Then The price should be "฿139"

  Scenario: Should update price by range THB 22,001 - 26,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 22,001 - 26,000"
    Then The price should be "฿159"

  Scenario: Should update price by range THB 26,001 - 36,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 26,001 - 36,000"
    Then The price should be "฿179"

  Scenario: Should update price by range THB 36,001 - 65,000
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 36,001 - 65,000"
    Then The price should be "฿289"

  Scenario: Should check for details when Selecting a insurance
    Given I access Bolttech in mobile view
    When The price range input loads
    And I select from the price range selector "THB 2,000 - 6,000"
    Then The price should be "฿39"
    Then I click on Select
    When The details page loads
    Then The striked Through Price should be "฿39"
#    And The current price should be "0.00"
    And The product name Should be "Screen Breakage"
    And Provider should be "bolttech"
    And The contract start date should be Today in Thailand Timezone
    And The contract renewal should be "Monthly"
    And The billing start date should be the start date plus 2 months minus 10 days