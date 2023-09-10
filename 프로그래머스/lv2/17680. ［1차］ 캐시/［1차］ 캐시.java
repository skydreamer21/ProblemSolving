import java.util.*;

class Solution {
    static Cache cache;
    
    static class City implements Comparable<City> {
        String city;
        int order;
        
        public City(String city, int order) {
            this.city = city;
            this.order = order;
        }
        
        @Override
        public int compareTo(City o) {
            return this.order - o.order;
        }
    }
    
    static class Cache {
        Map<String, Integer> cities;
        PriorityQueue<City> pq;
        int lastOrder;
        int size;
        
        public Cache(int size) {
            cities = new HashMap<>();
            pq = new PriorityQueue<>();
            lastOrder = 0;
            this.size = size;
        }
        
        public int get(String city) {
            // 캐시가 없을 때
            if (size == 0) {
                return 5;
            }
            
            // 캐시 미스일때
            if (!cities.containsKey(city)) {
                // 캐시가 덜 찼을 때
                if (cities.size() < size) {
                    pq.add(new City(city, lastOrder));
                    cities.put(city, lastOrder++);
                    return 5;
                } else { // 캐시가 다 찼을 때
                    while(true) {
                        City temp = pq.poll();
                        if (cities.get(temp.city) == temp.order) {
                            cities.remove(temp.city);
                            pq.add(new City(city, lastOrder));
                            cities.put(city, lastOrder++);
                            break;
                        }
                    }
                    return 5;
                }
            } else { // 캐시 히트힐 때
                pq.add(new City(city, lastOrder));
                cities.put(city, lastOrder++);
                return 1;
            }
        }
    }
    public int solution(int cacheSize, String[] cities) {
        cache = new Cache(cacheSize);
        int time = 0;
        for (String city : cities) {
            time += cache.get(city.toLowerCase());
        }
        return time;
    }
}