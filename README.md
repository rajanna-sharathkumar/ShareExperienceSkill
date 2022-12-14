###How to create .jar from this package?
Run the following command 

```shell
mvn org.apache.maven.plugins:maven-assembly-plugin:2.6:assembly -DdescriptorId=jar-with-dependencies package
```

###Skill JSON Interaction Model

```json
{
  "interactionModel": {
    "languageModel": {
      "invocationName": "share experience",
      "intents": [
        {
          "name": "AMAZON.CancelIntent",
          "samples": []
        },
        {
          "name": "AMAZON.HelpIntent",
          "samples": []
        },
        {
          "name": "AMAZON.StopIntent",
          "samples": []
        },
        {
          "name": "ShareExperienceToUserIntent",
          "slots": [
            {
              "name": "user",
              "type": "AMAZON.SearchQuery"
            }
          ],
          "samples": [
            "share this",
            "yes share with {user}",
            "yeah share with {user}",
            "yes share it with {user}",
            "share it with {user}",
            "okay share it with {user}",
            "share this with {user}",
            "share this to {user}",
            "yes please share it to {user}",
            "yes share it to {user}",
            "share the experience",
            "share this experience",
            "yes share the experience",
            "yes I want to hear"
          ]
        },
        {
          "name": "AMAZON.NavigateHomeIntent",
          "samples": []
        },
        {
          "name": "AMAZON.FallbackIntent",
          "samples": []
        },
        {
          "name": "AMAZON.YesIntent",
          "samples": []
        },
        {
          "name": "SaveUserNameIntent",
          "slots": [
            {
              "name": "user",
              "type": "AMAZON.SearchQuery"
            }
          ],
          "samples": [
            "I am {user}",
            "My name is {user}"
          ]
        }
      ],
      "types": []
    }
  }
}
```