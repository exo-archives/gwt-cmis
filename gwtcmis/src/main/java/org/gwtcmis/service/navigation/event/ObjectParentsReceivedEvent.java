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

package org.gwtcmis.service.navigation.event;

import org.gwtcmis.model.restatom.EntryCollection;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event is fired when the parent folder(s) for the specified non-folder, fileable object are received
 * 
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class ObjectParentsReceivedEvent extends GwtEvent<ObjectParentsReceivedHandler>
{

   /**
    * Type.
    */
   public static final GwtEvent.Type<ObjectParentsReceivedHandler> TYPE = 
      new GwtEvent.Type<ObjectParentsReceivedHandler>();
   
   /**
    * Entries.
    */
   private EntryCollection objectParents;
   
   /**
    * @param entries entries
    */
   public ObjectParentsReceivedEvent(EntryCollection entries)
   {
      this.objectParents = entries;
   }
   
   /**
    * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
    * 
    * @param handler handler
    */
   @Override
   protected void dispatch(ObjectParentsReceivedHandler handler)
   {
      handler.onObjectParentsReceived(this);
   }

   /**
    * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
    * 
    * @return Type {@link ObjectParentsReceivedHandler}
    */
   @Override
   public Type<ObjectParentsReceivedHandler> getAssociatedType()
   {
      return TYPE;
   }

   /**
    * @return {@link EntryCollection}
    */
   public EntryCollection getObjectParents()
   {
      return objectParents;
   }
}
