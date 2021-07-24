# EyeOpener
As a part of a Microsoft hackathon, (https://www.linkedin.com/in/jagadeeshwaranv/)[Jaga], (https://www.linkedin.com/in/varsha-venkatakrishnan-80702117a/)[Varsha] and I developed an android application that helped align QR codes, read bills,identify currency notes and coins for the visually-impaired using a voice controlled navigation.<br><br>

## Technologies used
* Android
* Microsoft Custom Vision service
* Tensorflow

## Steps
* Data collection: We created a custom dataset of currency notes sourced from (https://data.mendeley.com/datasets/48ympv8jjf/1)[Mendeley datasets] and own photographs. The photographs of individual currency notes (denominations of Rs 5,10,20,50,100,500,2000) was pooled with the help of my classmates from CSE G2, Batch of 2019 - PSG College of Technology (Special thanks to them!)
* Data augmentation: We augmented the data by changing the brightness, contrast, lumination and rotation angles
* Model training: The model training phase happened using the Microsoft Custom Vision Service. We exported the trained model so that the app could be used *offline*.
* The exported model was incorporated into an android application with the frontend and backend being written in Java.
