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

import java.util.HashMap;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UnicodeHandler extends DefaultHandler {

    private HashMap<String, Range> map;

    private StringBuffer accumulator;
    private String blockName;
    private int rangeStart;
    private int rangeEnd;

    @Override
    public void startDocument() {
        map = new HashMap<>();
        accumulator = new StringBuffer();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        accumulator.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("name")) {
            blockName = accumulator.toString();
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("start")) {
            rangeStart = Integer.parseInt(accumulator.toString(), 16);
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("end")) {
            rangeEnd = Integer.parseInt(accumulator.toString(), 16);
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("block")) {
            map.put(blockName, new Range(rangeStart, rangeEnd));
        }
    }

    public HashMap<String, Range> getMap() {
        return map;
    }

}
