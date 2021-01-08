from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from datetime import datetime
import io
import itertools
from packaging import version
from six.moves import range

import tensorflow as tf
from tensorflow import keras

import seaborn as sns
from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt

import matplotlib as mpl
import numpy as np
import sklearn.metrics
import random
import pathlib
from tensorflow.keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.models import load_model
import cv2
import os


L=[3, 3, 2, 3, 1, 4, 3, 3, 0, 2, 2, 4, 0, 2, 3, 2, 3, 3, 1, 2, 4, 2, 4, 3, 0, 3, 0, 3, 1, 4, 2, 3, 3, 2, 2, 3, 2, 2, 3, 2, 4, 3, 2, 3, 1, 0, 2, 3, 4, 4, 4, 1, 3, 0, 3, 3, 3, 0, 1, 2, 3, 1, 4, 3, 3, 3, 2, 4, 2, 2, 0, 2, 1, 1, 2, 4, 3, 0, 1, 0, 0, 2, 4, 3, 2, 3, 1, 1, 1, 3, 4, 4]
p=[3, 3, 2, 3, 1, 4, 3, 3, 0, 2, 2, 4, 0, 2, 3, 2, 3, 3, 1, 2, 4, 2, 4, 3, 0, 3, 0, 3, 1, 4, 2, 3, 3, 2, 2, 3, 2, 2, 3, 2, 4, 3, 2, 3, 1, 0, 2, 3, 4, 4, 4, 1, 3, 0, 3, 3, 3, 0, 1, 2, 3, 1, 4, 3, 3, 3, 2, 4, 2, 0, 0, 2, 1, 1, 2, 4, 3, 0, 1, 0, 0, 2, 4, 3, 2, 3, 3, 1, 1, 3, 4, 4]

class_names =  ['1N2P','1P2N','DoublePositive', 'InValid', 'Negative']

def plot_confusion_matrix(cm, class_names):
  figure = plt.figure(figsize=(8, 8))
  im=plt.imshow(cm, interpolation='nearest', cmap=plt.cm.Blues)
  
  # plt.title("Confusion matrix",size=18, weight='bold')
  plt.colorbar(im,fraction=0.046, pad=0.04)

  tick_marks = np.arange(len(class_names))
  plt.xticks(tick_marks, class_names, rotation=45, size=10,weight='bold')
  plt.yticks(tick_marks, class_names, size=10,weight='bold')

  # Normalize the confusion matrix.
  cm = np.around(cm.astype('float') / cm.sum(axis=1)[:, np.newaxis], decimals=2)

  # Use white text if squares are dark; otherwise black.
  threshold = cm.max() / 2.
  for i, j in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
    color = "white" if cm[i, j] > threshold else "black"
    plt.text(j, i, cm[i, j], horizontalalignment="center", color=color, size=12)

  plt.tight_layout()
  plt.ylabel('True label', size=12,weight='bold')
  plt.xlabel('Predicted label',size=12,weight='bold')

  return figure

cm = sklearn.metrics.confusion_matrix(L, p)

figure = plot_confusion_matrix(cm, class_names=class_names)
# plt.show()

plt.savefig('cm1200.png',bbox_inches = 'tight',dpi=1200)