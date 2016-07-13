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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Shakhar Dasgupta <shakhardasgupta@gmail.com>
 */
public class UnicodeBlockFactory {
    
    private HashMap<String, UnicodeBlock> blocksMap;
    
    private UnicodeBlockFactory() {
        blocksMap = new HashMap();
        try {
            initBlocksMap();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(UnicodeBlockFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static UnicodeBlockFactory newInstance() {
        return new UnicodeBlockFactory();
    }
    
    private void initBlocksMap() throws ParserConfigurationException, SAXException, IOException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(getClass().getResourceAsStream("/res/unicode.xml"));
        Element root = doc.getDocumentElement();
        NodeList blocks = root.getElementsByTagName("block");
        for(int i = 0; i < blocks.getLength(); i++) {
            Element block = (Element)blocks.item(i);
            String name = block.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
            int min = Integer.parseInt(block.getElementsByTagName("min").item(0).getFirstChild().getNodeValue(), 16);
            int max = Integer.parseInt(block.getElementsByTagName("max").item(0).getFirstChild().getNodeValue(), 16);
            System.out.println(name + ", " + min + ", " + max);
            UnicodeBlock unicodeBlock = new UnicodeBlock(name, min, max);
            Element codePoints = (Element)block.getElementsByTagName("codepoints").item(0);
            NodeList cps = codePoints.getElementsByTagName("cp");
            for(int j = 0; j < cps.getLength(); j++) {
                int cp = Integer.parseInt(cps.item(j).getFirstChild().getNodeValue(), 16);
                unicodeBlock.addCodePoint(cp);
            }
            blocksMap.put(name, unicodeBlock);
        }
        
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
