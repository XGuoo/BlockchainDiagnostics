#import pakages
from __future__ import absolute_import, division, print_function, unicode_literals
import tensorflow as tf

from tensorflow import keras
import datetime
from tensorflow.keras.models import Sequential, load_model
from tensorflow.keras.layers import Dense, Conv2D, Flatten, Dropout, MaxPooling2D,   GlobalAveragePooling2D
from tensorflow.keras.preprocessing.image import ImageDataGenerator

import os
os.environ["CUDA_VISIBLE_DEVICES"] = "-1"
import numpy as np
import matplotlib.pyplot as plt

#load images

PATH = os.path.dirname('*************enter the main path of train and valiation data ***********')
print (PATH)

train_dir = os.path.join(PATH, 'Tra')
validation_dir = os.path.join(PATH, 'Val')

train_1n2p_dir = os.path.join(train_dir, '1N2P')  
train_1p2n_dir = os.path.join(train_dir, '1P2N')  #
train_dp_dir = os.path.join(train_dir, 'DoublePositive')  # 
train_ng_dir = os.path.join(train_dir, 'Negative')  # 
train_iv_dir = os.path.join(train_dir, 'InValide')  # 

validation_1n2p_dir = os.path.join(validation_dir, '1N2P')  # 
validation_1p2n_dir = os.path.join(validation_dir, '1P2N')  # 
validation_dp_dir = os.path.join(validation_dir, 'DoublePositive')  # 
validation_ng_dir = os.path.join(validation_dir, 'Negative')  # 
validation_iv_dir = os.path.join(validation_dir, 'InValide')  #

#count the number of images
num_1n2p_tr = len(os.listdir(train_1n2p_dir))
num_1p2n_tr = len(os.listdir(train_1p2n_dir))
num_doublep_tr = len(os.listdir(train_dp_dir))
num_negative_tr = len(os.listdir(train_ng_dir))
num_iv_tr = len(os.listdir(train_iv_dir))


num_1n2p_val = len(os.listdir(validation_1n2p_dir))
num_1p2n_val = len(os.listdir(validation_1p2n_dir))
num_doublep_val = len(os.listdir(validation_dp_dir))
num_negative_val = len(os.listdir(validation_ng_dir))
num_iv_val = len(os.listdir(validation_iv_dir))


total_train = num_1n2p_tr + num_1p2n_tr + num_doublep_tr + num_negative_tr + num_iv_tr
total_val = num_1n2p_val + num_1p2n_val + num_doublep_val +num_negative_val +num_iv_val


print("Total training images:", total_train)
print("Total validation images:", total_val)


#setting the variables in model

batch_size = 32
epochs = 60
IMG_HEIGHT = 150
IMG_WIDTH = 150


# This function will plot images.
def plotImages(images_arr):
    fig, axes = plt.subplots(2, 5, figsize=(20,20))
    axes = axes.flatten()
    for img, ax in zip( images_arr, axes):
        ax.imshow(img)
        ax.axis('on')
        ax.grid('on')
    plt.tight_layout()
    plt.show()

# Generator for training data
train_image_generator = ImageDataGenerator(
    rescale=1./255,
    channel_shift_range=5,
    horizontal_flip=False,
    height_shift_range=0.05,
    width_shift_range=0.02,
    shear_range=0.1,
    zoom_range=0.15,
    rotation_range=2,
    # fill_mode='nearest',
    brightness_range=[0.75, 1.0])


# Generator for validation data   
validation_image_generator = ImageDataGenerator(
    rescale=1./255,
    channel_shift_range=5,
    horizontal_flip=False,
    height_shift_range=0.05,
    width_shift_range=0.02,
    shear_range=0.1,
    zoom_range=0.15,
    rotation_range=2,
    # fill_mode='nearest',
    brightness_range=[0.75, 1.0]
)

train_data_gen = train_image_generator.flow_from_directory(
    batch_size=batch_size,
    directory=train_dir,                                      
    shuffle=True,
    target_size=(IMG_HEIGHT, IMG_WIDTH),
    class_mode='sparse')

val_data_gen = validation_image_generator.flow_from_directory(batch_size=batch_size,
                                                              directory=validation_dir,
                                                              target_size=(IMG_HEIGHT, IMG_WIDTH),
                                                              class_mode='sparse')


# sample_training_images, _ = next(train_data_gen)
# plotImages(sample_training_images[:10])
# augmented_images = [train_data_gen[0][0][0] for i in range(10)] 
# plotImages(augmented_images)


###############definition of the model


############################ uncomment below if you want to start a new training process ##########################
model = Sequential([
    Conv2D(16, 3, padding='same', activation='relu', input_shape=(IMG_HEIGHT, IMG_WIDTH ,3)),
    MaxPooling2D(),
    Conv2D(32, 3, padding='same', activation='relu'),
    MaxPooling2D(),
    Conv2D(64, 3, padding='same', activation='relu'),
    MaxPooling2D(),
    Conv2D(128, 3, padding='same', activation='relu'),
    MaxPooling2D(),  
    Flatten(),
    Dense(128, activation='relu'),
    Dropout(0.5),
    Dense(128, activation='relu'),
    Dropout(0.5),
    Dense(128, activation='relu'),
    Dropout(0.5),
    Dense(5, activation='softmax')   
])


# complie the model
model_new.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

# model_new = load_model('*************load pretrained model******************') #########comment this line if you want to start a new training process
model.summary()


#tensorboard callback
log_dir="logs/fit/" + datetime.datetime.now().strftime("%Y%m%d-%H%M%S")
tensorboard_callback = tf.keras.callbacks.TensorBoard(log_dir=log_dir, histogram_freq=1)


#fit the model
history = model_new.fit_generator(
    train_data_gen,
    steps_per_epoch=total_train // batch_size,
    epochs=epochs,
    validation_data=val_data_gen,
    validation_steps=total_val // batch_size,
    callbacks=[tensorboard_callback]
)

acc = history.history['accuracy']
val_acc = history.history['val_accuracy']

loss = history.history['loss']
val_loss = history.history['val_loss']

epochs_range = range(epochs)

#plot the results
plt.figure(figsize=(8, 8))
plt.subplot(1, 2, 1)
plt.plot(epochs_range, acc, label='Training Accuracy')
plt.plot(epochs_range, val_acc, label='Validation Accuracy')
plt.legend(loc='lower right')
plt.title('Training and Validation Accuracy')

plt.subplot(1, 2, 2)
plt.plot(epochs_range, loss, label='Training Loss')
plt.plot(epochs_range, val_loss, label='Validation Loss')
plt.legend(loc='upper right')
plt.title('Training and Validation Loss')
plt.show()

# save the model
model_new.save('***********************enter the path you want to save the model/name of the model.h5************')