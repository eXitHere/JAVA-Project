/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dfts;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thana
 */
class MyMap<K,V> extends HashMap<K, V>{
    Map<V,K> reverseMap = new HashMap<V,K>();

    @Override
    public V put(K key, V value) {
        // TODO Auto-generated method stub
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    public K getKey(V value){
        return reverseMap.get(value);
    }
}
