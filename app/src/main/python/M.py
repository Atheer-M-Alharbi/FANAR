import pandas as pd
from os.path import dirname, join
from sklearn.model_selection import * 
from sklearn.feature_selection import RFE
from sklearn.metrics import * 
from sklearn.tree import DecisionTreeClassifier
 

def main(AutsmLevel, IqLevel, age, Perception):

    filename = join(dirname(__file__), "libs/FANAR_dataset.csv")
    dataset = pd.read_csv(filename, delimiter=",") 
    dataset = dataset.sample(frac = 1) 
    
    X= dataset.iloc[:,0:4]

    Y=dataset.iloc[:,4]
    
    model_DT =  DecisionTreeClassifier()
    rfe = RFE(model_DT,4)
    rfe= rfe.fit(X,Y)
     
    rfe= rfe.fit_transform(X, Y)

    xtrain_1,xtest_1,ytrain_1,ytest_1= train_test_split(rfe,Y,test_size=0.2,random_state=123,stratify=Y)
 
    model_DT= model_DT.fit(xtrain_1,ytrain_1)
    prediction_DT= model_DT.predict(xtest_1)

    scores_DT = cross_val_score(model_DT, xtrain_1, ytrain_1, cv=10, scoring='accuracy')*100
     
    Autsm_Level = int(AutsmLevel)  
    Iq_Level = int(IqLevel)  
    age_ = int(age)  
    Perception_ = int(Perception)  
        
    Xnew = [[Autsm_Level,Iq_Level,age_,Perception_]] 
 
    ynew = model_DT.predict(Xnew)
    result = int(ynew[0])

    return result
 


