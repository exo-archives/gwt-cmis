/**
 *  Copyright (C) 2010 eXo Platform SAS.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gwtcmis.unmarshallers.parser;

import org.gwtcmis.client.CMIS;
import org.gwtcmis.model.restatom.AtomAuthor;
import org.gwtcmis.model.restatom.AtomContentType;
import org.gwtcmis.model.restatom.AtomEntry;
import org.gwtcmis.model.restatom.EntryInfo;
import org.gwtcmis.model.util.DateUtil;
import org.gwtcmis.rest.QName;



import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class AtomEntryParser
{
   /**
    * Constructor.
    */
   protected AtomEntryParser()
   {
      throw new UnsupportedOperationException(); // prevents calls from subclass
   }

   /**
    * Parse entry xml document to {@link AtomEntry}.
    * 
    * @param response response
    * @param entry entry
    */
   public static void parse(Node response, AtomEntry entry)
   {
      NodeList nodeList = response.getChildNodes();
      entry.setEntryInfo(getEntryInfo(nodeList));
      Node childrenNode = null;
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         Node item = nodeList.item(i);
         if (item.getNodeName().equals(CMIS.CMISRA_OBJECT))
         {
            entry.setObject(ObjectParser.parse(item));
         }
         else if (item.getNodeName().equals(CMIS.CMISRA_CHILDREN))
         {
            childrenNode = item;
         }
      }
      if (childrenNode != null)
      {
         // entry has children node - parse it's children
         setChildren(entry, childrenNode);
      }
   }

   /**
    * Parse children node and set children entries to it's parent.
    * 
    * @param parent parent
    * @param childrenNode children node
    */
   private static void setChildren(AtomEntry parent, Node childrenNode)
   {
      if (childrenNode.getChildNodes() != null && childrenNode.getChildNodes().getLength() > 0)
      {
         for (int i = 0; i < childrenNode.getChildNodes().getLength(); i++)
         {
            if (childrenNode.getChildNodes().item(i).getNodeName().equals(CMIS.FEED))
            {
               childrenNode = childrenNode.getChildNodes().item(i);
            }
         }
         // Go throw all nodes of feed to find entries (children of parent entry)
         for (int i = 0; i < childrenNode.getChildNodes().getLength(); i++)
         {
            Node node = childrenNode.getChildNodes().item(i);
            QName qName = new QName(node.getNodeName(), node.getNamespaceURI());
            if (CMIS.ATOM_ENTRY.equals(qName))
            {
               AtomEntry entry = new AtomEntry();
               parse(node, entry);
               parent.getChildren().add(entry);
            }
         }
      }
   }

   /**
    * @param nodeList node list
    * @return {@link EntryInfo}
    */
   public static EntryInfo getEntryInfo(NodeList nodeList)
   {
      EntryInfo entryInfo = new EntryInfo();
      int i = nodeList.getLength() - 1;
      //parse entry nodes to form entry info
      while ((i >= 0) && (nodeList.getLength() > 0))
      {
         Node item = nodeList.item(i);
         String value = (item.getFirstChild() == null) ? "" : item.getFirstChild().getNodeValue();
         
         QName qName = new QName(item.getNodeName(), item.getNamespaceURI());
         if (CMIS.ATOM_AUTHOR.equals(qName))
         {
            AtomAuthor author = AtomAuthorParser.parse(item);
            entryInfo.setAuthor(author);
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_CONTENT.equals(qName))
         {
            AtomContentType content = new AtomContentType();
            for (int k = 0; k < item.getAttributes().getLength(); k++)
            {
               if (item.getAttributes().item(k).getNodeName().equals(CMIS.SOURCE))
               {
                  content.setSource(item.getAttributes().item(k).getNodeValue());
               }
               else if (item.getAttributes().item(k).getNodeName().equals(CMIS.TYPE))
               {
                  content.setType(item.getAttributes().item(k).getNodeValue());
               }
            }
            entryInfo.setContent(content);
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_ID.equals(qName))
         {
            entryInfo.setId(value);
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_SUMMARY.equals(qName))
         {
            entryInfo.setSummary(value);
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_TITLE.equals(qName))
         {
            entryInfo.setTitle(value);
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_UPDATED.equals(qName))
         {
            entryInfo.setUpdated(DateUtil.parseDate(value));
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_PUBLISHED.equals(qName))
         {
            entryInfo.setPublished(DateUtil.parseDate(value));
            item.getParentNode().removeChild(item);
         }
         else if (CMIS.ATOM_LINK.equals(qName))
         {
            entryInfo.getLinks().add(AtomLinkParser.parse(item));
            item.getParentNode().removeChild(item);
         }
         i--;
      }
      return entryInfo;
   }

}
