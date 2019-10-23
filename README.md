# Account transfers simulator API

Tech stack:
- Guice
- Hibernate JPA
- Hibernate Validation
- Guice servlet integration

## API

### GET /account/{id}

200: 
{
  "id":uuid,
  "balance":double,
  "currency":[PLN|GBP]
}

### GET /transfer

200:
[{
  "id":uuid,
  "balance":double,
  "currency":[PLN|GBP]
}]

### GET /transfer/{transactionId}

200:
{
  "id":uuid,
  "balance":double,
  "currency":[PLN|GBP]
}

### POST /transfer
#### Body:
{
  "from":uuid, 
  "to":uuid, 
  "value":double, 
  "currency":[PLN|GBP]
}

202: transaction accepted

{
  "transactionId":uuid
}

400: validation error

{
  "errors" : [string]
}

406: insufficent funds

{
  "errors" : [string]
}


# Example

All account balances are being initialized by sql init script /resources/data.sql

## Send money

curl http://localhost:4200/transfer -d '{"from":"d47bf3a2-a0f7-489a-81ce-5a4835cc5ab8", "to":"428360c4-6d6b-47b4-a1f9-1b48c03bc073", "value":23, "currency":"GBP"}'

## List transactions

curl http://localhost:4200/transfer

## Get account balance

curl http://localhost:4200/account/d47bf3a2-a0f7-489a-81ce-5a4835cc5ab8