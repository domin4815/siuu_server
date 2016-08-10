# siuu_server

## Setup

1. Install Docker, version 1.12 or higher.

2. When in the project's main directory, go to docker-scripts directory.
```
$ cd docker-scripts
```

3. Set up the virtual machines (you have to define the number of virtual machines) - needed only for the first time.
```
$ bash vm-setup.sh <VMs-count>
```

4. If you want to modify the services' sources (skip the whole point if not)
    1. Create an account on [Docker Hub](https://hub.docker.com) (if you don't have one yet).
    2. Ask for being added to 'siuuproject' organization
    3. Run the building script to build all services (type your Docker Hub login and password when prompted).
```
$ bash services-build.sh
```

5. Deploy the docker images of the services on the VMs
```
$ bash services-deploy.sh
```

6. You can dynamically configure the number of instances of each service
```
$ docker service scale <service-ID>=<number-of-tasks>
```
e.g.
```
docker service scale events-service=2
```