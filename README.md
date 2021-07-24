# EyeOpener
As a part of a Microsoft hackathon, [Jaga](https://www.linkedin.com/in/jagadeeshwaranv/), [Varsha](https://www.linkedin.com/in/varsha-venkatakrishnan-80702117a/) and I developed an android application that helped align QR codes, read bills, identify currency notes and coins for the visually-impaired using a voice controlled navigation.<br><br>

## Technologies used
* Android
* Microsoft Custom Vision service
* Tensorflow

## Steps
* Data collection: We created a custom dataset of currency notes sourced from [Mendeley dataset](https://data.mendeley.com/datasets/48ympv8jjf/1) and own photographs. The photographs of individual currency notes (denominations of Rs 5,10,20,50,100,500,2000) was pooled with the help of my generous classmates from CSE G2, Batch of 2019 - PSG College of Technology (Special thanks to them!)
* Data augmentation: We augmented the data by changing the brightness, contrast, lumination and rotation angles.
* Model training: The model training phase happened using the Microsoft Custom Vision Service. The trained model is exported offline.
* The exported model was incorporated into an android application with the frontend and backend being written in Java.

## Voice commands
The app recognizes a predefined set of voice commands:
* For currency identification Module: Voice commands should contain "currency" or "money"
* For coin identification: Voice commands should contain "coin"
* For QR code identification and alignment: Voice commands should contain "qr"
* For bill reader: Voice command should contain "bill" or "receipt"
* For help: Voice commands should contain "help"
* For knowing the creators: You can ask the app "Who created you?"

## Demo
[![YouTube demo](https://img.youtube.com/vi/GQrzBC7pMx8/0.jpg)](https://www.youtube.com/watch?v=GQrzBC7pMx8)
* [YouTube demo](https://www.youtube.com/watch?v=GQrzBC7pMx8)
* Run it on your own - [apk](https://github.com/balajimt/EyeOpener/blob/master/EyeOpener.apk)

