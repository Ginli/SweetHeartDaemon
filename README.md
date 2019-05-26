Welcome to SweetHeartDaemon!

### Overview

This project is a very simple Lambda function, it will send a sweet message to a phone number.

Input is the target phone number you want to send. Message contents are randomly chosen from resources/sweet_words.txt.
You can add as many words as you like, one message per line!

You can also set up a CloudWatchEvent rule to trigger this function at a regular rate. That's what I did: it will send
a sweet message to my girlfriend everyday :)

### Dependencies

AWS Lambda, AWS SNS, AWS CloudWatch Event, what else?
