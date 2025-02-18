# Bookstore E-Commerce Backend

## Overview
This is a monolithic e-commerce backend for a bookstore, built using **Spring Boot** and **MySQL**. The application provides features such as user authentication, book management, cart functionality, order processing, wishlist management, and payment integration using **Stripe**.

## Features
- **User Authentication & Authorization**: Secure login and registration using JWT.
- **Admin Management**: The first admin is created without authentication, subsequent admins can only be created by an existing admin.
- **Book Management**: CRUD operations for books.
- **Cart & Wishlist**: Users can add/remove books to their cart and wishlist.
- **Order Processing**: Users can place and manage their orders.
- **Payment Integration**: Payments are processed using Stripe.
- **Email Notifications**: Users receive confirmation emails for key actions.
- **Security**: Role-based access control with `@PreAuthorize`.

## Technologies Used
- **Spring Boot** (Backend Framework)
- **Spring Security** (Authentication & Authorization)
- **MySQL** (Database)
- **Stripe API** (Payment Processing)
- **JWT** (Token-Based Authentication)
- **Lombok** (Simplifies Java Code)

## API Endpoints
### Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Authenticate a user |
| POST | `/api/v1/auth/createAdmin` | Create the first admin without authentication |

### User Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/users/{userId}` | Get user details |
| PUT | `/api/v1/users/update/{userId}` | Update user details |
| POST | `/api/v1/users/createAdmin` | Admin creates another admin |
| DELETE | `/api/v1/users/delete/{userId}` | Delete a user (Admin only) |

### Book Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/books` | Get all books |
| GET | `/api/v1/books/{isbn}` | Get book details by ISBN |
| POST | `/api/v1/books/add` | Add a new book (Admin only) |
| PUT | `/api/v1/books/update/{isbn}` | Update book details (Admin only) |
| DELETE | `/api/v1/books/delete/{isbn}` | Delete a book (Admin only) |

### Cart Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/{userId}/cart/add` | Add a book to cart |
| GET | `/api/v1/{userId}/cart` | Get cart items |
| PUT | `/api/v1/{userId}/cart/updateCartItemQuantity` | Update cart item quantity |
| DELETE | `/api/v1/{userId}/cart/deleteCartItem` | Remove a book from the cart |
| DELETE | `/api/v1/{userId}/cart/deleteCart` | Clear the cart |

### Wishlist Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/{userId}/wishlist/addToWishList` | Add book to wishlist |
| GET | `/api/v1/{userId}/wishlist` | View wishlist |
| DELETE | `/api/v1/{userId}/wishlist/removeFromWishList` | Remove book from wishlist |

### Order Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/order/create` | Place an order |
| GET | `/api/v1/order` | Get all orders (Admin only) |
| GET | `/api/v1/order/{orderId}` | Get order details |
| PUT | `/api/v1/order/{orderId}/updateOrderStatus` | Update order status (Admin only) |
| DELETE | `/api/v1/order/{orderId}/delete` | Delete an order (Admin only) |

### Payment
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/payment/create` | Create a payment session using Stripe |
| POST | `/api/v1/payment/confirm` | Confirm and process payment |

### Forgot Password
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/v1/forgotPassword/{email}` | Send OTP for password reset |
| PUT | `/api/v1/forgotPassword/{email}/verifyOtp` | Verify OTP and reset password |
| PUT | `/api/v1/forgotPassword/{email}/changePassword` | Change password

## Installation and Setup
1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd bookstore-ecommerce-backend
   ```
2. Configure **application.yml**:
   ```yaml
   spring:
     application:
       name: bookstore-ecommerce
     datasource:
       url: jdbc:mysql://localhost:3306/bookstore
       username: (your_mysql_username)
       password: (your_mysql_password)
     jpa:
       hibernate:
         ddl-auto: update
       properties:
         hibernate:
           dialect: org.hibernate.dialect.MySQL8Dialect

     mail:
       host: smtp.gmail.com
       port: 587
       username: (your_email_address)
       password: ${MAIL_PASSWORD}
       properties:
         mail:
           smtp:
             auth: true
             starttls:
               enable: true

   stripe:
     secret-key: ${STRIPE_SECRET_KEY}
   ```
3. Store the Stripe secret key and Email password as an environment variable:
   ```sh
   export STRIPE_SECRET_KEY=your_stripe_secret_key
   export MAIL_PASSWORD=your_email_password
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## Stripe Payment Setup
1. Create an account on [Stripe](https://stripe.com/)
2. Get your API keys from the Stripe dashboard.
3. Ensure `STRIPE_SECRET_KEY` is set as an environment variable before running the application.






