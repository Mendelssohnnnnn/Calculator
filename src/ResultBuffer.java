package com.company;

import java.util.LinkedList;

public class ResultBuffer {
        final int maxBufferLength = 10;
        int currentBufferLength = 0;
        int pointer = -1;

        LinkedList<String> buffer = new LinkedList<>();

        public void addElement(String ele) {
                buffer.addFirst(ele);
                if (currentBufferLength < maxBufferLength) {
                        currentBufferLength++;
                } else {
                        buffer.removeLast();
                }
        }

        public String getElement() {
                return buffer.get(pointer);
        }

        public void increasePointer() {
                pointer = (pointer + 1) % currentBufferLength;
        }

        public void resetPointer() {
                pointer = 0;
        }
}
