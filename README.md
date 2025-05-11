# Medical API

[![AGTS LLC](https://www.agts.llc/images/logo.png)](https://www.agts.llc)

A comprehensive medical records management system API built with Spring Boot and JPA, developed by [AGTS LLC](https://www.agts.llc).

## Features

- Patient Management
- Medical Conditions Tracking
- Medical File Storage
- Secure Authentication
- RESTful API with Swagger UI

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- PostgreSQL 12 or higher
- Docker (optional, for containerized deployment)

## Project Structure

```
medical-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/xagau/medical/
│   │   │       ├── controller/       # REST controllers
│   │   │       ├── model/            # Entity classes
│   │   │       ├── repository/       # JPA repositories
│   │   │       └── service/          # Business logic
│   │   └── resources/
│   │       ├── application.properties # Configuration
│   │       └── swagger/              # Swagger UI configuration
└── pom.xml                           # Project configuration
```

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd medical-api
```

### 2. Configure Database

Edit `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/medical_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 4. Access Swagger UI

Once the application is running, you can access the API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Patient Management

- `POST /api/v1/patients` - Create a new patient
- `GET /api/v1/patients/{id}` - Get patient details
- `PUT /api/v1/patients/{id}` - Update patient information
- `DELETE /api/v1/patients/{id}` - Delete a patient

### Medical Conditions

- `POST /api/v1/conditions/add` - Add a new medical condition
- `GET /api/v1/conditions/patient/{patientId}` - Get conditions for a patient

### Medical Files

- `POST /api/v1/medical-files/upload` - Upload medical file
- `GET /api/v1/medical-files/patient/{patientId}` - Get medical files for a patient
- `DELETE /api/v1/medical-files/{fileId}` - Delete medical file

## Entity Relationships

### Patient Entity

```java
@Entity
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String passwordHash;
    private String ssoProvider;
    private String ssoId;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private String language;
    private String timezone;
    private CommunicationPreference communicationPreference;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private boolean consentToShareData;
    private LocalDate consentSignedDate;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PdfMedicalFile> medicalFiles;
}
```

### Medical File Entity

```java
@Entity
public class PdfMedicalFile {
    private Long id;
    private String type;
    private String fileName;
    private String contentType;
    private String description;
    private byte[] data;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
```

## Security

- All endpoints are secured with JWT authentication
- Passwords are hashed using BCrypt
- Role-based access control
- CSRF protection enabled

## Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=YourTestClassName
```

## Deployment

### Docker

```bash
# Build Docker image
docker build -t medical-api .

# Run Docker container
docker run -p 8080:8080 medical-api
```

### Kubernetes

```bash
# Deploy to Kubernetes
kubectl apply -f kubernetes/medical-api-deployment.yaml
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

- Project Link: [https://github.com/yourusername/medical-api](https://github.com/yourusername/medical-api)
- Company: [AGTS LLC](https://www.agts.llc)
- Email: contact@agts.llc
