# spring-security-jwt
Demo project to practice JWT authentication and authorization with Spring Security

## Data
The application initializes with two Roles and one User persisted on the database. 

### Roles 
| id | description |
|---|---|
| 1 | admin |
| 2 | member |

### Users 
| id | name | password |
|---|---|---|
| 1 | admin | admin |

*Note: the password is **not** being saved as plain text on the database.*

## Endpoints

### Register new User

```sh
$ curl --request POST \
  --url http://localhost:8080/register \
  --header 'Content-Type: application/json' \
  --data '{
	"username": "newUser",
	"password": "password"
}'
```

### Authenticate
Returns JWT and token type.

```sh
$ curl --request POST \
  --url http://localhost:8080/auth \
  --header 'Content-Type: application/json' \
  --data '{
	"username": "admin",
	"password": "admin"
}'
```

### List all users
Both roles `member` and `admin` can call this endpoint. It's required to pass the JWT as a Bearer on the Authentication header. 

```sh
$ curl --request GET \
  --url http://localhost:8080/users \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJTcHJpbmcgU2VjdXJpdHkgSnd0Iiwic3ViIjoiMiIsImlhdCI6MTY1MDgyMTA5OCwiZXhwIjoxNjUwOTA3NDk4fQ.3bpPRKFQupjq9WeWtpHs1jOq0zY4SHxlUEmku1dH22_Bj17CTUiTe9vk28VsfYTvCfqt3FEowsU7bCeOPqtSyQ'
```

### Create new User
Only role `admin` can call this endpoint. It's required to pass the JWT as a Bearer on the Authentication header.

```sh
$ curl --request POST \
  --url http://localhost:8080/users \
  --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJTcHJpbmcgU2VjdXJpdHkgSnd0Iiwic3ViIjoiMSIsImlhdCI6MTY1MDgzMDcwNSwiZXhwIjoxNjUwOTE3MTA1fQ.4H6khUJDiDMYtQ3l7uNEqZ6ZkcjQQty2YCo4RTTvt0I-T_13fpb08VHSe8sCrheTXUetI5NZZcz5T5oFCpqMew' \
  --header 'Content-Type: application/json' \
  --data '{
	"username": "username",
	"password": "password",
	"roleId": 1
}'
```
