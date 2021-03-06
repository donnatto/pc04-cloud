# pc04-dsc-2020-1-donnatto

## Arquitectura

[![Arquitectura del Programa](architecture.png)](https://app.cloudcraft.co/view/13dc46e6-d60c-44df-813f-b33c5c49e1e2?key=jFP4513T1VEPE3VNVsRSWA)

## Indicaciones para compilar

### Configurar en variables de entorno:

- AWS_ACCESS_KEY
- AWS_SECRET_KEY
- AWS_REGION (formato ej: us-west-2)
- AWS_TABLE_NAME
- AWS_QUEUE_URL

### Metodos de Banking

**POST a http://localhost:8080/api/sqs/payment para colocar ordenes de pago en la cola con el siguiente Json como modelo (no se envia transactionId)**

INPUT
```json
{
    "documentNumber": "47525859",
    "firstName": "Edwin",
    "lastName": "Dominguez",
    "payment": "payment",
    "transactionAmount": "475.00",
    "transactionDate": "29-06"
        
}
```

OUTPUT
```
Order received
```

**GET a http://localhost:8080/api/sqs/payment para ver las ordenes de pago**

OUTPUT
```json
[
    {
        "transactionId": "c7a1cccf-051f-4e34-b1fe-889abc8154cd",
        "documentNumber": "47525859",
        "firstName": "Edwin",
        "lastName": "Dominguez",
        "payment": "payment",
        "transactionAmount": 475.0,
        "transactionDate": "29-06"
    },
    {
        "transactionId": "87073ae1-ff29-4ef2-b6eb-7d0173aea2dd",
        "documentNumber": "47525859",
        "firstName": "Edwin",
        "lastName": "Dominguez",
        "payment": "payment",
        "transactionAmount": 475.0,
        "transactionDate": "29-06"
    },
    {
        "transactionId": "f4dfb9ea-29cb-4e78-97db-56b03c5e543c",
        "documentNumber": "47525859",
        "firstName": "Edwin",
        "lastName": "Dominguez",
        "payment": "payment",
        "transactionAmount": 475.0,
        "transactionDate": "29-06"
    }
]

```

### Metodos de Accounting

**POST a http://localhost:8080/api/dynamo/process para procesar las ordenes de pago y subirlas a una tabla de DynamoDB.**

OUTPUT
```
Payments received: 3
```
