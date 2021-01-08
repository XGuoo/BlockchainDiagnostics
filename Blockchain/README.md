## Contents

```
.
├── Code
│   ├── assay-on-the-blockchain@0.0.2-deploy.30
│   └── assay-on-the-blockchain@0.0.2-deploy.30.bna
├── README.md
└── test_report.html

```
The Code directory contains the source code of the blockchain network. And the test_report.html is the performance test report generated by [Hyperledger Caliper](https://github.com/hyperledger/caliper).

## Installition
  * Environment: Ubuntu 18.04

* Download and install the dependences:
  ```
  curl -O https://hyperledger.github.io/composer/latest/prereqs-ubuntu.sh

  chmod u+x prereqs-ubuntu.sh

  sudo ./prereqs-ubuntu.sh
  ```
* Install the development environment
  1. Install the hyperledger component:
     ```
     npm install -g composer-cli@0.20

     npm install -g composer-rest-server@0.20

     npm install -g generator-hyperledger-composer@0.20

     npm install -g yo

     npm install -g composer-playground@0.20
     ```
  2. Install the [VSCode](https://code.visualstudio.com/download) IDE
   
  3. Install Hyperledger Fabric
        ```
        mkdir ~/fabric-dev-servers && cd ~/fabric-dev-servers

        curl -O https://raw.githubusercontent.com/hyperledger/composer-tools/master/packages/fabric-dev-servers/fabric-dev-servers.tar.gz

        tar -xvf fabric-dev-servers.tar.gz

        cd ~/fabric-dev-servers
        export FABRIC_VERSION=hlfv12
        ./downloadFabric.sh

        ```

  4. Starting Hyperledger Fabric
        ```
        cd ~/fabric-dev-servers
        export FABRIC_VERSION=hlfv12
        ./startFabric.sh
        ./createPeerAdminCard.sh
        ```
  5. Start the web app "Playground"(optional)
        ```
        composer-playground
        ```
  6. Deploy the "Assay-tracking-network"
     1. Install the network

        ```
        composer network install --card PeerAdmin@hlfv1 --archiveFile assay-tracking-network@0.0.2-deploy.30.bna 
        ```

     2. Start the network
        
        ```
        composer network start --networkName assay-tracking-network --networkVersion 0.0.2-deploy.28 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file networkadmin.card 
        ```

        > You might meet the error "```manifest for hyperledger/fabric-ccenv:latest not found```", that is because the Hyperledger Composer project has been deprecated and the tag "latest" no longer exist. This can be fixed with following commonds:
        ```
        docker pull hyperledger/fabric-ccenv:1.4.0

        docker tag hyperledger/fabric-ccenv:1.4.0 hyperledger/fabric-ccenv:latest
        ```

     3. Import the network administrator card and check the connection
        ```
        composer card import --file networkadmin.card

        composer network ping --card admin@tutorial-network
        ```

    7. Create the REST server
        ```
        composer-rest-server -c admin_analys@assay-tracking-network -n never -d n -w true 
        ```
> The installation steps above are only for running our "Assay-tracking-network". If you want to make your own network more details can be found [here](https://hyperledger.github.io/composer/latest/).

## User Guide

The Hyperledger composer supports REST API which allows normal HTTP requests. You can easily send request from the "Playground" App from https://localhost:8080 (default url), the REST API page https://localhost:3000 (default url) or using the Android App we developed. 

This step is a **dependency** for the Android App component in this repository. 