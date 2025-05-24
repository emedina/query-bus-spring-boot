# 🔍 Spring Query Bus

A lightweight query bus implementation for Spring Boot applications that enables centralized query handling using Spring's dependency injection capabilities.

## 🚀 Recent Upgrades (v1.0.0)

### ✅ JDK 24 & Latest Dependencies

- **Java**: Upgraded to **JDK 24** (GA release March 2025)
- **Spring Framework BOM**: 6.1.13 → **6.2.1** (latest GA)
- **Maven Compiler Plugin**: 3.12.1 → **3.13.0**
- **Maven Surefire Plugin**: **3.5.2** (latest GA)
- **Versions Maven Plugin**: 2.17.1 → **2.18.0**

### 🧪 Comprehensive Testing

- **90%+ Test Coverage**: Added extensive unit tests for all components
- **Modern Testing**: JUnit 5, AssertJ, Mockito with proper fixtures
- **Quality Assurance**: Edge cases, null handling, and error scenarios covered
- **JaCoCo Integration**: Automated coverage reporting

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
    <version>6.2.1</version>
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
| **Spring Framework BOM** | 6.2.1 | Core Spring integration |
| **Java** | 24 | Runtime platform |
| **Shared Kernel Query Bus** | 1.0.0 | Query interfaces |
| **JUnit Jupiter** | 5.11.3 | Testing framework |
| **AssertJ** | 3.26.3 | Fluent assertions |
| **Mockito** | 5.14.2 | Mocking framework |

## 🔧 Development

### Requirements

- **JDK 24** or higher
- **Maven 3.9+**
- **Spring Framework 6.2+**

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
| 1.0.x            | 24      | 6.2.0+          | 1.0.0+        | Current |

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

`spring-boot` `query-bus` `cqrs` `hexagonal-architecture` `ddd` `query-pattern` `spring-framework` `dependency-injection` `java-24`

---

*🎯 This library is designed to support clean architecture principles and CQRS patterns in Spring Boot applications, providing the query side complement to the command bus.*
