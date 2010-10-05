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
package org.gwtcmis.object.impl;

import org.gwtcmis.object.CmisObject;
import org.gwtcmis.object.CmisPolicy;

import java.util.HashSet;


/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class CmisPolicyImpl extends CmisObjectImpl implements CmisPolicy
{
   
   /**
    * Policy text
    */
   private String policyText;
   
   public CmisPolicyImpl(CmisObject object, String policyText)
   {
      super(object.getProperties().getProperties(), object.getACL(), object.isExactACL(), 
         new HashSet<String>(object.getPolicyIds()), object.getRelationship(), object.getRenditions(), 
         object.getAllowableActions(), object.getChangeInfo(), object.getObjectInfo(), 
         object.getPathSegment());
      this.policyText = policyText;
   }
   
   /**
    * @see org.gwtcmis.object.CmisPolicy#getPolicyText()
    */
   public String getPolicyText()
   {
      return policyText;
   }

   /**
    * @see org.gwtcmis.object.CmisPolicy#setPolicyText(java.lang.String)
    */
   public void setPolicyText(String policyText)
   {
      this.policyText = policyText;
   }

}
