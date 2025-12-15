### Endpoints du micro-service Utilisateurs

| Méthode | URL                         | Description                                      | Corps de requête                        | Réponse principale                         | Codes HTTP importants                      |
|--------|-----------------------------|--------------------------------------------------|-----------------------------------------|--------------------------------------------|--------------------------------------------|
| GET    | `/api/users`                | Liste tous les utilisateurs                      | –                                       | `200 OK` + `List<UserResponse>`            | `200`                                      |
| GET    | `/api/users/{id}`           | Récupère le profil d’un utilisateur par id       | –                                       | `200 OK` + `UserResponse`                  | `200`, `404` (utilisateur non trouvé)      |
| POST   | `/api/users`                | Crée un nouvel utilisateur (inscription)         | `UserRequest` (JSON)                    | `201 Created` + `UserResponse`             | `201`, `400` (validation), `409` (email déjà utilisé) |
| PUT    | `/api/users/{id}`           | Met à jour le profil complet d’un utilisateur    | `UserRequest` (JSON)                    | `200 OK` + `UserResponse`                  | `200`, `400`, `404`                        |
| DELETE | `/api/users/{id}`           | Supprime un utilisateur                          | –                                       | `204 No Content`                           | `204`, `404`                               |
| PATCH  | `/api/users/{id}/role`      | Met à jour uniquement le rôle d’un utilisateur   | `RoleUpdateRequest` (JSON `{ "role": ... }`) | `200 OK` + `UserResponse`             | `200`, `400`, `404`                        |
| POST   | `/api/auth/login`           | Authentification (login) par email + mot de passe| `LoginRequest` (JSON)                   | `200 OK` + `UserResponse` (sans password)  | `200`, `400`, `401` (identifiants invalides) |
