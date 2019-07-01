/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybernostics.jsp2thymeleaf.api.util;

import java.util.Map.Entry;

/**
 *
 * @author jason
 */
public class EntryUtils
{

    public static <K, V> Entry<K, V> entryOf(K key, V value)
    {
        return new BasicEntry<K, V>(key, value);
    }

    public static class BasicEntry<K, V> implements Entry<K, V>
    {

        private K key;
        private V value;

        public BasicEntry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey()
        {
            return key;
        }

        @Override
        public V getValue()
        {
            return value;
        }

        @Override
        public V setValue(V value)
        {
            this.value = value;
            return this.value;
        }

    }
}
