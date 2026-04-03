# 🧪 AutomationExercise Test Automation Framework (Katalon)

## 📌 Overview

This project is a **test automation framework built with Katalon Studio** to validate UI and API functionality of https://automationexercise.com.

The framework is designed with:

* scalability
* maintainability
* environment flexibility
* data-driven testing

---

## 🏗️ Architecture

```
Keywords/
  config/
    Env.groovy
    ConfigLoader.groovy
  pages/
  utils/
    TestDataFactory.groovy
    TestDataStructured.groovy
    FakerUtils.groovy

Object Repository/
Test Cases/
Test Suites/
Test Listeners/
Profiles/
Include/config/
```

---

## ⚙️ Key Features

### ✅ 1. Environment-based execution (Profiles)

The framework supports multiple environments:

* `default`
* `test`
* `uat`

Each profile defines:

* base URLs
* credentials
* flags (e.g. `useTestDataFile`)

Example:

```
GlobalVariable.baseUrl
GlobalVariable.apiBaseUrl
GlobalVariable.useTestDataFile
```

Helper class:

```groovy
Env.isTest()
Env.isUat()
Env.envName()
```

---

### ✅ 2. Config-driven execution (JSON)

Config file located at:

```
Include/config/config.json
```

Example:

```json
{
	"testComplexConfig": {
		"selectionRule": {
			"category": "Women",
			"subcategory": "Dress",
			"priceThreshold": 500,
			"itepPosition": 2
		}
	}
}
```

Loaded via TestListener before each test - might be loaded before each test suite:

```groovy
def configLoader = new ConfigLoader("Include/config/config.json")		
GlobalVariable.testConfig = configLoader.load()
```

---

### ✅ 3. Dynamic Test Data (CSV / Faker) specified by the environment

Controlled by profile variable:

```
useTestDataFile = true / false
```

#### If TRUE:

* Data is loaded from:

  * `TestData/Users.csv`
  * `TestData/UsersTest.csv`
  * `TestData/UsersUat.csv`

#### If FALSE:

* Data is generated using `FakerUtils`

---

### ✅ 4. Structured Test Data Model

```groovy
class TestDataStructured {
    String name
    String email
    String password
    ...
}
```

Used across:

* UI tests
* API tests

---

### ✅ 5. Centralized Test Data Factory

```groovy
new TestDataFactory().getUser()
```

Automatically decides:

* CSV vs Faker
* environment-specific dataset

---

### ✅ 6. Test Hooks (Test Listener)

User is initialized before each test:

```groovy
@BeforeTestCase
def initializeTestData() {
...
    GlobalVariable.testUser = new TestDataFactory().getUser(1)
...
}
```

---

### ✅ 7. Parameterized Test Objects

Dynamic values injected into XPath:

```xpath
//a[contains(text(),'${category}')]
```

Usage:

```groovy
findTestObject('obj', [('category'): notes.category])
```

---

### ✅ 8. Self-Healing Locators

Enabled via:

```
Project → Settings → Self-Healing
```

Since UI supports only single locator, fallback locators are added via XML:

```xml
<selectorCollection>
   <entry>
      <key>CSS</key>
      <value>button[data-qa='signup-button']</value>
   </entry>
   <entry>
      <key>XPATH</key>
      <value>//button[@data-qa='signup-button']</value>
   </entry>
</selectorCollection>
```

---

### ✅ 9. Handling Popups & Ads

Framework includes utilities to handle:

* consent popups
* Google Ads overlays (like `aswift_*` iframes)

---

### ✅ 10. Robust Interaction Layer

Custom utilities:

* safe click
* wait handling
* scrolling

---

## 🥒 BDD Support (Cucumber)

The framework supports **BDD testing using Cucumber**, allowing scenarios to be written in Gherkin format.

### 📁 Structure

Include/features/ → Feature files
Include/scripts/groovy/ → Step Definitions
Test Cases/BDD/ → Test cases with BDD runner

---

## 🚀 Running Tests

### ▶ From Katalon Studio

1. Select Test Suite
2. Choose profile (`default / test / uat`)
3. Click **Run**

---


### ▶️ Running BDD Tests

1. Select Test Case
2. Choose profile (`default / test / uat`)
3. Click **Run**


## 📊 Reporting

Reports generated in:

```
Reports/<TestSuite>/<timestamp>/
```
on TestSuite run

I am also going to commit to the repository one Report folder to show self-healing report there

Includes:

* execution logs
* screenshots
* HTML report

---

## 🧪 API Testing

API requests are:

* parameterized
* environment-driven

Example:

```
POST ${GlobalVariable.apiBaseUrl}/searchProduct
```

Body:

```
search_product=jean
```

---

## 🧠 Design Principles

* DRY (no duplication)
* separation of concerns
* reusable components
* environment abstraction
* data-driven testing
* Cucumber integration

---

## ⚠️ Known Limitations

* This version of Katalon UI supports only one locator → multi-locator via XML (at least I was not able to detect that :) )
* Ads/overlays require manual handling

---

