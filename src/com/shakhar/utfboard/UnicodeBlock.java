/*
 * Copyright (C) 2016 Shakhar Dasgupta <shakhardasgupta@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.shakhar.utfboard;

import java.util.ArrayList;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UnicodeBlock {
    
    private final String name;
    private final int min;
    private final int max;
    private final ArrayList<Integer> codePoints;
    
    UnicodeBlock(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.codePoints = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public int getMinimum() {
        return min;
    }
    
    public int getMaximum() {
        return max;
    }
    
    public void addCodePoint(int codePoint) {
        codePoints.add(codePoint);
    }
    
    public ArrayList<Integer> getCodePoints() {
        return codePoints;
    }
}
