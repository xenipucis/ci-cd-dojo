Feature: As a Docker donegal team, we need a way of managing the ninjas and help them to get up on their skills and feel
  challenged as they start growing. We'll then provide a service that enables a Ninjas CRUD with Belt and Dojo (region).

  Scenario Outline: As an application I'd like to have a rest endpoint for creating, reading, updating and deleting
    ninjas, so that in the next events we keep updating ninjas' belts based on their skills.

    Given The <ApplicationURL> is responsive
    When adding a <ninjaPayload> as a new ninja
    Then the added ninja should be listed

    Examples:
    | ApplicationURL        | ninjaPayload |
    | http://localhost:8080 | {"name": "Romero", "belt": {"color": "black"}, "dojo": {"name": "Donegal"}} |
    | http://localhost:8080 | {"name": "GMAN", "belt": {"color": "black"}, "dojo": {"name": "Dublin"}}    |