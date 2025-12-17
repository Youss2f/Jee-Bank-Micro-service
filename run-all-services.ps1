# run-all-services.ps1
Write-Host "STARTING E-COMMERCE MICROSERVICES..." -ForegroundColor Green

# 1. Start Config Service
Write-Host "[1/6] Starting Config Service..."
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f config-service/pom.xml" -NoNewWindow
Start-Sleep -Seconds 40

# 2. Start Discovery Service
Write-Host "[2/6] Starting Discovery Service..."
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f discovery-service/pom.xml" -NoNewWindow
Start-Sleep -Seconds 20

# 3. Start Gateway Service
Write-Host "[3/6] Starting Gateway Service..."
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f gateway-service/pom.xml" -NoNewWindow
Start-Sleep -Seconds 5

# 4. Start Business Services
Write-Host "[4/6] Starting Customer, Inventory, and Billing Services..."
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f customer-service/pom.xml" -NoNewWindow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f inventory-service/pom.xml" -NoNewWindow
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f billing-service/pom.xml" -NoNewWindow
Start-Sleep -Seconds 10

# 5. Start AI Services
# Write-Host "[5/6] Starting Chatbot and MCP Services..."
# Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f mcp-server/pom.xml" -NoNewWindow
# Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run -f chatbot-service/pom.xml" -NoNewWindow

Write-Host "ALL SERVICES LAUNCHED."
Write-Host "Eureka Dashboard: http://localhost:8761"
Write-Host "Gateway API: http://localhost:8888"
