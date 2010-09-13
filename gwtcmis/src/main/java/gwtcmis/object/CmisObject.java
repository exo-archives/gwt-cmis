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
package gwtcmis.object;

import gwtcmis.object.impl.ChangeInfo;
import gwtcmis.object.impl.CmisObjectImpl;
import gwtcmis.object.impl.ObjectInfo;

import gwtcmis.model.AllowableActions;
import gwtcmis.model.Rendition;
import gwtcmis.model.acl.AccessControlEntry;
import gwtcmis.model.property.CmisProperties;


import java.util.Collection;
import java.util.List;

/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public interface CmisObject
{

   /**
    * {@inheritDoc}
    */
   List<AccessControlEntry> getACL();

   /**
    * {@inheritDoc}
    */
   AllowableActions getAllowableActions();

   /**
    * {@inheritDoc}
    */
   ChangeInfo getChangeInfo();

   /**
    * {@inheritDoc}
    */
   ObjectInfo getObjectInfo();

   /**
    * {@inheritDoc}
    */
   String getPathSegment();

   /**
    * {@inheritDoc}
    */
   Collection<String> getPolicyIds();

   /**
    * {@inheritDoc}
    */
   CmisProperties getProperties();

   /**
    * @param properties
    */
   void setProperties(CmisProperties properties);

   /**
    * {@inheritDoc}
    */
   List<CmisObjectImpl> getRelationship();

   /**
    * {@inheritDoc}
    */
   List<Rendition> getRenditions();

   /**
    * {@inheritDoc}
    */
   boolean isExactACL();

   void setAllowableActions(AllowableActions allowableActions);

   void setChangeInfo(ChangeInfo changeInfo);

   void setObjectInfo(ObjectInfo objectInfo);

   void setExactACL(boolean exactACL);

   void setPathSegment(String pathSegment);

}