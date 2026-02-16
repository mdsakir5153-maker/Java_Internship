# Expense Tracker - Java Console Application

A comprehensive expense tracking system built with Java and MySQL that helps you manage and monitor your personal expenses with category-based organization.

## Features

- **Category Management**: Create and manage expense categories
- **Expense Tracking**: Add, view, and delete expenses with detailed information
- **Date-based Filtering**: View expenses by date range
- **Category-wise Reports**: Filter expenses by category
- **Database Integration**: MySQL database for persistent data storage
- **Environment Configuration**: Secure credential management using `.env` files
- **Console Interface**: Easy-to-use menu-driven command-line interface

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J library (included in `lib/`)
- dotenv-java library (included in `lib/`)

## Setup Instructions

### 1. Database Setup

Create the database and tables by running the SQL script:

```bash
mysql -u root -p < database.sql
```

Or manually execute the SQL commands from `database.sql` in your MySQL client.

### 2. Environment Configuration

The `.env` file is already configured with the following settings:

```env
DB_URL=jdbc:mysql://localhost:3306/expense_tracker
DB_USER=root
DB_PASSWORD=SAKIR@123
```

**Note**: Update the `.env` file if your MySQL credentials are different.

### 3. Compile the Project

```bash
javac -d bin -cp "lib/*" src/com/expansetracker/*.java
```

### 4. Run the Application

```bash
java -cp "bin;lib/*" com.expansetracker.Main
```

## Usage

### Main Menu Options

1. **Add Expense**: Record a new expense with category, amount, description, and date
2. **View All Expenses**: Display all recorded expenses
3. **View Expenses by Category**: Filter expenses by specific category
4. **View Expenses by Date Range**: Filter expenses within a date range
5. **Delete Expense**: Remove an expense by ID
6. **Manage Categories**: Add or view expense categories
7. **Exit**: Close the application

### Sample Workflow

1. Start the application
2. Choose option `6` to view existing categories or add new ones
3. Choose option `1` to add a new expense
4. Enter category ID, amount, description, and date
5. Use option `2` to view all expenses
6. Use option `3` or `4` to filter expenses by category or date

## Database Schema

### Categories Table
- `id` (INT, Primary Key, Auto Increment)
- `name` (VARCHAR, Unique)
- `description` (TEXT)
- `created_at` (TIMESTAMP)

### Expenses Table
- `id` (INT, Primary Key, Auto Increment)
- `category_id` (INT, Foreign Key)
- `amount` (DECIMAL)
- `description` (TEXT)
- `expense_date` (DATE)
- `created_at` (TIMESTAMP)

## Project Structure

```
Expense/
├── src/
│   └── com/
│       └── expansetracker/
│           ├── Main.java           # Entry point and UI
│           ├── DBConnection.java   # Database connection manager
│           ├── Expense.java        # Expense model class
│           ├── ExpenseDAO.java     # Expense data operations
│           └── CategoryDAO.java    # Category data operations
├── lib/
│   ├── mysql-connector-j-9.5.0.jar
│   └── dotenv-java-3.0.2.jar
├── bin/                            # Compiled classes
├── .env                            # Environment variables
├── .env.example                    # Example environment file
├── database.sql                    # Database schema
└── README.md                       # This file
```

## Technologies Used

- **Java**: Core programming language
- **MySQL**: Relational database
- **JDBC**: Database connectivity
- **dotenv-java**: Environment variable management

## Troubleshooting

### Connection Issues
- Verify MySQL server is running
- Check credentials in `.env` file
- Ensure database `expense_tracker` exists

### Compilation Errors
- Verify JDK is installed: `java -version`
- Ensure all JAR files are in `lib/` directory
- Check classpath in compile command

### Runtime Errors
- Ensure database schema is created
- Verify `.env` file exists and has correct values
- Check MySQL user has proper permissions

## License

This project is for educational purposes.
