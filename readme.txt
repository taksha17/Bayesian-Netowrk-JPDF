Author Name : Taksha S Thosani
UTA ID : 1002086312
Email : txt6312@mavs.uta.edu

Bayesian Network Inference
This program performs Bayesian network inference on a dataset and calculates the joint probability distribution for a given set of variables.

Compile the program using the command javac BNet.java

Run the program using the command java BNet <training_data> <Bt/Bf> <Gt/Gf> <Ct/Cf> <Ft/Ff>

<training_data> is the path to the file containing the training data.

<Bt/Bf> <Gt/Gf> <Ct/Cf> <Ft/Ff> are the values for the variables B, G, C, and F respectively. Use Bt for B=true, Bf for B=false, and so on.

The program uses the following formulas to calculate the probabilities:

P(B): the probability of B being true
P(G|B): the probability of G being true given B is true
P(C): the probability of C being true
P(F|G,C): the probability of F being true given G and C are true
The joint probability distribution is calculated as follows:

P(B, G, C, F) = P(B) * P(G|B) * P(C) * P(F|G,C)
The program then prints out the probabilities and the joint probability distribution.

Example Usage
java BNet data.txt Bt Gt Ct Ft

This will calculate the probabilities and the joint probability distribution for the values B=true, G=true, C=true, and F=true, using the data in data.txt.

Sample compilation and code running snapshots are attached in the Archieved file as well.

References :-

