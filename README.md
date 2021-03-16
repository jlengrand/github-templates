# Github Templates

This repository was created for my talk at [Javaland 2021 about Voice apps](https://programm.javaland.eu/2021/#/scheduledEvent/606587).

The app presented here is an Alexa voice app that allows the user to create repositories from Github templates.
It was created using the [Alexa Skills Kit](https://developer.amazon.com/en-US/alexa/alexa-skills-kit)

## Structure : 

The structure of this repository is the typical structure of a Java generated ask app.

* _skill-package_ contains all the information about the skill itself, as well as the 'front-end' of the app
* _lambda_ contains the Java code and represents the 'back-end' of the app. It is uploaded as an AWS lambda.

Some interesting files : 

* [en-GB.json](skill-package/interactionModels/custom/en-GB.json) contains the JSON representation of my app model (basically its front-end).
* [CreateRepositoryIntentHandler.java](lambda/src/com/amazon/ask/githubtemplates/handlers/CreateRepositoryIntentHandler.java) is where the magic happens, and the repository is created based on user input.

## Running the code

This code is specifically wired to run on my environment. However, you can try it in a few steps.

* Make sure to be logged in the AWS CLI locally.
* In the [skill.json](skill-package/skill.json) file, empty out the "apis" object.
* Make sure that the [ask-resources.json](ask-resources.json) file "type" key contains your default AWS region.
* Once you have deployed the lambda using `ask deploy`, create the `GITHUB_LOGIN` and `GITHUB_OAUTH` environment variables so the lambda can interact with your Github account.
* If you want to be able to run [GithubDataGrabber](lambda/src/com/amazon/ask/githubtemplates/data/GithubDataGrabber.java), create a .githubconfig file in the root of the repository with the `GITHUB_LOGIN` and `GITHUB_OAUTH` values.

_note : You can find more information about the Github API [here](https://github-api.kohsuke.org/index.html)._

_note2: Using environment variables is fine for testing, but for actual production usage there are better ways to interact with the Github API._

## Slides of the talk

See [slides](slides/hey-google-javaland.pdf).

## Author

* [Julien Lengrand-Lambert](https://twitter.com/jlengrand)