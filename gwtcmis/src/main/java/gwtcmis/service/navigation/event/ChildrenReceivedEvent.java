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

package gwtcmis.service.navigation.event;

import gwtcmis.model.restatom.EntryCollection;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event is fired when list of child objects contained in the specified folder is received
 * 
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class ChildrenReceivedEvent extends GwtEvent<ChildrenReceivedHandler>
{
   /**
    * Type.
    */
   public static final GwtEvent.Type<ChildrenReceivedHandler> TYPE = 
      new GwtEvent.Type<ChildrenReceivedHandler>();

   /**
    * Folder's children.
    */
   private EntryCollection children;

   /**
    * @param entries entries
    */
   public ChildrenReceivedEvent(EntryCollection entries)
   {
      this.children = entries;
   }

   /**
    * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
    * 
    * @param handler handler
    */
   @Override
   protected void dispatch(ChildrenReceivedHandler handler)
   {
      handler.onChildrenReceived(this);
   }

   /**
    * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
    * 
    * @return Type {@link ChildrenReceivedHandler}
    */
   @Override
   public Type<ChildrenReceivedHandler> getAssociatedType()
   {
      return TYPE;
   }

   /**
    * @return the children
    */
   public EntryCollection getChildren()
   {
      return children;
   }
}
