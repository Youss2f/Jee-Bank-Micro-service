# Zero Tolerance Deployment Verify

## 1. Build and Run
Execute the following to build the entire stack with "Zero Tolerance" standards (Robust, Production-Ready):

```powershell
docker-compose up -d --build
```
*Note: The first build may take 10-15 minutes as it compiles all microservices.*

## 2. Infrastructure Check
Verify that all containers are running:
```powershell
docker ps
```
You should see:
- `discovery-service`
- `config-service`
- `gateway-service`
- `customer-service`
- `inventory-service`
- `billing-service`
- `postgres`

## 3. Application Health Check

### Service Discovery (Eureka)
- **URL**: [http://localhost:8761](http://localhost:8761)
- **Success Criteria**: Dashboard loads, showing all services (CUSTOMER-SERVICE, BILLING-SERVICE, etc.) with Status `UP`.

### API Gateway
- **URL**: [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)
- **Success Criteria**: Returns `{"status":"UP"}`

### Microservices via Gateway
- **Customer Service**: [http://localhost:8888/CUSTOMER-SERVICE/customers](http://localhost:8888/CUSTOMER-SERVICE/customers)
  - *Returns list of customers (initially empty or H2 data if seeded)*
- **Inventory Service**: [http://localhost:8888/INVENTORY-SERVICE/products](http://localhost:8888/INVENTORY-SERVICE/products)
- **Billing Service**: [http://localhost:8888/BILLING-SERVICE/bills](http://localhost:8888/BILLING-SERVICE/bills)

## 4. Database Verification
Connect to the Postgres container to verify persistence:
```powershell
docker exec -it <postgres-container-id> psql -U user -d bank_db
```
Inside SQL:
```sql
\dt
select * from customer;
```
