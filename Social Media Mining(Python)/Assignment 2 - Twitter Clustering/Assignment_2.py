import sys
import json
import networkx
import twitter

from TwitterClusteringModule import *


#My id 768231590187134976
#SUCampus id 407922176

myNode = socialNode(twitter_id= 768231590187134976)

print(myNode.top5)

myID = myNode.twitter_id

myNetwork = socialNetwork(myNode)

print(myNetwork.networkGraph.number_of_nodes())
desired_size = 100#  input("How many nodes in in your graph?")
myNetwork.expandNetwork(size = int(desired_size))

  

average_distance = myNetwork.returnAverageDistance()
diameter = myNetwork.returnDiameter()

print("Your graph has an average distance of: ")
print(average_distance)
print("Your graph has a diameter: ")
print(diameter)

save = open((str(myID)+".txt"), "w")
save.write("Central node ID: " + str(myID))
save.write("Average Distace: " + str(average_distance))
save.write("Diameter: " + str(diameter))

draw = input("Would you like a display of the network?")
if(draw[0].lower() == 'y'):
    myNetwork.networkDraw()




if __name__ == '__main__':
    print('Module booted as main')
