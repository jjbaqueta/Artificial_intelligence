# Artificial_intelligence
In this repository there are some projects regarding artificial intelligence techniques. In particular, autonomous agents, genetic algorithms, fuzzy logic and neural networks.

<b>Fuzzy Classifier</b>

In this project a fuzzy classifier was implemented to solve a classification problem. The results from fuzzy classifier are compared against the results obtained from a multi-layer perceptron network (MLP). In particular, the neural network used in this project was built using the framework Weka (implemented in  Java). In this experiment, the classifiers are used to decide whether a money bill is genuine or fake according with some wavelet components. Below, it is shown a simple comparison between approaches:

- Fuzzy approach:

![fuzzy_inputs](https://user-images.githubusercontent.com/42413563/93220124-7caf8000-f742-11ea-8e9d-df5440b0b3b3.png)

Correctly classified instances: 240 -> 87.59%

Confusion Matrix:<br>
a	b		<-- classified as:<br>
154	31 | a = 0<br>
3	86 | b = 1<br>

- Neural Network apporach:

![neural_network](https://user-images.githubusercontent.com/42413563/93223150-204e5f80-f746-11ea-9e64-ba20a2c89b09.png)

Correctly classified instances: 273 -> 99.635%

Confusion Matrix:<br>
a	b		<-- classified as:<br>
144	0 | a = 0<br>
1	129 | b = 1<br>
