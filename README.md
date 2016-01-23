beard cli
=================================

Description //TODO

```
activator run
```


```

curl --header "Content-type: application/json"  --request POST --data '{"query": "find({\"type\":\"prayag\"})"}' http://localhost:9000/query

{"result":[{"type":"Product_Received","_id":"56807942fe95e549e40095d6"}]}
```

tests
-----
```
sbt test
```

uses
--------

https://github.com/prayagupd/vagrant-mongo2.4-puppet