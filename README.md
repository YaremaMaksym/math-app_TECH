# math-app_TECH

This project implements a CLI application for calculating the roots of expressions. Users can enter mathematical expressions, calculate their roots, and store the results in a database. The program also validates the correctness of the entered expressions before calculating.

## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)

## Technologies

The project uses the following technologies:

- Java
- Jeval (for evaluating expressions)
- JDBC (for connecting to DB, executing queries and retrieving data)
- PostgreSQL (as the relational database management system)
- Maven (for dependency management)
- Git (for version control)

## Installation

To run the project locally, follow these steps:

1. Clone the repository:

   ```
   git clone https://github.com/YaremaMaksym/math-app_TECH.git
   ```

2. Open the project in your preferred IDE.
    
3. Set up the database:

* Install and configure PostgreSQL on your system.
* Update the DBConnectionManager class with your PostgreSQL credentials.
* Create a new database named math_app.
* Create tables:
```
CREATE TABLE expressions (
    id serial PRIMARY KEY,
    expression text
);

CREATE TABLE roots (
    id serial PRIMARY KEY,
    expression_id integer REFERENCES expressions(id),
    value double precision
);
```

4. Run the application

## Usage

1. Run the Application:
   Start the application by running the main method of the Main class.

3. Enter Expressions and Roots:
  * Choose option "1️⃣ - Enter expression Menu" to access the expression input menu.
  * In the expression input menu, you can:
      * Enter a new expression that you want to enter roots for, the app will check if you have entered the correct expression and save it to the DB, if so.
      * Enter the roots for the current expression, and the app will check if you right, if so it will save root to the DB.
      * Exit to the main menu by selecting option "3️⃣ - Exit to main menu".
  
3. Find Expressions by Roots:
  * Select option "2️⃣ - Find expressions by roots".
  * Enter the roots separated by spaces.
  * The app will display expressions that have the specified roots from DB.

4. Find Expressions with Single Root:
  * Select option "3️⃣ - Find expressions with one root".
  * The app will display expressions that have only one root from DB.
    
5. Exit the Application:
  * Select option "4️⃣ - Exit" to exit the application.
