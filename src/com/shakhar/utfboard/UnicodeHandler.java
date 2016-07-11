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
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UnicodeHandler extends DefaultHandler {

    private HashMap<String, UnicodeBlock> map;

    private StringBuffer accumulator;
    private String blockName;
    private int blockMin;
    private int blockMax;
    private UnicodeBlock block;

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
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("codepoints")) {
            block = new UnicodeBlock(blockName, blockMin, blockMax);
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("name")) {
            blockName = accumulator.toString();
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("min")) {
            blockMin = Integer.parseInt(accumulator.toString(), 16);
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("max")) {
            blockMax = Integer.parseInt(accumulator.toString(), 16);
            accumulator.setLength(0);
        } else if(qName.equalsIgnoreCase("cp")) {
            block.addCodePoint(Integer.parseInt(accumulator.toString(), 16));
            accumulator.setLength(0);
        } else if (qName.equalsIgnoreCase("block")) {
            map.put(blockName, block);
        }
    }

    public HashMap<String, UnicodeBlock> getMap() {
        return map;
    }

}
