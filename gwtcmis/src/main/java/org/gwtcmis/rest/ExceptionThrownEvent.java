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

package org.gwtcmis.rest;

import org.gwtcmis.rest.ExceptionThrownHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by The eXo Platform SAS .
 * 
 * @author <a href="mailto:gavrikvetal@gmail.com">Vitaliy Gulyy</a>
 * @version $
 */

public class ExceptionThrownEvent extends ServerExceptionEvent<ExceptionThrownHandler>
{
   /**
    * Exception.
    */
   private Throwable throwable;

   /**
    * Error message.
    */
   private String errorMessage;

   /**
    * GWT Event type.
    */
   public static final GwtEvent.Type<ExceptionThrownHandler> TYPE = new GwtEvent.Type<ExceptionThrownHandler>();

   /**
    * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
    */
   @Override
   public GwtEvent.Type<ExceptionThrownHandler> getAssociatedType()
   {
      return TYPE;
   }

   /**
    * Default constructor.
    */
   public ExceptionThrownEvent()
   {
   }

   /**
    * @param throwable exception
    */
   public ExceptionThrownEvent(Throwable throwable)
   {
      this.throwable = throwable;
   }

   /**
    * @param errorMessage error message
    */
   public ExceptionThrownEvent(String errorMessage)
   {
      this.errorMessage = errorMessage;
   }

   /**
    * @see org.gwtcmis.rest.ServerExceptionEvent#setException(java.lang.Throwable)
    */
   public void setException(Throwable exception)
   {
      throwable = exception;
   }

   /**
    * @return {@link Throwable} exception
    */
   public Throwable getError()
   {
      return throwable;
   }

   /**
    * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
    */
   @Override
   protected void dispatch(ExceptionThrownHandler handler)
   {
      handler.onError(this);
   }

   /**
    * @return {@link String} error message
    */
   public String getErrorMessage()
   {
      return errorMessage;
   }
}
