# Docker Donegal - Ninjas Belt

## Prerequisite
- This project assumes you're using Docker 18+
```
mongo --host=ninja.dockerdonegal.ie:27017 -u ninja -p ninja ninja
```


```
POST: http://localhost:8080/ninja/save

```


```
{
	"name": "Romero",
	"belt": {
		"color": "white"
	},
	"dojo": {
		"name": "Donegal"
	}
}
```

```
{
   	"name": "Gearoid",
   	"belt": {
   		"color": "white"
   	},
   	"dojo": {
   		"name": "Dublin"
   	}
}
```
```
git flow init //accept defaults
git flow feature start add-unit-tests
git branch // to check which branch you are in

```

Follow documentation of jenkins (pipelines) at https://jenkins.io/doc/book/pipeline/docker/