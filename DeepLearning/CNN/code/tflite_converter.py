import tensorflow as tf
from tensorflow.keras.models import Sequential, load_model
import os
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"
import numpy as np

#load the keras model
model = load_model('*************path to the model*************')#### here is the tf modle file


converter = tf.lite.TFLiteConverter.from_keras_model(model)
converter.optimizations = [tf.lite.Optimize.DEFAULT]
tflite_model = converter.convert()   ### define the converted model
#save the tf lite file
file = open( 'tflite_model.tflite' , 'wb' ) 
file.write( tflite_model )

# 