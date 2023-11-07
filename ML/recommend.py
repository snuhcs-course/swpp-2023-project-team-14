import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
import numpy as np

def min_max_scaling(embeddings, sigma_factor=0):
    min_val = np.min(embeddings)
    max_val = np.max(embeddings)
    normalized_embeddings = (embeddings - min_val) / (max_val - min_val)
    sigma = np.std(normalized_embeddings)
    scaled_embeddings = normalized_embeddings + sigma_factor * sigma * np.random.rand(len(embeddings))
    return scaled_embeddings

def get_recommend_items(userIdx, topN=5):
    userScores = similarityMatrix[userIdx]
    topItemIndices = userScores.argsort()[-topN:][::-1]
    recommendedItems = itemEmbeddingsDf.iloc[topItemIndices]
    return recommendedItems, userScores

def get_least_recommend_items(userIdx, bottomN=5):
    userScores = similarityMatrix[userIdx]
    bottomItemIndices = userScores.argsort()[:bottomN] 
    leastRecommendedItems = itemEmbeddingsDf.iloc[bottomItemIndices]
    return leastRecommendedItems, userScores

def get_recommend(userIdx, printAll = False):
    recommendedEvents, scores = get_recommend_items(userIdx,10)
    leastRecommendEvents, scores = get_least_recommend_items(userIdx,5)

    if printAll:
        print(userEmbeddingsDf.iloc[userIdx])
        print("\n","="*100)
        print("유저 관심행사\n")
        print(userEmbeddingsDf.iloc[userIdx]["관심 있는 축제(학술행사)"])
        print(userEmbeddingsDf.iloc[userIdx]["관심 있는 축제(공연행사)"])

        print("\n","="*100)
        print("추천행사\n")
        print(recommendedEvents["행사명"])
        print("\n","="*100)
        print("비추천행사\n")
        print(leastRecommendEvents["행사명"])
        print("\n","="*100)

    return recommendedEvents["행사명"].to_numpy()

def recover_scale(df, randomness=0):
    df = df.apply(lambda x: np.array(x.strip('[]').split()).astype(np.float64)) #recover embedding
    df = df.apply(lambda x: min_max_scaling(x, randomness)) # add randomness
    return np.vstack(df)

if __name__ == '__main__':
    userEmbeddingsDf = pd.read_csv('userEmbedding.csv')
    itemEmbeddingsDf = pd.read_csv('eventEmbedding.csv')

    
    #HYPER-PARAMETERS
    scalingVector = [0.05, 0.2, 0.3, 0.45]
    randomness = 0

    #RECOVER & ADD RANDOMNESS & SCALE EMBEDDING
    userkoBertEmbeddings = recover_scale(userEmbeddingsDf['koBertEmbedding'], randomness)
    itemkoBertEmbeddings = recover_scale(itemEmbeddingsDf['koBertEmbedding'], randomness)
    userkoElectraEmbeddings = recover_scale(userEmbeddingsDf['koElectraEmbedding'], randomness)
    itemkoElectraEmbeddings = recover_scale(itemEmbeddingsDf['koElectraEmbedding'], randomness)

    # TF-IDF
    userDescriptions = userEmbeddingsDf['description'].fillna('')  # Replace NaN values with empty strings
    itemDescriptions = itemEmbeddingsDf['description'].fillna('')

    tfidfVectorizer = TfidfVectorizer()
    userTfidfMatrix = tfidfVectorizer.fit_transform(userDescriptions)
    itemTfidfMatrix = tfidfVectorizer.transform(itemDescriptions)

    ## Item Type
    userFestivalInterest = userEmbeddingsDf["공연/축제 행사의 선호도"]/(userEmbeddingsDf["학술 행사의 선호도"] \
                                                             + userEmbeddingsDf["공연/축제 행사의 선호도"])
    userAcademicInterest = 1-userFestivalInterest
    oneHotItemType = pd.get_dummies(itemEmbeddingsDf['구분'], prefix='구분').values #[학술, 축제]
    userItemTypeInterest = np.array([userFestivalInterest.values, userAcademicInterest.values]).T

    ## COMBINE SIMILARITY
    scalingVector = np.array(scalingVector)/np.sum(scalingVector)
    
    tfidfSimilarityMatrix = cosine_similarity(userTfidfMatrix, itemTfidfMatrix)
    koElectraSimilarityMatrix = cosine_similarity(userkoElectraEmbeddings, itemkoElectraEmbeddings)
    koBertSimilarityMatrix = cosine_similarity(userkoBertEmbeddings, itemkoBertEmbeddings)
    typeSimilarity = cosine_similarity(userItemTypeInterest, oneHotItemType)
    
    similarityMatrix = (scalingVector[0] * tfidfSimilarityMatrix) + (scalingVector[1] * koElectraSimilarityMatrix) \
                        +(scalingVector[2] * koBertSimilarityMatrix) + (scalingVector[3]*typeSimilarity)


    ## Get List of recommendations for All Users
    allRecommends = [get_recommend(userIdx, False) for userIdx in range(len(userkoBertEmbeddings))]
    # allRecommends = np.asarray(allRecommends)