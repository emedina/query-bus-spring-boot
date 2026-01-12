# 🔍 Spring Query Bus

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Java Version](https://img.shields.io/badge/Java-25-blue)
![Test Coverage](https://img.shields.io/badge/coverage-90%25-brightgreen)

A lightweight query bus implementation for Spring Boot applications that enables centralized query handling using Spring's dependency injection capabilities.

## 📚 Further Learning

This implementation is part of a comprehensive exploration of Hexagonal Architecture patterns. The concepts are covered in depth in:

**English Version**
*Decoupling by Design: A Pragmatic Approach to Hexagonal Architecture*

- [PDF](https://leanpub.com/decouplingbydesignapractitionersguidetohexagonalarchitecture)  
- [Kindle](https://a.co/d/4KwauyK)  
- [Paperback](https://a.co/d/cGQI8gX)  

**Versión en Español**  
*Desacoplamiento por Diseño: Una Guía Práctica para la Arquitectura Hexagonal*

- [PDF](https://leanpub.com/desacoplamientopordiseounaguaprcticaparalaarquitecturahexagonal)  
- [Kindle](https://amzn.eu/d/ic50CoH)  
- [Tapa blanda](https://amzn.eu/d/1fHOpN6)  

The book provides in-depth coverage of:

- Spring Boot query bus implementations and patterns
- Type-safe query routing with return values
- Automatic handler discovery techniques
- Spring integration strategies for query handling
- Query pattern variations in CQRS systems
- Hexagonal architecture for data retrieval
- Testing strategies for query-driven systems
- Real-world query bus implementations

## 🎯 Overview

This library provides a clean implementation of the Query pattern integrated with Spring's application context. It automatically discovers query handlers and routes queries to their appropriate handlers, promoting loose coupling and separation of concerns in your application architecture.

## ✨ Features

- **🔍 Automatic Handler Discovery**: Automatically registers query handlers from Spring's application context
- **🎯 Type-Safe Query Routing**: Routes queries to their corresponding handlers based on generic type resolution
- **🔧 Spring Integration**: Leverages Spring's dependency injection for handler instantiation
- **⚡ Lightweight**: Minimal overhead with clean, focused API
- **🏗️ Hexagonal Architecture Support**: Perfect for implementing the query side of CQRS patterns
- **📊 Return Value Support**: Handlers can return typed results from query execution

## 📦 Installation

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.emedina.query</groupId>
    <artifactId>query-bus-spring-boot</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Required Dependencies

```xml
<!-- Core shared kernel -->
<dependency>
    <groupId>com.emedina.sharedkernel</groupId>
    <artifactId>shared-kernel-query-bus</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Spring Context (provided scope) -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>7.0.2</version>
</dependency>
```

## 🚀 Quick Start

### 1️⃣ Create a Query

```java
import com.emedina.sharedkernel.query.Query;

public class FindUserByIdQuery implements Query {
    private final Long userId;
    
    public FindUserByIdQuery(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return userId;
    }
}
```

### 2️⃣ Create a Query Handler

```java
import com.emedina.sharedkernel.query.core.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class FindUserByIdQueryHandler implements QueryHandler<UserDto, FindUserByIdQuery> {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDto handle(FindUserByIdQuery query) {
        User user = userRepository.findById(query.getUserId());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
```

### 3️⃣ Configure the Query Bus

```java
import com.emedina.query.spring.Registry;
import com.emedina.query.spring.SpringQueryBus;
import com.emedina.sharedkernel.query.core.QueryBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryBusConfiguration {
    
    @Bean
    public Registry registry(ApplicationContext applicationContext) {
        return new Registry(applicationContext);
    }
    
    @Bean
    public QueryBus queryBus(Registry registry) {
        return new SpringQueryBus(registry);
    }
}
```

### 4️⃣ Use the Query Bus

```java
import com.emedina.sharedkernel.query.core.QueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private QueryBus queryBus;
    
    public UserDto getUserById(Long userId) {
        FindUserByIdQuery query = new FindUserByIdQuery(userId);
        return queryBus.query(query);
    }
}
```

## 🏗️ Architecture

The query bus consists of three main components:

### 🔍 QueryBus

The main interface for executing queries. The `SpringQueryBus` implementation routes queries to their handlers and returns typed results.

### 📋 Registry

Maintains the mapping between query types and their handlers. It automatically discovers handlers from Spring's application context using generic type resolution.

### 🏭 QueryProvider

A factory that creates query handler instances using Spring's dependency injection capabilities.

## ⚙️ How It Works

1. **🔍 Handler Discovery**: On startup, the `Registry` scans the Spring application context for beans implementing `QueryHandler<R, Q>`
2. **🧬 Type Resolution**: Uses Spring's `GenericTypeResolver` to determine which query type each handler processes
3. **📝 Handler Registration**: Maps query types to their corresponding handler providers
4. **🚀 Query Execution**: When a query is executed, the bus looks up the appropriate handler, delegates execution, and returns the result

## 🧪 Testing

The library includes comprehensive unit tests with proper fixtures. All tests follow established patterns:

```bash
mvn test
```

### 📊 Test Coverage

- ✅ **Unit Tests**: All components tested with Mockito and proper fixtures
- ✅ **Edge Cases**: Missing handlers, null results, and empty contexts covered
- ✅ **Type Safety**: Generic type handling and casting scenarios
- ✅ **90%+ Coverage**: Comprehensive test suite with JaCoCo reporting

### 🧪 Test Structure

```
src/test/java/com/emedina/query/spring/
├── fixtures/
│   ├── TestQuery.java
│   ├── TestQueryHandler.java
│   ├── AnotherTestQuery.java
│   └── AnotherTestQueryHandler.java
├── SpringQueryBusTest.java
├── RegistryTest.java
└── QueryProviderTest.java
```

## 📋 Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| **Spring Framework BOM** | 7.0.2 | Core Spring integration |
| **Java** | 25 | Runtime platform |
| **Shared Kernel Query Bus** | 1.0.0 | Query interfaces |
| **JUnit Jupiter** | 6.0.2 | Testing framework |
| **AssertJ** | 3.27.6 | Fluent assertions |
| **Mockito** | 5.21.0 | Mocking framework |

## 🔧 Development

### Requirements

- **JDK 25** or higher
- **Maven 3.9+**
- **Spring Framework 7.0+**

### Building

```bash
mvn clean compile
```

### Testing

```bash
mvn test
```

### Coverage Report

```bash
mvn jacoco:report
```

View coverage report at `target/site/jacoco/index.html`

### Dependency Updates

```bash
mvn versions:display-dependency-updates
mvn versions:display-plugin-updates
```

## 📊 Version Compatibility

| Query Bus Spring | Min JDK | Spring Framework | Shared Kernel | Notes |
|------------------|---------|------------------|---------------|-------|
| 1.0.x            | 25      | 7.0.0+          | 1.0.0+        | Current |

## 🤝 Contributing

1. 🍴 Fork the repository
2. 🌿 Create a feature branch
3. ✅ Add tests for your changes
4. 🧪 Ensure all tests pass (90%+ coverage)
5. 📤 Submit a pull request

### Code Style

- Follow existing patterns and conventions
- Use proper JavaDoc for public APIs
- Maintain test coverage above 90%
- Use only GA releases for dependencies

## 📄 License

This project is part of the hexagonal architecture examples and follows the same licensing terms.

## 👨‍💻 Author

**Enrique Medina Montenegro**

---

## 🏷️ Tags

`spring-boot` `query-bus` `cqrs` `hexagonal-architecture` `ddd` `query-pattern` `spring-framework` `dependency-injection` `java-25`

---

*🎯 This library is designed to support clean architecture principles and CQRS patterns in Spring Boot applications, providing the query side complement to the command bus.*
