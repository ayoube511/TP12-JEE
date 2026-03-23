# 🔐 Spring JWT API

> API REST sécurisée avec **JSON Web Token (JWT)** — Spring Boot 3 + Spring Security + MySQL + BCrypt

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.x-darkgreen?style=flat-square&logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-0.11.5-purple?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)

---

## 📋 Description

API REST **stateless** sécurisée avec JWT. Contrairement aux TPs précédents qui utilisaient des sessions,
ce projet utilise un **token signé** transmis dans chaque requête HTTP.  
Aucune session n'est stockée côté serveur.

---

## 🔄 Comparaison avec les TPs précédents

| | TP Security 1 & 2 | TP Security JPA | Ce TP (JWT) |
|---|---|---|---|
| Authentification | Formulaire HTML | Formulaire HTML | **API REST** |
| Sessions | ✅ Stockées serveur | ✅ Stockées serveur | ❌ **STATELESS** |
| Token | ❌ | ❌ | ✅ **JWT Bearer** |
| Interface | Thymeleaf | Thymeleaf | **JSON pur** |
| Stockage users | Mémoire | MySQL | **MySQL** |

---

## 🔁 Flux d'authentification JWT
```
Client → POST /api/auth/login (username + password)
              ↓
         Spring vérifie les credentials
              ↓
         Génère un token JWT signé
              ↓
Client reçoit le token
              ↓
Client → GET /api/admin/dashboard
         Header: Authorization: Bearer <token>
              ↓
         JwtAuthorizationFilter valide le token
              ↓
         Accès autorisé ✅ ou refusé 403 ❌
```

---

## ✨ Fonctionnalités

- 🔐 Login REST qui génère un token JWT
- 🔍 Filtre JWT qui valide chaque requête
- 👤 Rôle **USER** → `/api/user/profile`
- 🔑 Rôle **ADMIN** → `/api/admin/dashboard`
- 🌐 Route publique sans token → `/api/auth/public`
- 🚫 403 si token absent ou invalide
- 🗄️ Utilisateurs stockés en **MySQL** avec **BCrypt**

---

## 🏗️ Structure du projet
```
src/main/java/ma/ens/security/
├── config/
│   ├── SecurityConfig.java              # STATELESS + règles + filtre JWT
│   └── DatabaseInitializer.java         # Insertion automatique des données
├── jwt/
│   ├── JwtUtil.java                     # Génération + validation du token
│   └── JwtAuthorizationFilter.java      # Filtre JWT sur chaque requête
├── entities/
│   ├── User.java                        # Entité JPA
│   └── Role.java                        # Entité JPA
├── repositories/
│   ├── UserRepository.java
│   └── RoleRepository.java
├── services/
│   └── CustomUserDetailsService.java    # Charge les users depuis MySQL
├── web/
│   ├── AuthController.java              # POST /api/auth/login
│   └── TestController.java              # Routes protégées de test
└── SpringJwtApiApplication.java
```

---

## 🛠️ Technologies

| Technologie | Version | Rôle |
|---|---|---|
| Spring Boot | 3.3 | Framework principal |
| Spring Security | 6.x | Authentification & autorisation |
| jjwt | 0.11.5 | Génération et validation JWT |
| Spring Data JPA | 3.x | Persistance |
| MySQL | 8.x | Base de données |
| BCrypt | - | Encodage mots de passe |
| Lombok | - | Réduction boilerplate |

---

## ⚙️ Prérequis

- ✅ JDK 17+
- ✅ MySQL 8.x
- ✅ Maven 3.x
- ✅ Postman (pour tester l'API)

---

## 🚀 Installation & Lancement

### 1. Cloner le projet
```bash
git clone https://github.com/ton-username/spring-jwt-api.git
cd spring-jwt-api
```

### 2. Créer la base MySQL
```sql
CREATE DATABASE security_jwt;
```

### 3. Configurer application.properties
```properties
spring.datasource.password=ton_mot_de_passe
```

### 4. Lancer
```bash
mvn spring-boot:run
```

---

## 🧪 Resultat des Tests avec Postman
<img width="1257" height="791" alt="Capture d&#39;écran 2026-03-22 233038" src="https://github.com/user-attachments/assets/48f59a7d-b351-4b56-9c4c-b571bedcccf2" />
<img width="1251" height="964" alt="Capture d&#39;écran 2026-03-23 103043" src="https://github.com/user-attachments/assets/09fd4028-38ee-4187-9961-3ed2d7b7e6ef" />
<img width="1249" height="968" alt="Capture d&#39;écran 2026-03-23 104029" src="https://github.com/user-attachments/assets/b19a794d-ac1c-4f7d-a43a-ee912b1c2e26" />

---

## 🧪 Comptes de test

| Utilisateur | Mot de passe | Rôle | Accès |
|---|---|---|---|
| `user` | `1111` | ROLE_USER | `/api/user/profile` |
| `admin` | `1234` | ROLE_ADMIN + ROLE_USER | Toutes les routes |

---

## 🔑 Code clé
```java
// Génération du token
public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
}

// Filtre JWT — vérifie chaque requête
String token = header.substring(7); // retire "Bearer "
String username = jwtUtil.extractUsername(token);
if (jwtUtil.validateToken(token)) {
    // Reconstruit le contexte d'authentification
    SecurityContextHolder.getContext().setAuthentication(authentication);
}
```

---

## 👨‍💻 Auteur

**Ayoube Moubssite**  
TP JWT — API REST Stateless avec Spring Security + JSON Web Token

---

> ⚠️ En production : stocker `jwt.secret` dans une variable d'environnement, jamais en dur dans le code.
