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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UnicodeBlockFactory {
    
    private HashMap<String, UnicodeBlock> blocksMap;
    
    private UnicodeBlockFactory() {
        try {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            UnicodeHandler handler = new UnicodeHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(getClass().getResourceAsStream("/res/unicode.xml")));
            blocksMap = handler.getMap();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UTFBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static UnicodeBlockFactory newInstance() {
        return new UnicodeBlockFactory();
    }
    
    public ArrayList<String> getBlockNames() {
        ArrayList<String> list = new ArrayList<>(blocksMap.keySet());
        Collections.sort(list);
        return list;
    }
    
    public UnicodeBlock getBlockByName(String blockName) {
        return blocksMap.get(blockName);
    }
}
