README.txt
Robert Dutile & Myo Min Thant

Our project is split into 3 packages.

First is the default package, which contains the main method, and one helper function.
The main method takes 2 filenames and the number of requested samples as arguments, reads the the files given, and constructs a Bayesian Network.
It also calls the two sampling classes and prints the results to the console.
The helper method getNodeNum() is used when reading the input file, and returns an index number for a particular node.

Second is the sampling package, which contains the two sampling classes LilelihoodWeight and RejectionSample.
Both contain the helper functions randNodeValue() (which returns a random double between 0 and 1, and is used as the basis for our randomization),
and normalize(), which takes a vector of counts for true and false and returns the normalized value of true. they also both have variables to store 
the name of the query variable and the bayesian network.
RejectionSample also contains the methods priorSample, priorSampleHelper, and rejectionSampling. rejectionSampling handles the sampling algorithm,
priorSample is a helper that provides the randomly generated samples, and priorSampleHelper is a helper for priorSample that handles recursion.
LikelihoodWeight additionally holds the functions likelihoodWeighting, weightedSample and findProb. likelihoodWeighting handles the likelihood weighting algorithm,
weightSample is a helper that provides weighted samples, and findProb finds the probability of a given node.

Third, there is the Structure package. In it, Node represents the nodes of the bayesian network, Table stores the conditional probability table within each node,
TableEntry represents individual entries in the table, and Event holds a HashMap of node names and assigned boolean values that is used for storing and passing along
samples.  Of them, TableEntry is the only one with any methods besides setters, getters and toString overrides, as it also has a helper function called binString,
which returns as string version of the class' binary id.


Importantly, when creating our algorithm, we used option B when it came to node reading.