# embedded-caches-quarkus project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `embedded-caches-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/embedded-caches-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Deploying on Openshift
TODO: refactor the headless service into the deployment so that it happens automatically
You must deploy the headless serivce in https://github.com/jayhowell/embedded-caches-quarkus/blob/lambda_issue/src/main/resources/default-configs/headlessservice.yaml
```
oc project cacheappdemo
oc apply -f src/main/resources/default-configs/headlessservice.yaml
```
To build on openshift `./mvnw clean package -Dquarkus.container-image.build=true`
To deploy `./mvnw clean package -Dquarkus.kubernetes.deploy=true`

## Running the scripts
Once you deploy, you must set the api location of the service. Make sure you have the api on the end
```
export EP=http://embedded-caches-quarkus-cache.apps.cluster-4c57w.4c57w.sandbox2855.opentlc.com/api
./scripts/load.sh
./scripts/get.sh
```
