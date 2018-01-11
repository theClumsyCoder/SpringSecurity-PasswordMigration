# SpringSecurity-PasswordMigration
Welcome to the code repository that contians Spring application configured with Spring Security filter. The spring secuirty filter in this application uses a custom authetication provider to authenticate users and migrate existing users from insecure hashing algorithms like MD5 to industry accepted secure password hashing algorithms like Password Based Key Derivation Function 2 (PBKDF2). 
For a holistic approach on password migration please refer to the following blog:
https://veggiespam.com/painless-password-hash-upgrades/

# Contents:
1. Spring Application Code with Spring Secuity Custom Authentication Provider Implementation
2. MySQL database script to setup required tables and populate user data 
