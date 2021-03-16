/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at
         http://aws.amazon.com/apache2.0/
     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.amazon.ask.githubtemplates.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.githubtemplates.handlers.github.GithubAPI;
import com.amazon.ask.githubtemplates.handlers.github.GithubInitException;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.intentName;

public class CreateRepositoryIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("CreateRepositoryIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Logger logger = Logger.getAnonymousLogger();

        // Not recommended in prod :)
        String username = System.getenv("GH_USERNAME");
        String key = System.getenv("GH_APIKEY");

        IntentRequest intentRequest = (IntentRequest) input.getRequestEnvelope().getRequest();
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();

        for (Map.Entry<String, Slot> entry : slots.entrySet()) {
            logger.log(Level.WARNING, entry.getKey() + ":" + entry.getValue().getValue() + ":" + entry.getValue().getName());
        }
        String speechText;

        try{
            GithubAPI api = new GithubAPI();

            String language = slots.get("language").getValue().toLowerCase();
            String title = slots.get("title").getValue().replace(" ", "-");

            api.getGithub().createRepository(title)
                    .fromTemplateRepository(
                            api.TEMPLATES.get(language).getValue0(),
                            api.TEMPLATES.get(language).getValue1()
                    ).create();

            speechText = "Great! I've created a " + language + " repository called " + title + " !";

        } catch (GithubInitException e) {
            speechText = "Sorry, you are not logged in! I cannot create new repositories";
        } catch (IOException e) {
            speechText = "Sorry, error when trying to create the repository. Please try again later";
            logger.log(Level.SEVERE, e.getMessage());
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .build();
    }

}
