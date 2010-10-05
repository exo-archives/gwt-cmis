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

package org.gwtcmis.service.object.event;


import org.gwtcmis.model.restatom.AtomEntry;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by The eXo Platform SAS.
 * 
 * Event is fired when object was moved
 * 
 * @author <a href="mailto:zhulevaanna@gmail.com">Anna Zhuleva</a>
 * @version $Id:
 */
public class ObjectMovedEvent extends GwtEvent<ObjectMovedHandler>
{
   /**
    * Type.
    */
   public static final GwtEvent.Type<ObjectMovedHandler> TYPE = new GwtEvent.Type<ObjectMovedHandler>();

   /**
    * Entry.
    */
   private AtomEntry entry;
   
   /**
    * @param entry entry
    */
   public ObjectMovedEvent(AtomEntry entry)
   {
      this.entry = entry;
   }
   
   /**
    * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
    * 
    * @param handler handler
    */
   @Override
   protected void dispatch(ObjectMovedHandler handler)
   {
      handler.onObjectMoved(this);
   }

   /**
    * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
    * 
    * @return Type {@link ObjectMovedHandler} 
    */
   @Override
   public Type<ObjectMovedHandler> getAssociatedType()
   {
      return TYPE;
   }

   /**
    * @return {@link AtomEntry}
    */
   public AtomEntry getEntry()
   {
      return entry;
   }
}
