# offline-execution-engine
## Data driven testing framework
### Example of a restful request
This is an example test data which means to request youtub
* Json test data
* Assert with copy from http json response
* Support regex in assertion
```json
  {
    "name": "POC GET",
    "workflow": "APITest",
    "assets": [
      {
        "info": "---this is a demo for requesting YouTube restful api---",
        "request": {
          "method": "GET",
          "url": "https://www.googleapis.com/youtube/v3/search",
          "param": {
            "part": "snippet",
            "q": "ExecutionEngine+Cactus",
            "type": "video",
            "key": "AIzaSyBXpFaKcdrc3N70wxmO1Qj6tszmbaoR-GU"
          }
        },
        "assertions": {
          "StatusCode": 200,
          "ResponseJson": {
            "kind": "youtube#searchListResponse",
            "etag": "\"[\\s\\S]+\"",
            "regionCode": "TW",
            "pageInfo": {
              "totalResults": 0,
              "resultsPerPage": 5
            }
          }
        }
      }
    ]
  }
```
### Example of a loop request
Send request twice
```json
  {
    "name": "POC GET",
    "workflow": "APITest",
    "assets": [
      {
        "repeat": 2,
        "assets": [
          {
            "info": "---this is a demo for requesting YouTube restful api---",
            "request": {
              "method": "GET",
              "url": "https://www.googleapis.com/youtube/v3/search",
              "param": {
                "part": "snippet",
                "q": "ExecutionEngine+Cactus",
                "type": "video",
                "key": "AIzaSyBXpFaKcdrc3N70wxmO1Qj6tszmbaoR-GU"
              }
            }
          }
        ]
      }
    ]
  }
```
### Example of extracion from response
Use "extracion" key to extract value from response, and use in next test. Any formated string as "{{key}}" will be replace with the value from extractions.
```json
  {
    "name": "POC GET",
    "workflow": "APITest",
    "assets": [
      {
        "info": "---this is a demo for requesting YouTube restful api---",
        "request": {
          "method": "GET",
          "url": "https://www.googleapis.com/youtube/v3/search",
          "param": {
            "part": "snippet",
            "q": "ExecutionEngine+Cactus",
            "type": "video",
            "key": "AIzaSyBXpFaKcdrc3N70wxmO1Qj6tszmbaoR-GU"
          }
        },
        "extractions": {
          "etag": "etag"
        }
      },
      {
        "info": "---this is a demo for requesting YouTube restful api---",
        "request": {
          "method": "GET",
          "url": "https://www.googleapis.com/youtube/v3/search",
          "param": {
            "fakePram": "{{etag}}",
            "part": "snippet",
            "q": "ExecutionEngine+Cactus",
            "type": "video",
            "key": "AIzaSyBXpFaKcdrc3N70wxmO1Qj6tszmbaoR-GU"
          }
        }
      }
    ]
  }
```

### Example of JavaScript injection
Java script can be inject with format "[js[ 1 + 1 ]]", and can be used to assert a field in response, print something, or even sleep seconds. 
Combine with extraion feature will be a good pratice.
```json
      "assertions": {
          "StatusCode": 200,
          "ResponseJson": {
            "success": true,
            "message": "[js[print('delay for send gift');for(var t = Date.now();Date.now() - t <= 5000;);'']]",
            "error_code": 0,
            "results": {
              "diamond": "[js[ {{myDiamond}}-{{giftCost}} ]]",
              "usableBalance": {
                "userId": "{{userIdA}}",
                "diamond": "{{myBalance}}"
              }
            }
          }
        }
```
### Example of DB operation -- NoSql mongo
```json
{
    "name": "MongoFindOne",
    "workflow": "APITest",
    "assets": [
      {
        "info": "---Mongo Find---",
        "mongoOperation": {
          "query": {
            "table": "hostapplication",
            "criteria": {
              "userId": 5
            }
          }
        }
      }
    ]
  }
```
### Example of DB opration -- Cache DB Redis
_id will be transfer as DBObject
```Json
  {
    "name": "RedisGet",
    "workflow": "APITest",
    "assets": [
      {
        "info": "---POC for redis get---",
        "redisOperation": {
          "get": {
            "criteria": {
              "_id": "f34rgju789iuhgfd32wsxcfg",
              "try": "null"
            }
          }
        },
        "assertions": {
          "ResponseString": "[\\w]+"
        }
      }
    ]
  }
```

### Example of DB operation -- Sql Postgres
```json
  "SqlOperation": {
    "select": {
      "table": "user",
      "criteria": {
        "id": 1,
        "name": "leo"
      }
    }
  }
```

## api-test
framework for rest api testing

## carnival
worker repository

## engine-core
implementation of executeion engine

## engine-data
implemnttation of main bean for execution engine (TestData), extend TestData class to customize testing behavior

## testng-base
offline execution solution, testNG based, extend AbstractMainTest to customize execution behavior

## web-application
online execution solution, spingboot + mongo, still under developing

