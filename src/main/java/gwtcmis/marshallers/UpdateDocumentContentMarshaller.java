/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package gwtcmis.marshallers;

import gwtcmis.rest.Marshallable;

import gwtcmis.marshallers.builder.ObjectXMLBuilder;


/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class UpdateDocumentContentMarshaller implements Marshallable
{
   /**
    * Location of content stream source.
    */
   private String contentSourceUrl;

   /**
    * @param contentSourceUrl location of content stream source
    */
   public UpdateDocumentContentMarshaller(String contentSourceUrl)
   {
      this.contentSourceUrl = contentSourceUrl;
   }

   /**
    * @see gwtcmis.rest.Marshallable#marshal()
    */
   public String marshal()
   {
      return ObjectXMLBuilder.updateDocumentContent(contentSourceUrl);
   }

}
