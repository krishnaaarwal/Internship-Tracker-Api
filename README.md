Internship Tracker API

A production-minded Spring Boot backend to track internship postings and applications — built the right way: security-first, permission-driven, and ownership-aware.
I learned a ton building this; this README captures what the project actually is, how it’s designed, and how to run and extend it.

Summary

A RESTful API for managing Users, Companies, Internships and Applications with:

JWT + OAuth2 (Google/GitHub) authentication and account linking

Role → Permission mapping and method-level authorization (@PreAuthorize)

Ownership checks (owner-only operations via a central AuthorizationService)

Service-layer security (not only controller guards)

Pagination, DTOs, validation and layered architecture (controllers → services → repositories)

PostgreSQL for real persistence (dev/devops-ready), spring.jpa.hibernate.ddl-auto used for schema during development

Features (what’s implemented)

Authentication

Email/password login (via POST /auth/login)

OAuth2 login (Google, GitHub) with account linking and success handler that returns JWT

Stateless JWT authentication for protected endpoints

JWT-based security with a filter that verifies token and loads user from DB

Refresh token planned (not implemented yet)

Authorization

Roles (multiple per user) stored as an @ElementCollection (eager)

Permission enum (PermissionType) and RolePermissionMapping defines what each role can do

UserEntity.getAuthorities() combines ROLE_* and permissions as GrantedAuthority

Method-level security via @EnableMethodSecurity + @PreAuthorize

Central AuthorizationService (@Component("authz")) for ownership checks (application owner, company owner, admin, etc.)

Ownership checks used in service layer to prevent horizontal privilege escalation

Domain model

User — identity, roles, optional company, provider type (EMAIL/GOOGLE/GITHUB), OAuth provider id

Company — business entity that owns internships

Internship — belongs to a Company

Application — a user’s application to an internship

DTO pattern for requests/responses (no entity leaks in controllers)

Other

JPA/Hibernate (lifecycle, validation annotations)

Paging & sorting (Spring Data Pageable) with server-side safeguards

ModelMapper for mapping DTOs ↔ entities (used intentionally; critical endpoints use explicit mapping where needed)

Clean separation of concerns: controllers handle HTTP; services contain business + security; repositories access DB

Tech stack

Java 22

Spring Boot 4.x (4.0.1 in dev logs)

Spring Security (OAuth2 client + JWT)

Spring Data JPA / Hibernate 7.x

PostgreSQL (dev DB used in logs)

Maven

ModelMapper


Quick start (development)

Clone & build

git clone <https://github.com/krishnaaarwal/Internship-Tracker-Api>
cd Internship-t=Tracker-Api
mvn clean package -DskipTests


Configure environment
Create src/main/resources/application.yml or specify environment variables.

Run

mvn spring-boot:run
# or
java -jar target/internship-tracker-api.jar


The app will create tables on startup (development mode seen in logs). Change ddl-auto for production.

Important env vars / properties you must set

jwt.secretkey — strong random string (used to sign JWTs)

spring.datasource.url, username, password — point to your Postgres

spring.security.oauth2.client.registration.google.client-id / client-secret (and similarly for github) if you want OAuth2

For production: configure spring.jpa.hibernate.ddl-auto=validate and use a migration tool (Flyway/Liquibase)

Example usage (core endpoints)

Public

POST /auth/signup — create local (email) user

POST /auth/login — returns JWT (access token)

GET /oauth2/authorization/google — OAuth login start (browser)

Users (service-layer guarded)

GET /users/{id}

GET /users

POST /users

PUT/PATCH /users/{id}

DELETE /users/{id}

Companies

GET /company

GET /company/{id}

POST /company (create)

PUT/PATCH /company/{id}

DELETE /company/{id}

Internships

GET /internships

GET /internships/{id}

GET /internships/company/{companyId}

POST /internships (only recruiter for their company or admin)

Applications

GET /applications/user/{userId} — pagination supported

POST /applications — create application

PUT /applications/{id}/status — update status (ownership/permission checks)

DELETE /applications/{id}

Many endpoints require permissions (e.g. APPLICATION_READ, INTERNSHIP_WRITE) and ownership checks; unauthorized requests return 403.

Security notes (how it works)

JwtAuthFilter extracts Bearer tokens, parses JWT, and sets Authentication using UserEntity loaded from DB.

AuthService handles:

email/password login (via AuthenticationManager)

OAuth2 login flows + account linking (if OAuth email matches an existing local account, accounts are linked)

UserEntity.getAuthorities() returns both role authorities (e.g. ROLE_RECRUITER) and mapped permissions as SimpleGrantedAuthority (e.g. INTERNSHIP_WRITE)

RolePermissionMapping centralizes Role → Permission mapping

AuthorizationService (bean named authz) contains owner checks used in @PreAuthorize SpEL expressions, e.g. @authz.isApplicationOwner(#id)

Design decisions & rationale (short)

Service-layer authorization: prevents bypass by internal calls and gives business-level protection, not only HTTP-level.

Permissions + roles: roles are coarse, permissions are fine-grained. Roles map to permission sets so adding new roles is straightforward.

Ownership checks centralized: avoids repeating DB lookup logic everywhere and keeps expressions readable.

No entity in DTOs: requests/response use DTOs only. Server resolves entity references by id — reduces attack surface.

How to create an admin (dev)

You must seed an admin account (dev options):

Insert directly into DB (roles column needs the set value). Example SQL concept — adapt to your schema:

-- You will need to insert user record and roles in element collection table; easiest: use an app-data.sql or an admin endpoint for seeding


Or temporarily change signup flow to assign ADMIN (not recommended). Better: seed via migration script.

Tip: create an initial admin in data.sql (dev) or manage via a simple one-time admin endpoint protected by a secret.

Testing & debugging

Use Postman or curl to call /auth/login and pass Authorization: Bearer <token> to protected endpoints.

Watch startup logs for DB connection and Hibernate DDL messages.

Use spring.jpa.open-in-view=false in application.yml to avoid lazy-loading during view rendering.
