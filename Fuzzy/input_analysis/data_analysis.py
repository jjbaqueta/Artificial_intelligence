import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

""" This file implements the data analysis for the money bills problem.
    In particular, the fuzzy classifier must be able to identify if a bill money is true or false.
    The fuzzy classifier was implemented in Java.
"""

# CONSTANTS ---------------------------

OFFSET = 0.2      # Constant value used to select overlapped values
FUZZY_RATE = 20   # Constant value that defines the number of fuzzy values that will be produced for analysis.

# FUCTIONS ---------------------------

""" Remove all elements from the list of activation (listT) that also be in the list of non activation (listF)
    @param listT: list of activation values
    @param listF: list of non activation values
    @param offset: range of overlap of values 
"""
def removeOverlaps(listT, listF, offset):
    overlaps = []

    # Finding overlaps
    for vF in listF:
        min = vF - (abs(vF) * offset)
        max = vF + (abs(vF) * offset)

        for vT in listT:
            if vT >= min and vT <= max:
                overlaps.append(vT)

    # Removing overlap values from listT
    for element in overlaps:
        if element in listT:
            listT.remove(element)

""" Round the values
    @param data: list of values.
"""
def rounding(data):
    for i in range(len(data)):
        data[i] = float("{0:.2f}".format(data[i]))

""" Execute the data analysis and show the result on screen
    @param listT: list of activation values
    @param listF: list of non activation values
    @param serieName: name of set of data
"""
def run(listT, listF, serieName):
    fig, axs = plt.subplots(1, 4, figsize=(12, 4), constrained_layout=True)
    rounding(listT)
    rounding(listF)

    # Original data
    axs[0].scatter([1] * len(listT), listT, label='True inputs')
    axs[0].scatter([0] * len(listF), listF, label='False inputs')
    axs[0].set_title('Original data')
    axs[0].set_xlabel('Output class (0: false, 1: true)')
    axs[0].set_ylabel(serieName)
    axs[0].legend()

    removeOverlaps(listT, listF, OFFSET)

    # Data without overlaps
    axs[1].scatter([1] * len(listT), listT, label='True inputs')
    axs[1].scatter([0] * len(listF), listF, label='False inputs')
    axs[1].set_title('Overlap remotion')
    axs[1].set_xlabel('Output class (0: false, 1: true)')
    axs[1].set_ylabel(serieName)
    axs[1].legend()

    # Data distribution
    axs[2].hist(listT, FUZZY_RATE, label='True inputs')
    axs[2].set_title('Distribution of true inputs')
    axs[2].set_xlabel(serieName)
    axs[2].set_ylabel('Frequency')
    axs[2].legend()

    # Generating fuzzy values
    n, bins = np.histogram(listT, FUZZY_RATE)
    values = np.array(bins).tolist()
    frequency = np.array(n).tolist()

    i = 0
    fuzzyValues = []
    nbValues = len(frequency) * 0.7

    while i < nbValues:
        index = frequency.index(max(frequency))
        frequency.pop(index)
        fuzzyValues.append(values.pop(index))
        i = i + 1

    fuzzyValues.sort()
    print('Values for ', serieName)
    print(fuzzyValues)

    axs[3].scatter(fuzzyValues, range(len(fuzzyValues)), label='True inputs', color='cyan')
    axs[3].set_title('Fuzzy values candidates')
    axs[3].set_ylabel('Index')
    axs[3].set_xlabel(serieName)
    axs[3].legend()

    for i, v in enumerate(fuzzyValues):
        axs[3].text(v, i, "{0:.2f}".format(v), fontsize=8)

    fig.suptitle('Data analysis', fontsize=16)

    fig.savefig("images/" + serieName + "_" + str(int(OFFSET * 100)) + ".png")
    plt.close()

# MAIN ---------------------------

varianceF = []
varianceT = []

skewnessF = []
skewnessT = []

curtosisF = []
curtosisT = []

entropyF = []
entropyT = []

# Read each line from input file.

table = open("datasets/full_dataset.txt", "r")
lines = table.readlines()

for line in lines:
    data = line.split(',')

    if int(data[4]) == 1:        
        varianceT.append(float(data[0]))
        skewnessT.append(float(data[1]))
        curtosisT.append(float(data[2]))
        entropyT.append(float(data[3]))
    else:
        varianceF.append(float(data[0]))
        skewnessF.append(float(data[1]))
        curtosisF.append(float(data[2]))
        entropyF.append(float(data[3]))

run(varianceT, varianceF, 'Variance')
run(skewnessT, skewnessF, 'Skewness')
run(curtosisT, curtosisF, 'Curtosis')
run(entropyT, entropyF, 'Entropy')
