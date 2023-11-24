## Rewards API Design
#### JSON Response Object 

```
{
"id"="dcd3950a-9a99-4d5d-a628-e26367c3bd5b",
"timestamp"="1700202277986L",
"month"="OCTOBER",
"customer_id"="959549314",
"name"="Dave Ray",
"amount"="157.75"
},
{
"id"="UUID.randomUUID()",
"timestamp"="Long.valueOf(Instant.now().toEpochMilli())+String.valueOf('L')",
"month"="Month.of( Date.from( Instant.ofEpochMilli( Long.parseLong(timestamp.subSequence( 0,timestamp.length()-1),0,timestamp.length()-1,10) ) ).getMonth())",
"customer_id"="UUID.randomUUID().hashCode()",
"name"="Regina Hensley",
"amount"="364.35"
}
```