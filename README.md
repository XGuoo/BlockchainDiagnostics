## Contents
```
.
├── CNN
│   ├── ConfusionMiatrix.png
│   ├── Images
│   ├── code
│   ├── model.h5
│   └── model.tflite
├── Object_detection
│   ├── TestImages
│   ├── TraingingImages
│   ├── annotation
│   ├── obj_results.xlsx
│   ├── pre-trained-models
│   ├── scripts
│   └── trained_model
└── README.md
```

We used two different approches in Deep Learning part, one is a convolutional neural network (CNN), which is simplier but faster, another is an object-detection model based on ResNet, which can provide more information. 

## Setting up the development environment

* For CNN
   1. Install python environment ([Anaconda](https://www.anaconda.com/) is recomended)
   2. Install Tensorflow
        ```
        pip install --upgrade tensorflow
        ```

* For Object Detection
   1. Install TensorFlow Object Detection API 
        ```
        git clone https://github.com/tensorflow/models.git
        ```
   2. Install Protobuf from [protoc repo](https://github.com/tensorflow/models.git)
   3. Install COCO API
        ```
        pip install cython

        pip install git+https://github.com/philferriere/cocoapi.git#subdirectory=PythonAPI
        ```
        >Visual C++ 2015 build tools must be installed and on your path
   4. Install the Object Detection API
        ```
        cp object_detection/packages/tf2/setup.py .
        python -m pip install .
        ```
   5. Install LabelImg
        ```
        pip install labelImg
        ```
   
## Model training

* CNN
    1. Prapering the dataset. 
     >The dataset for this study was placed in "```Image/TrainingImages```" directory, the dataset were devided to Training set and Validation set, and in each subset the images with different labels were placed into saperate folders.

    2. Run the Python code for training. 
     > The code for training is "```/code/main.py```". You need to change the "__Paths__" in the Python code to the directory on your computer. 

    >More information can be found on the [TensorFlow Official website](https://tensorflow-object-detection-api-tutorial.readthedocs.io/en/latest/install.html)
* Object Detection
    1. Praparing the dataset.
       1. Lable the Images with LabelImg software.
       2. Run the partition.py in "```/scripts/preprocess/```" folder to split the dataset to training set and test set.
            ```
            python partition_dataset.py -x -i [PATH_TO_IMAGES_FOLDER] -r 0.1
            ```
            >The dataset used in this study was placed in "```TrainingImage```" directory.

        3. Create Label Map
            Label Map is a file includes every label in your dataset and its id. E.g.
            ```
            item {
                id: 1
                name: 'cat'
            }

            item {
                id: 2
                name: 'dog'
            }
            ```
            > The lable map for this study is "```/annotation/label_map.pbtxt```"
        4. Create Tensorflow Records
   
            ```
            # Create train data:
            python generate_tfrecord.py -x [PATH_TO_IMAGES_FOLDER]/train -l [PATH_TO_ANNOTATIONS_FOLDER]/label_map.pbtxt -o [PATH_TO_ANNOTATIONS_FOLDER]/train.record

            # Create test data:
            python generate_tfrecord.py -x [PATH_TO_IMAGES_FOLDER]/test -l [PATH_TO_ANNOTATIONS_FOLDER]/label_map.pbtxt -o [PATH_TO_ANNOTATIONS_FOLDER]/test.record
            ```
            > You can use the script  "```/scripts/preprocess/generate_tfrecord.py```" with the commands above to do this.

        5. Start Training
           1. Create a new folder for the model, then copy and past the "```pipline.config```" file from a pre-trained model to the new folder.
           2. Edit the config file.
           3. Run the "```scripts/model_main_tf2.py```" by following command:
                ```
                #This is an example for starting the training process

                python model_main_tf2.py --model_dir=models/my_ssd_resnet50_v1_fpn --pipeline_config_path=models/my_ssd_resnet50_v1_fpn/pipeline.config
                ```

   
            >TensorFlow 2 provided many pre-trained models in [TensorFlow 2 Detection model zoo](https://github.com/tensorflow/models/blob/master/research/object_detection/g3doc/tf2_detection_zoo.md)

    >More details about training a custom Object Detection model with TensorFlow can be found [here](https://tensorflow-object-detection-api-tutorial.readthedocs.io/en/latest/install.html)

## Test the Model

You can easily test the performace of your model with the scripts provided in this repo. The only thing you need to do is editing "PATH" in the code by following the comments.


* CNN

    The test code for the CNN model was "```/CNN/code/test.py```".

* Object detection

    The test code can be found in "```/Object_detection/script/testmodel.py```".