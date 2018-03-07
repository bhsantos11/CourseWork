import twitter
import sys
import json
import time
import networkx
import operator
import pickle
from pathlib import Path
import matplotlib.pyplot as plt


def oauth_login():
    # XXX: Go to http://twitter.com/apps/new to create an app and get values
    # for these credentials that you'll need to provide in place of these
    # empty string values that are defined as placeholders.
    # See https://dev.twitter.com/docs/auth/oauth for more information 
    # on Twitter's OAuth implementation.
    
    CONSUMER_KEY = 'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'
    CONSUMER_SECRET = 'XXXXXXXXXXXXXXXXXXXXXXX'
    OAUTH_TOKEN = 'XXXXXXXXXXXXXXXXXXXXXXXXX'
    OAUTH_TOKEN_SECRET = 'XXXXXXXXXXXXXXXXXXXXXXXXXXXX'    
    auth = twitter.oauth.OAuth(OAUTH_TOKEN, OAUTH_TOKEN_SECRET, CONSUMER_KEY, CONSUMER_SECRET)
    twitter_api = twitter.Twitter(auth=auth)
    return twitter_api


twitter_api = oauth_login()

def parsedUserShow(ID):
        parsedUserShowDB = {}
        my_file = Path("parsedUserShowDB.txt")
        if my_file.is_file():
            with open("parsedUserShowDB.txt", "rb") as f:
                try:
                    parsedUserShowDB = pickle.load(f)
                except EOFError:
                    print("EOF Error, initializing empty DB")
                    
        if ID in parsedUserShowDB:
            return parsedUserShowDB[ID]
        else: #Should always be true when user not yet in DB
            UserResponseShow = twitter_api.users.show(user_id = ID)
         
            UserDumps = json.dumps(UserResponseShow, indent=1)
            UserResponseParsed = json.loads(UserDumps) #This is the User Data Dictionary
            parsedUserShowDB[ID] = UserResponseParsed
            with open("parsedUserShowDB.txt", "wb") as f:
                pickle.dump(parsedUserShowDB, f)
            return UserResponseParsed # type dict
#End of parsedUserShow

def top5(reciprocals_dict):
    top5Dict = {}

    #If there are 5 or less reciprocals the top 5 will be all of the reciprocals
    if(len(reciprocals_dict) <= 5 ):
        top5Dict = reciprocals_dict

    else:
        #figure out which of the 5 reciprocals have the most followers
        reciprocalsSorted = ( sorted( reciprocals_dict.items(), key=operator.itemgetter(1) ) ) # List of tuples sorted by follower count
        reciprocalsSorted.reverse() # We get the list sorted in ascending order but want it in descending so we can chose the first 5
        #First 5 pairs will be the top 5

        for i in range(5):
            id = reciprocalsSorted[i][0]
            followerCount = reciprocalsSorted[i][1]
            top5Dict.update({id : followerCount})

    return top5Dict
    

        

def dictionaryOfReciprocals(reciprocals_set):

    reciprocalsDict = {}
    for i in reciprocals_set:
        reciprocalData = parsedUserShow(i)
        reciprocalID = reciprocalData['id']
        reciprocalFollowersCount = reciprocalData['followers_count']
        reciprocalPair = {reciprocalID : reciprocalFollowersCount}
        reciprocalsDict.update(reciprocalPair)

    return reciprocalsDict


class socialNode():

    #Instance variables
    #twitter_id = 0 
    #twitter_handle = "NONE"
    #number_of_followers = 0
    #list_of_friends = [] Will be list of all friends as returned from Twitter API
    #dict_of_reciprocals = () dictionary of reciprocals
    #top_5 = {} # Will be a dictonary of the 5 reciprocals with most followers, 'id': number_of_followers

    

    def __init__(self, *args, twitter_id = 0, twitter_handle = "NONE"):
        
        my_file = Path("SocialNodesDB.txt")
        if my_file.is_file():
            with open("SocialNodesDB.txt", "rb") as f:
                try:
                    SocialNodesDB = pickle.load(f)
                except EOFError:
                    print("EOF Error, initializing empty DB")
                    SocialNodesDB = {}
        else:
            with open("SocialNodesDB.txt", "wb") as f:
                SocialNodesDB = {}
                pickle.dump(SocialNodesDB, f)

        if(twitter_id in SocialNodesDB):# In case we have seen this user node before and have it cached
            socialNodeDict = SocialNodesDB[twitter_id]
            
            #print(SocialNodesDB)
            UserResponseParsed = socialNodeDict["UserResponseParsed"]

            #Instantiates class variables on user data
            #TO-DO IMPLEMENT CACHE TO CACHE ALL THIS DATA
            self.twitter_id = UserResponseParsed['id'] #int

            self.twitter_handle = UserResponseParsed['screen_name'] # string
        
            self.number_of_followers = UserResponseParsed['followers_count'] # int 

            self.list_of_followers = socialNodeDict["list_of_followers"] # list

            self.list_of_friends = socialNodeDict["list_of_friends"]
        
            self.dict_of_reciprocals = dictionaryOfReciprocals(set(self.list_of_followers) & set(self.list_of_friends)) # dictionary of a set of reciprocals

            self.top5 = top5(self.dict_of_reciprocals) # dict { id : followerCount }

            #socialNodeInfo = {"UserResponseParsed" : UserResponseParsed, "list_of_followers" : self.list_of_followers,
            #                    "list_of_friends" : self.list_of_friends}

        else: # In case the user is new

            UserResponseParsed = parsedUserShow(twitter_id) 
   


            #Instantiates class variables on user data
            self.twitter_id = UserResponseParsed['id'] #int

            self.twitter_handle = UserResponseParsed['screen_name'] # string
        
            self.number_of_followers = UserResponseParsed['followers_count'] # int 

            self.list_of_followers = json.loads( json.dumps( twitter_api.followers.ids(user_id = self.twitter_id, count = 5000), indent = 1 ) )['ids'] # list

            self.list_of_friends = json.loads( json.dumps(twitter_api.friends.ids(user_id = self.twitter_id, count = 5000), indent = 1  ) )['ids'] # list
        
            self.dict_of_reciprocals = dictionaryOfReciprocals(set(self.list_of_followers) & set(self.list_of_friends)) # dictionary of a set of reciprocals

            self.top5 = top5(self.dict_of_reciprocals) # dict { id : followerCount }

            socialNodeDict = {"UserResponseParsed" : UserResponseParsed, "list_of_followers" : self.list_of_followers,
                                "list_of_friends" : self.list_of_friends}

            SocialNodesDB[self.twitter_id] = socialNodeDict

            with open("SocialNodesDB.txt", "wb") as f:
                pickle.dump(SocialNodesDB, f)

    #End of Init
    
    
    
        

    


#This will return a graph with our node in the middle and edges to the top 5
def top5Graph(originalNode):
        top5Dict = originalNode.top5

        top5Graph = networkx.Graph()
        originalNodeID = originalNode.twitter_id

        top5Graph.add_node(originalNodeID)

        for ID in top5Dict:
            top5Graph.add_node(ID)
            top5Graph.add_edge(originalNodeID, ID)

        return top5Graph



#This class is made to handle the Graph portion so it should not call the twitter API
class socialNetwork():

    #networkGraph = networkx.Graph()
    #initialNode = socialNode()

    def __init__(self, initialNode):

        #Instantiates initialNode
        self.initialNode = initialNode
        self.initialNodeID = initialNode.twitter_id
        
        self.visited = [] # This is not the id's of nodes in the graph, its the ID's of the nodes that we have already fetched the Top 5 for
       
        #Creates graph
        self.networkGraph = networkx.Graph()

        #Utilizing gpickle to save time in future runs
        self.gpickleFileName = str(self.initialNodeID) + ".gpickle"
        if(Path(self.gpickleFileName).is_file()):
            
            self.networkGraph = networkx.read_gpickle(self.gpickleFileName)
            print("Loaded pickle graph save with " + str(self.networkGraph.number_of_nodes())+ " nodes")

            #Saving visited
            try:
                with open((str(self.initialNodeID) + '.txt'), 'rb') as f:
                    self.visited = pickle.load(f)
            except:
                with open((str(self.initialNodeID) + '.txt'), 'wb') as f:
                    pickle.dump(self.visited, f)
            
            print("Loaded pickle visited list with " + str(len(self.visited)) + " visited nodes")

        else: # Adds initial node to graph
             self.networkGraph.add_node(self.initialNodeID) 
       
       
        


    def returnDiameter(self):
        return networkx.diameter(self.networkGraph)

    def returnAverageDistance(self):
        return networkx.average_shortest_path_length(self.networkGraph)

    # size and node must be passed as parameters
    # this is the crawler
    def expandNetwork(self, **kwargs):
        #Here is where we populate the graph with the top 5 of the top 5 until we reach around the size we want
        bfs_edges = networkx.bfs_edges(self.networkGraph, self.initialNodeID)
        bfs_edges_list = list(bfs_edges)
        print(bfs_edges_list)

        #Terminating recursion case, since our graph size is constant in all iterations we are checking 
        # the correct size, we'll endup with around 25 more nodes than askes
        
        while(self.networkGraph.number_of_nodes() < kwargs['size']):
            if((len(self.visited) > 0 and bfs_edges == 0)):
                print("Problem")
                break
            selectedNode = self.initialNode
            selectedNodeID = selectedNode.twitter_id

            #Determine next node via BFS
            bfs_edges = networkx.bfs_edges(self.networkGraph, self.initialNodeID)
            bfs_edges_list = list(bfs_edges)
            if(int(self.networkGraph.number_of_nodes()) != 0): #Always true except for first node
                
                for edge in bfs_edges_list:
                    #if node not in graph, that is selected node
                    endingNodeID = edge[1]
                    if(endingNodeID not in self.visited):
                        try:
                            selectedNode = socialNode(twitter_id = endingNodeID)
                            selectedNodeID = selectedNode.twitter_id
                        except: #This is normally triggered when the user has their profile set to private "not authorized" error
                            print("Could not use node " + str(endingNodeID) + " probably has profile set to private")
                            selectedNodeID = endingNodeID
                        break

            #To prevent double entries
            if(selectedNodeID not in self.visited):
                self.visited.append(selectedNodeID)

            #Get a graph from  selected node, initially our first node, max of 5 nodes
            selectedTop5Graph = top5Graph(selectedNode)

            # Add top 5 graph of selected node to our graph
            self.networkGraph = networkx.compose(self.networkGraph, selectedTop5Graph)

            print(selectedNodeID)
            print(self.networkGraph.number_of_nodes())

            #Writes to pickle to save time in future runs
            networkx.write_gpickle(self.networkGraph, self.gpickleFileName)
            with open((str(self.initialNodeID) + '.txt'), 'wb') as f:
                pickle.dump(self.visited, f) 
                
            time.sleep(2)
            
            
            #end of while loop

        

        #self.expandNetwork(size = kwargs['size'])
        #end of expandNetwork
        #THIS SHOULD NOT BE RECURSION
    
    def networkDraw(self):
        networkx.draw(self.networkGraph, with_labels=True, font_weight="bold")
        plt.show()



if __name__ == '__main__':
    print('THIS SHOULD NOT BE MAIN AND DOES NOTHING, ITS A MODULE TO IMPORT TO MAIN MODULE')
