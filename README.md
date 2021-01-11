
# Smartphone based Malaria diagnostics
[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.4432763.svg)](https://doi.org/10.5281/zenodo.4432763)

## Overview

This project aimed to provide a low-cost, reliable, user-friendly method for malaria diagnostics. It includes four main parts: 
* An Android App
*  A Alockchain network (based on [ Hyperledger Composer](https://hyperledger.github.io/composer/latest/))
*  Deeplearning models (based on [TensorFlow](https://www.tensorflow.org/)) 
*  A portable heater for LAMP-based Malaria tests. 

The Android App was designed to control the entire system, the blockchain network provided secured data storage, Deeplearning techniques were used to analyse the test results automatically and the heater could provide stable temperatures for the diagnostic assay. 

## Contents


```
.
├── Android
│   ├── Assay
│   └── README.md
├── Blockchian
│   ├── assay-tracking-network@0.0.2-deploy.28
│   ├── assay-tracking-network@0.0.2-deploy.28.bna
│   ├── README.md
│   └── test_report.html
├── DeepLearning
│   ├── CNN
│   ├── Object_detection
│   └── README.md
└── Heater
    ├── 3D models
    ├── Arduino Code
    ├── Circuit_diagram
    └── README.md
```

## User Guides and Executables
You can fined separate User Guides for installation and execution of the code in the individual README.md files for each component. Also each component does not contain an executable as you will need to compile for your platform (see versions and requirements in individual components) and update server references to get a working copy. 

The Android App does not contain a User Guide for the User interface interactions but the format of the User Interface is quite simple and self explanatory. Further information can be found on our related publication (link to appear soon).

The installation time of the code in this repository was not reported as it is not substantial (in the region of a few minutes). The majority of the installation time will be in setting up the environment including IDEs and Dependences. However this time is not quantified as it depends on equipment and user installation preferences. 

### Steps for installation and demo 
1. Install the Blockchain network
2. Obtain the relevant datasets from our [data repository]()
3. Follow the installation and training steps for Deeplearning
4. Follow the instructions for the Heater (this step requires following instructions to replicate purpose built hardware)
5. Install and run the Android App
 
## License

This project is licensed under the GNU GPL V3 Open Source License.
