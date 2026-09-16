# Problem Statement — Vehicle Rental System

## Problem Statement

Vehicle rental agencies need a reliable way to track which vehicles they own, which
ones are currently available, who has rented them, and how much each rental costs.
Doing this manually — on paper or in scattered spreadsheets — is slow and error-prone:
it's easy to double-book a vehicle that's already rented out, lose track of who has
returned what, or make mistakes calculating rent for different vehicle types that each
have their own pricing rules (a car isn't priced the same way as a truck carrying cargo).

This project addresses that problem by building a small, self-contained software system
that manages a vehicle fleet, its customers, and its rental transactions in one place.
The system enforces the business rules automatically — a vehicle cannot be rented twice
at once, a vehicle that was never rented cannot be "returned," and rental cost is
calculated consistently based on vehicle type and duration — removing the risk of manual
error.

Beyond the practical use case, the project also serves an academic purpose: it is
designed as a learning exercise to demonstrate the core principles of Object-Oriented
Programming (encapsulation, inheritance, abstraction, polymorphism, interfaces, and
exception handling) through a realistic, working application rather than isolated
textbook examples.

## Scope of the Project

**In scope:**

- Managing a fleet of three vehicle types — Car, Bike, and Truck — each with its own
  attributes and its own rental pricing rule
- Registering and storing customer details
- Renting a vehicle to a customer for a specified number of days
- Returning a rented vehicle and closing out the rental
- Searching the fleet by ID, by brand and model, or by maximum daily rate
- Calculating rental cost in advance, with an optional discount
- Viewing all vehicles, all customers, and the full rental history from the console
- Validating user input and handling error conditions gracefully (duplicate IDs,
  invalid IDs, renting an already-rented vehicle, returning a non-rented vehicle,
  invalid numeric input, etc.)

**Out of scope (not implemented in this version):**

- Persistent storage — all data is held in memory (`ArrayList`) and is lost when the
  program exits; there is no file, database, or cloud storage layer
- A graphical user interface or web interface — the application is console-only
- User authentication, login, or role-based access (e.g. admin vs. staff accounts)
- Payment processing or integration with real payment gateways
- Real-time fleet tracking (GPS/location), insurance handling, or damage reporting
- Multi-branch or multi-location support
- Networking, APIs, or multi-user concurrent access

These exclusions keep the project focused and appropriately sized for a second-year
college OOP assignment, while leaving clear directions for future extension (see the
README's "Future Enhancements" section).

## Target Users

- **Students and instructors** evaluating the project as an academic OOP assignment —
  the primary audience for this particular build.
- **Small rental-shop operators or staff** who need a lightweight tool to track vehicle
  availability and rentals without investing in commercial rental-management software.
- **Developers/learners** studying Java OOP who want a complete, runnable reference
  example that ties abstraction, inheritance, polymorphism, interfaces, and exception
  handling together in one coherent codebase, rather than disconnected snippets.

## High-Level Features

1. **Add Vehicle** — register a new Car, Bike, or Truck with its type-specific details
   (seats/AC, engine capacity, or load capacity).
2. **Display Vehicles** — view the entire fleet, or filter to only currently available
   vehicles.
3. **Search Vehicle** — look up vehicles by ID, by brand and model, or by a maximum
   daily rental rate.
4. **Register Customer** — capture customer ID, name, phone number, and driving
   licence number.
5. **Rent Vehicle** — assign an available vehicle to a registered customer for a given
   number of days and automatically calculate the total cost.
6. **Return Vehicle** — close out an active rental and mark the vehicle available again.
7. **Calculate Rental Cost** — get a cost estimate before committing to a rental,
   optionally applying a discount percentage.
8. **Display Customer Details** — view all registered customers in a table.
9. **Display Rental Details** — view the full rental history, including which rentals
   are still ongoing and which have been returned.
10. **Robust error handling** — every user-facing operation validates its input and
    reports problems (duplicate IDs, missing records, invalid choices, bad numeric
    input) through clear messages instead of crashing the program.
