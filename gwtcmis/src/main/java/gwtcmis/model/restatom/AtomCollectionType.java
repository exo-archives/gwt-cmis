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

package gwtcmis.model.restatom;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by The eXo Platform SAS.
 * 
 * @author <a href="mailto:zhulevaanna@gmail.com">Anna Zhuleva</a>
 * @version $Id:
 */

public class AtomCollectionType
{
   /**
    * Href.
    */
   private String href;

   /**
    * Tittle.
    */
   private String tittle;

   /**
    * Accepts.
    */
   private List<AtomAcceptType> accepts;

   /**
    * @return the href
    */
   public String getHref()
   {
      return href;
   }

   /**
    * @param href
    *            the href to set
    */
   public void setHref(String href)
   {
      this.href = href;
   }

   /**
    * @return the tittle
    */
   public String getTittle()
   {
      return tittle;
   }

   /**
    * @param tittle
    *            the tittle to set
    */
   public void setTittle(String tittle)
   {
      this.tittle = tittle;
   }

   /**
    * @return the accepts
    */
   public List<AtomAcceptType> getAccepts()
   {
      if (accepts == null)
      {
         accepts = new ArrayList<AtomAcceptType>();
      }
      return accepts;
   }
  
}