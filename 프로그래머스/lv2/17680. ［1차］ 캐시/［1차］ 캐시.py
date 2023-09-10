from collections import deque

def solution(cacheSize, cities):
    answer = 0
    cache = deque([])
     
    for city in cities:
        if city.lower() in cache:
            answer += 1
            cache.remove(city.lower())
            cache.append(city.lower())
            
        else:
            cache.append(city.lower())
            answer += 5
            
        if (len(cache) > cacheSize):
            cache.popleft()
        
    return answer