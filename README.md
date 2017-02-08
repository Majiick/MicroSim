[![Video](http://img.youtube.com/vi/7j_amXT9Hwc/0.jpg)](https://www.youtube.com/watch?v=7j_amXT9Hwc)
^ youtube vid

# Explanation
This is a simulation of microorganisms that evolve in order to collect as much food as possible every round.

The microorganisms have a grid of 7x7 cells, which can be filled with 3 components; a driver, a rotator or a cytoplasm.

The rotator rotates the cell, the driver moves it forward an the cytoplasm can consume the food when it touches it.

The rotators and drivers have triggers on them, which will trigger the action of rotating or moving the cell forward.

There are 3 triggers; edge, chrono, and eye. The eye will trigger an action if there is food in front of the component, the chrono trigger simply triggers a component every x frames, and the edge trigger triggers when a component goes out of the map.

The components themselves have random values, for example one driver might only move 20 units forward, while another might move 200.


The cells randomly mutate components every round. If the mutations turned out to be for the worst, then it will revert back and mutate again from the last state.

The states are all saved in an .xml file as way of serializing the grid states so we can revert back to them if mutations turned out bad.

# Code To Note
    - Polymoprhism, Inheritance and interfaces are all included.
    - Lambas and filters used on collections.
    - I/O and serialization done through saving organism states to an XML file.
    - The class structure is pretty bad and messy.
    - Some duplicated code in classes extending IComponent.
    - Start() in triggers isn't used, should be but there's a kerfuffle with the constructors.
    - XML class should be a singleton.
    - There should be a global static singleton Info class or whatever that I can get an instance of processing from, instead of passing it around everywhere.
    - I feel like there's a way to cut a lot of boilerplate in XML, but can't put my finger on it.
    - Especially smelly code in XML with the switches on instanceof and using the enums.
    - The constructors on the triggers are a mess.
