# import packages
import random
import pathlib
from tensorflow.keras.preprocessing.image import ImageDataGenerator
import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.models import load_model
import cv2
import os
os.environ['KMP_DUPLICATE_LIB_OK']='True'
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"
# 


PATH = os.path.dirname('************enter the root path of image*************')

data_root = pathlib.Path(PATH)



#### check the lable and index
for item in data_root.iterdir():
  print(item)

# we provide 2 methods for testing the model

# Method1:
# The images need to be placed into the folders that named with image labels
all_image_paths = list(data_root.glob('*/*'))
all_image_paths = [str(path) for path in all_image_paths]
random.shuffle(all_image_paths)

image_count = len(all_image_paths)
print(image_count)

label_names = sorted(
     item.name for item in data_root.glob('*/') if item.is_dir())
print(label_names)

label_to_index = dict((name, index) for index, name in enumerate(label_names))
print(label_to_index)

all_image_labels = [label_to_index[pathlib.Path(path).parent.name]
                    for path in all_image_paths]

# print("First 10 labels indices: ", all_image_labels[:34])

class_names = ['1N2P','1P2N','DoublePositive', 'InValid', 'Negative']

# ##### process the image ###########
batch_size = 1000
IMG_HEIGHT =128
IMG_WIDTH = 128

test_image_generator = ImageDataGenerator(
                                                rescale=1./255,

                                                )

test_data = test_image_generator.flow_from_directory(batch_size=batch_size,
                                                              directory=data_root,
                                                              target_size=(IMG_HEIGHT, IMG_WIDTH),
                                                              class_mode='sparse')




######## load the model ################ 
model = load_model('************enter the path of the model**************')
model.summary()
test, labels = next(test_data)

test_loss, test_acc = model.evaluate(test_data)

print('\nTest accuracy:', test_acc)

predictions = model.predict(test)

l = labels.astype(int)
L = l.tolist()


################ plot functions ##################
def plot_image(i, predictions_array, true_label, img):
  predictions_array, true_label, img = predictions_array, true_label[i], img[i]
  plt.grid(False)
  plt.xticks([])
  plt.yticks([])

  plt.imshow(img, cmap = plt.cm.binary)

  predicted_label = np.argmax(predictions_array)
  if predicted_label == true_label:
    color = 'blue'
  else:
    color = 'red'

  plt.xlabel("{} {:2.0f}% \n ({})".format(class_names[predicted_label],
                                100*np.max(predictions_array),
                                class_names[true_label]),
                                color=color)


def plot_value_array(i, predictions_array, true_label):
  predictions_array, true_label = predictions_array, true_label[i]
  plt.grid(False)
  plt.xticks(range(5))
  plt.yticks([])
  thisplot = plt.bar(range(5), predictions_array, color="#777777")
  plt.ylim([0, 1])
  predicted_label = np.argmax(predictions_array)

  thisplot[predicted_label].set_color('red')
  thisplot[true_label].set_color('blue')

################## plot the image ##############
num_rows = 4
num_cols = 4
num_images = num_rows*num_cols
plt.figure(figsize=(2*2*num_cols, 2*num_rows))
for i in range(num_images):
  plt.subplot(num_rows, 2*num_cols, 2*i+1)
  plot_image(i, predictions[i], L, test)
  plt.subplot(num_rows, 2*num_cols, 2*i+2)
  plot_value_array(i, predictions[i], L)
plt.tight_layout()
plt.show()

# method 2
# If you want to test images without labels use the functions below

def plot_unkwon(i, predictions_array, img):
  predictions_array, img = predictions_array, img[i]
  plt.grid(False)
  plt.xticks([])
  plt.yticks([])

  plt.imshow(img, cmap=plt.cm.binary)

  predicted_label = np.argmax(predictions_array)

  color = 'black'

  plt.xlabel("{} {:2.0f}% ".format(class_names[predicted_label],
                                100*np.max(predictions_array)),
                                color=color)

# print(class_names[predicted_label1[:10]], 100*np.max(predictions_unknown[:10]))
def run_test(dir_test,num_rows1,num_cols1):
  PA = os.path.dirname(dir_test)
  data_root_f = pathlib.Path(PA)


  test_image_generator1 = ImageDataGenerator(rescale=1./255)

  im = test_image_generator1.flow_from_directory(batch_size=batch_size,
                                                                directory=data_root_f,
                                                                target_size=(IMG_HEIGHT, IMG_WIDTH),
                                                                class_mode='categorical')


  # label_names = sorted(
  #      item.name for item in data_root.glob('*/') if item.is_dir())
  #  print(label_names)

  # imag,ll = next(im)
  # data_root_f = list(data_root_f.glob('*/*'))

  # data_root_f = str(data_root_f)
  img = tf.io.read_file(dir_test)
  image= tf.image.decode_image(img)
  imag = tf.image.resize(image,[128,128])
  a=imag.numpy()
  a=a.sum()
  # a= (sum (image))/16384
  # a=imag.sum()
  print (a)
  imag = imag/255.0
  imag = np.expand_dims(imag,axis=0)
  a= a/49152
  print (a)
  predictions_unknown=model.predict(imag)
  # print("im is ", imag)
  # predicted_label1=np.argmax(predictions_unknown)
  # num_rows1=2
  # num_cols1=4
  num_images1=num_rows1*num_cols1
  plt.figure(figsize=(2*2*num_cols1, 2*num_rows1))
  for i in range(num_images1):
    plt.subplot(num_rows1, num_cols1, 1*i+1)
    plot_unkwon(i, predictions_unknown[i], imag)
  plt.tight_layout()
  plt.show()

#  Use run_test(dir_test,num_rows,num_cols) to test the images without label
# e.g.  run_test('C:/image/test',1,1)
run_test('*******path of the image(s)************',1,1)



