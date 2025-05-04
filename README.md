# Product Catalog

This is API is designed to manage the products, providing endpoints for all CRUD (Create, Read, Update, and Delete)
operatiosn. The API is build with Scala, Play framework, PostgresSQL and Angular.

This project is associated with [this board](https://github.com/users/v-limo/projects/4/views/1).

## Getting Started - Running the project

### Prerequisites

* Scala, docker (or postgresSQL) sbt installed (for the backend).
* Node.js and npm installed (for the frontend)

It is recommended to run the postgres database with docker.

### Clone the project

```bash
git clone git@github.com:v-limo/productcatalog.git
cd productcatalog
```

### Backend (Scala + Play)

To run the PostgreSQL database, you can either:

- Configure your own database by updating the backend/application.conf file
- Use the Docker setup (recommended) without modifying the configuration

```bash

cd backend

# Make sure the docker is running
docker compose up -d

# - Run the backend
sbt run

# The backend will start, usually on port 9000
```

---

- Note: Any pending migrations will be automatically applied if this is the first time you are running the application.

---

### Frontend (Angular)

```bash
cd frontend
npm install
npm start

# The frotend will start, usually on port 4200
```

## API Endpoints

```bash

 POST /products
 GET /products
 GET /products/:id
 PUT /products/:id
 DELETE /products/:id

```

**Example Product JSON:**

```json
{
  "id": 32123,
  "name": "Book ÜberPro",
  "category": "laptops",
  "code": "B12945",
  "price": 12.13,
  "details": [
    {
      "key": "Cpu",
      "value": "16 core"
    },
    {
      "key": "Display",
      "value": "Yes"
    }
  ]
}
```
