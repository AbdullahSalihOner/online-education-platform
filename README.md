# Online Education Platform

This project is a Spring Boot application designed to serve as a backend for an online education platform. The application provides features for user authentication, role-based access control, and other core functionalities required in an educational platform.

## Table of Contents

1. [Technologies Used](#technologies-used)
2. [Project Structure](#project-structure)
3. [Getting Started](#getting-started)
4. [Project Steps](#project-steps)

---

## Technologies Used

- **Spring Boot**: Provides the foundation for building the application with features like dependency injection, auto-configuration, and more.
- **Spring Security**: Manages authentication and authorization in the application.
- **JWT (JSON Web Token)**: Used for secure token-based authentication.
- **JPA (Java Persistence API)**: Manages database interactions in an object-oriented way.
- **Redis**: Used for caching.
- **Lombok**: Reduces boilerplate code by generating getters, setters, builders, etc.
- **Swagger (Springdoc OpenAPI)**: Provides API documentation and a user interface to interact with API endpoints.
- **MapStruct**: Used for mapping between DTOs and entities.
- **H2, MySQL, PostgreSQL, MongoDB**: Supported databases for flexibility in deployment environments.

---

## Project Structure

The project follows a layered architecture to separate different functionalities, making it easier to maintain and extend.

- `config`: Configuration classes such as security and application settings.
- `constant`: Stores application constants.
- `controller`: Contains REST API controllers to handle HTTP requests.
- `dto`: Data Transfer Objects used to transfer data between layers.
- `exception`: Custom exceptions and global error handling.
- `mapper`: Maps entities to DTOs and vice versa.
- `model`: Entity classes representing database tables.
- `repository`: Data access layer using Spring Data JPA.
- `result`: Standardized API response structures.
- `security`: Security-related configurations and filters.
- `service`: Business logic layer for core functionalities.

---

## Getting Started

---

## Project Steps

# Step 1: Initial Setup

1. **Dependencies**:
    - Added essential Spring Boot dependencies, such as `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, etc., in `pom.xml`.
    - Configured the application to use MySQL as the main database and included optional configurations for PostgreSQL, MongoDB, and H2.

2. **Application Properties**:
    - Set server port to `8090`.
    - Configured `application.properties` for database connection, JWT settings, and Redis.



# Step 2: Project Structure Setup

1. **Layered Structure**:
    - Created directories for `config`, `constant`, `controller`, `dto`, `exception`, `mapper`, `model`, `repository`, `result`, `security`, and `service` for a modular architecture.


# Step 3: Base Entity Creation

1. **BaseEntity Class**:
    - Created `BaseEntity` with common fields: `id`, `createdAt`, `updatedAt`, and `isDeleted`.
    - Added `@MappedSuperclass`, `@EntityListeners(AuditingEntityListener.class)`, and other annotations to support auditing and soft delete functionality.



# Step 4: Role and User Entities

1. **Role and ERole**:
    - Defined `ERole` enum with roles like `ROLE_ADMIN`, `ROLE_STUDENT`, `ROLE_TEACHER`, and `ROLE_PARENT`.
    - Created `Role` entity with fields `id` and `name`.

2. **User Entity**:
    - Defined `User` entity extending `BaseEntity`, with fields for `username`, `email`, `password`, `profile_image`, and a many-to-many relationship with `Role`.


# Step 5: Repositories

1. **UserRepository**:
    - Added methods to check existence by username/email and find by username.

2. **RoleRepository**:
    - Added method to find a role by name.

# Step 6: Implementing Security with JWT Authentication

In this step, we implemented JWT (JSON Web Token) authentication using Spring Security to secure the application. This configuration allows user authentication and authorization using JWT, enabling stateless session management and efficient role-based access control.

## Overview of Changes

1. **JWT Utility Class (`JwtUtils`)**:
   - This class handles generating, parsing, and validating JWT tokens. It includes methods to:
      - **Generate JWT** based on username.
      - **Extract the username** from a token.
      - **Validate JWT** to ensure it is correctly formatted, not expired, and signed with the correct secret.
      - **Generate and clean JWT cookies** to store and manage tokens in the user's browser.

2. **Authentication Entry Point (`AuthEntryPointJwt`)**:
   - Implements `AuthenticationEntryPoint` to handle unauthorized access attempts.
   - Logs unauthorized access and responds with a JSON object containing an error message, HTTP status, and the requested path.

3. **JWT Authentication Filter (`AuthTokenFilter`)**:
   - Extends `OncePerRequestFilter` to ensure that each request is processed only once.
   - Checks if a valid JWT token is present in the request, validates it, and if valid, sets the authentication in the `SecurityContextHolder`.
   - Extracts the token from cookies and sets user authentication based on token validity.

4. **UserDetails Implementation (`UserDetailsImpl`)**:
   - Implements `UserDetails` to represent the authenticated user and includes necessary information like username, password, and roles.
   - Contains a `build` method to create a `UserDetailsImpl` object based on a `User` entity, mapping user roles to Spring Security's `GrantedAuthority`.

5. **UserDetails Service Implementation (`UserDetailsServiceImpl`)**:
   - Implements `UserDetailsService` to load user details by username.
   - Fetches user information from the database and maps it to a `UserDetailsImpl` instance for authentication purposes.

6. **Security Configuration (`WebSecurityConfig`)**:
   - Configures Spring Security to work with JWT, disabling session-based authentication and enabling stateless session management.
   - Defines a `SecurityFilterChain` with:
      - CSRF protection disabled.
      - Custom `AuthEntryPointJwt` for unauthorized access handling.
      - URL permissions, allowing public access to `/api/auth/**` and `/api/test/**` while securing other endpoints.
      - JWT authentication filter (`AuthTokenFilter`) added to the security filter chain.
      - Password encoder (`BCryptPasswordEncoder`) to securely hash passwords.

---

## Code Breakdown

### 1. `JwtUtils` Class

This utility class performs JWT operations:
- **Key Methods**:
   - `generateTokenFromUsername(String username)`: Generates a new JWT for the given username.
   - `validateJwtToken(String authToken)`: Validates the provided token, handling various exceptions.
   - `getUserNameFromJwtToken(String token)`: Extracts the username from the JWT.
   - `generateJwtCookie(UserDetailsImpl userPrincipal)`: Creates a secure JWT cookie for storing the token in the client's browser.
   - `getCleanJwtCookie()`: Returns a cookie with a null value to clear the JWT.

### 2. `AuthEntryPointJwt` Class

- This component handles authentication errors by responding with a JSON error message when a user attempts to access a resource without authorization.

### 3. `AuthTokenFilter` Class

- This filter intercepts requests to check for a valid JWT token:
   - Parses the JWT from cookies.
   - Validates the JWT and sets the authentication context if valid.

### 4. `UserDetailsImpl` and `UserDetailsServiceImpl` Classes

- `UserDetailsImpl`: Implements `UserDetails` to represent a user with roles, password, and account details.
- `UserDetailsServiceImpl`: Loads user data by username and maps it to `UserDetailsImpl`.

### 5. `WebSecurityConfig` Class

- Configures Spring Security settings:
   - Disables default HTTP sessions and enables stateless JWT-based authentication.
   - Defines URL permissions and adds the custom JWT filter (`AuthTokenFilter`).

---

# Step 7: Implementing DTOs for Authentication and User Information

In this step, we created Data Transfer Objects (DTOs) to facilitate the exchange of data between the client and server for authentication and user registration. These DTOs provide a structured way to handle login requests, user sign-up, and responses with meaningful data.

## Overview of DTOs Created

1. **LoginRequest**:
    - A DTO used for handling login requests. Contains fields for `username` and `password`, both of which are required.
    - **Annotations Used**:
        - `@Getter` and `@Setter` from Lombok for automatic generation of getter and setter methods.
        - `@NotBlank` to ensure the fields are not empty.

2. **SignupRequest**:
    - A DTO used for handling user registration requests. Contains fields for `username`, `email`, `password`, and `role`.
    - **Fields**:
        - `username`: Must be between 3 and 100 characters.
        - `email`: Must be a valid email format.
        - `password`: Must be between 6 and 40 characters.
        - `role`: A set of roles (e.g., `ROLE_ADMIN`, `ROLE_STUDENT`) assigned to the user.
    - **Annotations Used**:
        - `@Getter` and `@Setter` from Lombok.
        - `@NotBlank` for mandatory fields.
        - `@Size` for minimum and maximum character length validation.
        - `@Email` for validating email format.

3. **MessageResponse**:
    - A simple DTO used to send a response message to the client, typically after an operation like registration or login.
    - Contains a single field:
        - `message`: Holds the response message.
    - **Annotations Used**:
        - `@Data` from Lombok, which generates getter, setter, `toString`, `equals`, and `hashCode` methods for the class.

4. **UserInfoResponse**:
    - A DTO used to send user information back to the client, typically after a successful login.
    - **Fields**:
        - `id`: The unique identifier of the user.
        - `username`: The username of the user.
        - `email`: The email address of the user.
        - `roles`: A list of roles assigned to the user.
    - **Constructor and Methods**:
        - A parameterized constructor to initialize all fields.
        - Custom setter methods for each field.
    - **Annotations Used**:
        - `@Getter` from Lombok to automatically generate getter methods.

---

## Code Breakdown

### 1. `LoginRequest` Class

- **Purpose**: Used for receiving login data from the client.
- **Fields**:
    - `username`: The username entered by the user.
    - `password`: The password entered by the user.
- **Validation**:
    - `@NotBlank` on both fields to ensure they are provided in the request.

### 2. `SignupRequest` Class

- **Purpose**: Used for receiving new user registration data.
- **Fields**:
    - `username`: Username for the new user. Validated to be between 3 and 100 characters.
    - `email`: User’s email address. Validated to ensure proper email format.
    - `password`: User’s password, with a length constraint between 6 and 40 characters.
    - `role`: Set of roles for the user, allowing role-based access control.
- **Validation**:
    - `@NotBlank` on required fields (`username`, `email`, and `password`).
    - `@Size` to ensure `username` and `password` meet minimum and maximum length requirements.
    - `@Email` on `email` to validate the email format.

### 3. `MessageResponse` Class

- **Purpose**: Used to send a response message back to the client.
- **Field**:
    - `message`: Holds the text of the response message, which could indicate success or failure.
- **Annotations**:
    - `@Data` generates all necessary boilerplate code, making it a concise DTO for simple responses.

### 4. `UserInfoResponse` Class

- **Purpose**: Used to send user details back to the client upon successful login.
- **Fields**:
    - `id`: The unique identifier of the user.
    - `username`: The user’s username.
    - `email`: The user’s email address.
    - `roles`: A list of the user’s assigned roles.
- **Constructor**:
    - A parameterized constructor for initializing all fields at once.
- **Custom Setter Methods**:
    - Although `@Getter` is used, setter methods are manually defined to ensure controlled setting of each field.

---


# Step 8: Implementing Result Wrapper Classes for API Responses

In this step, we created result wrapper classes to standardize API responses. These classes allow for consistent handling of success and error messages, as well as any data returned by the application. By using these wrappers, we can manage response codes, messages, and data payloads in a structured way, enhancing the readability and maintainability of the code.

## Overview of Classes Created

1. **Result**:
    - A base class representing a general response structure, containing a result code and a result message.
    - Used to indicate the status of an API call (e.g., success, error, validation failure).
    - Includes predefined static instances for common results like `SUCCESS`, `SERVER_ERROR`, `VALIDATION_ERROR`, etc.
    - **Key Methods and Fields**:
        - `resultCode`: An integer representing the status code of the response.
        - `resultText`: A message describing the result (e.g., "OK!", "SERVER ERROR!").
        - `isSuccess()`: Returns `true` if the result indicates success (i.e., `resultCode` is 0).
        - `showMessage(Result resultType, String customMessage)`: Allows customization of result messages based on predefined result types.

2. **DataResult<T>**:
    - A subclass of `Result` specifically designed to wrap responses that include data payloads.
    - Generic class (`DataResult<T>`) that can wrap any type of data, making it flexible for various use cases.
    - **Key Fields and Methods**:
        - `data`: The actual data payload returned by the API (e.g., a list of users, a single object).
        - Two constructors:
            - `DataResult(T data, Result result)`: Initializes with data and a predefined `Result`.
            - `DataResult(T data, int resultCode, String resultText)`: Initializes with data, a custom result code, and result message.

---

#### Example Usage
```
Result successResult = Result.SUCCESS;
Result validationErrorResult = Result.showMessage(Result.VALIDATION_ERROR, "Invalid email format.");
```
### 2. `DataResult<T>` Class

The `DataResult` class extends `Result` to include data in the response, allowing API responses to include both status information and a data payload.

- **Generic Type**: `DataResult<T>` allows flexibility to handle various types of data (e.g., `DataResult<List<User>>`).
- **Constructors**:
  - `DataResult(T data, Result result)`: Allows creating a data result based on an existing `Result` instance.
  - `DataResult(T data, int resultCode, String resultText)`: Creates a new `DataResult` with a custom result code, result message, and data payload.
- **Key Methods**:
  - `getData()`: Retrieves the data associated with this result.
- **toString()**:
  - Provides a detailed representation of the `DataResult`, including `data`, `resultCode`, and `resultText`.

#### Example Usage
```
DataResult<User> userResult = new DataResult<>(user, Result.SUCCESS);
DataResult<List<User>> usersResult = new DataResult<>(userList, 0, "User list fetched successfully.");
```


# Step 9: Implementing UserMapper for DTO and Entity Conversion

In this step, we implemented the `UserMapper` interface to handle conversions between `User` entity objects and various Data Transfer Objects (DTOs) used in authentication and user data handling. Using MapStruct, we created mapping methods to streamline the transformation of data between different layers of the application, particularly for authentication and user management.

## Overview of `UserMapper` Mappings

The `UserMapper` interface is annotated with `@Mapper` from the MapStruct library, which enables automatic generation of mapping implementations at compile time. This mapper interface includes methods for converting between `User` entities and DTOs like `LoginRequest`, `SignupRequest`, and `UserInfoResponse`.

1. **loginRequestToUser**:
    - Maps the `LoginRequest` DTO to a `User` entity. This conversion is used during the login process to verify user credentials.
    - **Mapped Fields**:
        - `username` and `password` from `LoginRequest` are directly mapped to the corresponding fields in the `User` entity.

2. **signupRequestToUser**:
    - Maps the `SignupRequest` DTO to a `User` entity, used during user registration.
    - **Mapped Fields**:
        - `username`, `email`, and `password` fields from `SignupRequest` are directly mapped to the `User` entity.
        - The `roles` field is ignored in this method, as roles are generally assigned separately post-registration.

3. **userToUserInfoResponse**:
    - Maps a `User` entity to a `UserInfoResponse` DTO, typically used to return user details after a successful login.
    - **Mapped Fields**:
        - `id`, `username`, and `email` from `User` are mapped to the corresponding fields in `UserInfoResponse`.
        - `roles` is mapped by converting each `Role` object in the user's roles to a string representation using a `stream` and `map` function.

4. **usersToUserInfoResponses**:
    - Maps a list of `User` entities to a list of `UserInfoResponse` DTOs, allowing for batch processing of user data.
    - **Mapped Fields**: All fields in each `User` entity are mapped similarly to the `userToUserInfoResponse` method.

5. **mapRoles**:
    - A custom method to handle the mapping of roles in `Set<String>` format.
    - This method is used to ensure that role data remains consistent and can be easily processed within the application.

---

## Code Breakdown

### `UserMapper` Interface

```
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // LoginRequest to User conversion (for authentication)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User loginRequestToUser(LoginRequest loginRequest);

    // SignupRequest to User conversion (for registration)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", ignore = true) // Roles will be set separately
    User signupRequestToUser(SignupRequest signupRequest);

    // User to UserInfoResponse conversion (for returning user info)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))")
    UserInfoResponse userToUserInfoResponse(User user);

    // List<User> to List<UserInfoResponse> conversion
    List<UserInfoResponse> usersToUserInfoResponses(List<User> users);

    // Convert Set of roles from SignupRequest to User entity roles
    default Set<String> mapRoles(Set<String> roles) {
        return roles.stream().collect(Collectors.toSet());
    }
}
```


# Step 10: Implementing Custom Exception Handling with Global Exception Handler

In this step, we implemented custom exception handling to provide meaningful error responses across the application. We created various custom exceptions for specific error scenarios and a global exception handler using `@ControllerAdvice` to centralize exception handling. This approach standardizes error responses and improves the user experience by providing clear and consistent error messages.

## Overview of Components Created

1. **Custom Exceptions**:
    - Several custom exceptions were created to handle specific error cases in the application. These exceptions extend `RuntimeException` and are annotated with `@ResponseStatus` to set the appropriate HTTP status codes.

2. **ErrorDetails Class**:
    - A data class to represent the structure of error responses sent to the client.
    - Contains fields for `timestamp`, `message`, and `details` to give context to each error response.

3. **GlobalExceptionHandler Class**:
    - A centralized exception handler annotated with `@ControllerAdvice` to intercept exceptions thrown by controllers.
    - Uses `@ExceptionHandler` to catch specific exceptions and return a standardized error response.
    - Provides a global handler for uncaught exceptions, ensuring a consistent error structure across the application.

---

## Code Breakdown

### 1. Custom Exceptions

The following custom exceptions were implemented to handle specific error scenarios in the application:

- **AlreadyDeletedException**:
    - Thrown when attempting to access or modify a resource that has already been marked as deleted.
    - Annotated with `@ResponseStatus(HttpStatus.GONE)` to return a `410 Gone` status.

- **DuplicateResourceException**:
    - Thrown when attempting to create or update a resource that already exists.
    - Annotated with `@ResponseStatus(HttpStatus.CONFLICT)` to return a `409 Conflict` status.

- **InvalidInputException**:
    - Thrown when invalid input is provided by the user.
    - Not annotated with `@ResponseStatus`, but handled in the `GlobalExceptionHandler` to return a `400 Bad Request` status.

- **OperationFailedException**:
    - Thrown when an operation fails unexpectedly.
    - Handled in the `GlobalExceptionHandler` to return a `500 Internal Server Error` status.

- **OutOfStockException**:
    - Thrown when an operation attempts to use resources that are out of stock.
    - Annotated with `@ResponseStatus(HttpStatus.BAD_REQUEST)` to return a `400 Bad Request` status.

- **ResourceNotFoundException**:
    - Thrown when a requested resource cannot be found.
    - Annotated with `@ResponseStatus(HttpStatus.NOT_FOUND)` to return a `404 Not Found` status.

- **SaveException**:
    - Thrown when there is a failure during saving a resource.
    - Annotated with `@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)` to return a `500 Internal Server Error` status.

### 2. `ErrorDetails` Class

The `ErrorDetails` class represents the structure of error responses. It contains:

- **Fields**:
    - `timestamp`: The date and time when the error occurred.
    - `message`: A brief message describing the error.
    - `details`: Additional information about the error, such as the request path.

- **Annotations**:
    - `@Data`, `@AllArgsConstructor`, and `@NoArgsConstructor` from Lombok to automatically generate boilerplate code.
      Example:
```
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
```

### 3. `GlobalExceptionHandler` Class

The `GlobalExceptionHandler` class is annotated with `@ControllerAdvice`, allowing it to intercept and handle exceptions across all controllers.

- **Methods**:
    - Each method is annotated with `@ExceptionHandler` to handle a specific exception type.
    - Returns a `ResponseEntity<ErrorDetails>` with the appropriate HTTP status and `ErrorDetails` payload.

- **Key Handlers**:
    - `handleResourceNotFoundException`: Handles `ResourceNotFoundException` and returns a `404 Not Found`.
    - `handleGlobalException`: A generic handler for unexpected exceptions, returning a `500 Internal Server Error`.
    - `handleOperationFailedException`: Handles `OperationFailedException` with a `500 Internal Server Error`.
    - `handleInvalidInputException`: Handles `InvalidInputException` and returns a `400 Bad Request`.
    - `handleAlreadyDeletedException`: Handles `AlreadyDeletedException` with a `410 Gone`.
    - `handleSaveException`: Handles `SaveException` with a `500 Internal Server Error`.
    - `handleDuplicateResourceException`: Handles `DuplicateResourceException` with a `409 Conflict`.

Example:
```
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "An unexpected error occurred", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Additional handlers for other exceptions...
}
```

# Step 11: Implementing Breadcrumb Service for Navigation Path Display

In this step, we implemented a `BreadcrumbService` to dynamically generate breadcrumb navigation paths based on the current URL. Breadcrumbs help users understand the hierarchical structure of the website and navigate easily by showing the path from the homepage to the current page.

## Overview of Components Created

1. **BreadcrumbItem Class**:
    - A simple model class to represent each item in the breadcrumb navigation.
    - Each breadcrumb item has a `label` and a `url`:
        - `label`: The display text for the breadcrumb (e.g., "Home", "Products").
        - `url`: The URL associated with the breadcrumb item, allowing navigation.

2. **BreadcrumbService Class**:
    - A service class that dynamically generates a breadcrumb path based on the provided URL.
    - This service uses the current URL to break down each segment and convert it into a structured breadcrumb list.
    - **Key Methods**:
        - `generateBreadcrumb(String currentPath)`: Main method to generate breadcrumb items based on the `currentPath`.
        - `capitalize(String str)`: Helper method to capitalize the first letter of each breadcrumb label for better readability.

---

## Code Breakdown

### 1. `BreadcrumbItem` Class

The `BreadcrumbItem` class represents a single item in the breadcrumb trail.

- **Fields**:
    - `label`: Display text for the breadcrumb item, such as "Home" or "Products".
    - `url`: The URL that the breadcrumb item links to, enabling navigation.

- **Constructor**:
    - `BreadcrumbItem(String label, String url)`: Initializes the breadcrumb item with the specified label and URL.

- **Methods**:
    - `getLabel()`: Returns the label of the breadcrumb item.
    - `getUrl()`: Returns the URL of the breadcrumb item.
    - `toString()`: Provides a string representation of the breadcrumb item for debugging purposes.

Example:
```
public class BreadcrumbItem {
    private String label;
    private String url;

    public BreadcrumbItem(String label, String url) {
        this.label = label;
        this.url = url;
    }

    // Getters and toString() method...
}
```

### 2. `BreadcrumbService` Class

The `BreadcrumbService` class is responsible for generating a list of `BreadcrumbItem` objects based on the current URL path.

- **Method: `generateBreadcrumb(String currentPath)`**:
    - Splits the `currentPath` by `/` to create segments for each part of the URL path.
    - Creates a list of `BreadcrumbItem` objects:
        - Always starts with the "Home" item.
        - Iterates over each segment, capitalizing it for readability and appending it to the breadcrumb list.
    - Returns the list of breadcrumb items in the correct hierarchical order.

- **Method: `capitalize(String str)`**:
    - Capitalizes the first letter of the input string to enhance the display of breadcrumb labels.

Example:
```
@Service
public class BreadcrumbService {

    public List<BreadcrumbItem> generateBreadcrumb(String currentPath) {
        List<BreadcrumbItem> breadcrumbs = new ArrayList<>();

        // The homepage is always the first breadcrumb item
        breadcrumbs.add(new BreadcrumbItem("Home", "/"));

        // Add each path segment as a breadcrumb item
        String[] pathSegments = currentPath.split("/");
        StringBuilder currentUrl = new StringBuilder();
        
        for (String segment : pathSegments) {
            if (!segment.isEmpty()) {
                currentUrl.append("/").append(segment);
                breadcrumbs.add(new BreadcrumbItem(capitalize(segment), currentUrl.toString()));
            }
        }

        return breadcrumbs;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```
### Usage Example:
Suppose the current path is `/products/electronics/laptops`. 
The breadcrumb service would generate the following breadcrumb items:
1. Home (/)
2. Products (/products)
3. Electronics (/products/electronics)
4. Laptops (/products/electronics/laptops)

This breadcrumb items can be displayed in the UI to help users navigate the site effectively.
